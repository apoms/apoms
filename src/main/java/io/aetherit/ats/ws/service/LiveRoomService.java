package io.aetherit.ats.ws.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.aetherit.ats.ws.application.support.FileStorageProperties;
import io.aetherit.ats.ws.exception.FileStorageException;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSUserBas;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;
import io.aetherit.ats.ws.model.live.ATSLiveRoomServer;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.repository.LiveRoomRepository;

@Service
public class LiveRoomService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LiveRoomService.class);

    private UserService userService;
    private LiveRoomRepository liveRoomRepository;
    private FileStorageProperties fileStorageProperties;

    @Autowired
    public LiveRoomService(LiveRoomRepository liveRoomRepository, 
			    		   UserService userService,
			    		   FileStorageProperties fileStorageProperties) {
        this.userService = userService;
        this.liveRoomRepository = liveRoomRepository;
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * live room list
     * @return
     */
    public List<ATSLiveRoomServer> getLiveRoomList(ATSLangCode langCd) {
    	List<ATSLiveRoomServer> liveRoomServerList = new ArrayList<ATSLiveRoomServer>();
    	List<ATSLiveRoom> liveRoomList = liveRoomRepository.selectLiveRoomList();
    	for(ATSLiveRoom liveRoom:liveRoomList) {
    		ATSWebrtcServer webrtcServer = getWebrtcServer(liveRoom.getRoomId());
    		
    		if(webrtcServer==null) {
    			webrtcServer = getWebrtcServer(0);
    		}
    		ATSLiveRoomServer liveRoomServer = ATSLiveRoomServer.builder()
    														    .liveRoom(liveRoom)
    														    .webrtcServer(webrtcServer)
    														    .anchor(userService.getUser(liveRoom.getUserId()))
    														    .build();
    		liveRoomServerList.add(liveRoomServer);
    	}
    	return liveRoomServerList;
    }
    
    /**
     * live room by room id or user id
     * @return
     */
    public ATSLiveRoomServer getLiveRoom(HashMap<String,Object> map) {
    	ATSLiveRoom liveRoom = liveRoomRepository.selectLiveRoom(map);
    	
		ATSWebrtcServer webrtcServer = getWebrtcServer(liveRoom.getRoomId());
		
		if(webrtcServer==null) {
			webrtcServer = getWebrtcServer(0);
		}
		ATSLiveRoomServer liveRoomServer = ATSLiveRoomServer.builder()
														    .liveRoom(liveRoom)
														    .webrtcServer(webrtcServer)
														    .build();
    	return liveRoomServer;
    }
    
    
    /**
     * insert live room
     * @return
     */
    public void patchLiveRoom(ATSLiveRoomPatch liveRoom) {
    	liveRoomRepository.patchLiveRoom(liveRoom);
    }
    
    
    /**
     * webrtc server info
     * @return
     */
    private ATSWebrtcServer getWebrtcServer(long roomId) {
    	return liveRoomRepository.selectWebrtcServer(roomId);
    }
    
    /**
     * insert server room
     * @return
     */
    public void setServerRoom(ATSServerRoom serverRoom) {
    	liveRoomRepository.insertServerRoom(serverRoom);
    }
    
    /**
     * insert live room user history
     * @return
     */
    public void setLiveRoomAccess(ATSLiveRoomUserHst accessRoomHistory) {
    	liveRoomRepository.insertLiveRoomUserHst(accessRoomHistory);
    }
    
    
    /**
	 * 사용자 프로파일 사진 업로드
	 * @param userProfile
	 * @param file
	 */
	public void uploadThumnailObject(ATSLiveRoom liveRoom, MultipartFile file, String fileName, String thumnailUrl) {

    	Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(), thumnailUrl, liveRoom.getUserId()+"")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            Path targetLocation = fileStorageLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            try {
//            	liveRoomRepository.updateThumnailUrl(liveRoom);
            	
            	ATSLiveRoomPatch thumnailPatch = ATSLiveRoomPatch.builder()
            											 .roomId(liveRoom.getRoomId())
            											 .thumnailUrl(thumnailUrl)
            											 .build();
            	
            	patchLiveRoom(thumnailPatch);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}
    
    
}
