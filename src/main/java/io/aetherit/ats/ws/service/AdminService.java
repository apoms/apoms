package io.aetherit.ats.ws.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.aetherit.ats.ws.application.support.FileStorageProperties;
import io.aetherit.ats.ws.exception.FileStorageException;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.model.type.ATSLiveRoomStatus;
import io.aetherit.ats.ws.model.type.ATSLiveRoomType;
import io.aetherit.ats.ws.model.type.ATSUserStatus;
import io.aetherit.ats.ws.repository.AdminRepository;

@Service
public class AdminService {

    private FileStorageProperties fileStorageProperties;
    private AdminRepository adminRepository;
    
    @Autowired
    public AdminService(FileStorageProperties fileStorageProperties, 
    					AdminRepository adminRepository) {
        this.fileStorageProperties = fileStorageProperties;
        this.adminRepository = adminRepository;
    }

    /**
	 * movie file upload
	 * @param channelFile
	 * @param file
	 */
    public void uploadMovieAttachObject(ATSMovieBas movieBas,MultipartFile[] files) {
        
//            Arrays.asList(files)
//            	  .stream()
//	              .map(file -> storeFile(movieBas,file))
//	              .collect(Collectors.toList());
            
//            adminRepository.insertMovieBas(movieBas);
    }
    

    public void storeFile(ATSMovieBas movieBas,MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(),fileStorageProperties.getMoviePath(),movieBas.getMovId()+"")
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
//            adminRepository.insertChannelFile(channelFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    
    /**
     * 파일 다운로드
     * @param fileName
     * @return
     * @throws MalformedURLException
     */
    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
    	
    	Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    	
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
		
        Resource resource = new UrlResource(filePath.toUri());
		if(resource.exists()) {
		    return resource;
		} else {
		    throw new FileStorageException("File not found " + fileName);
		}
    }
    
    public void approvalUserTypeAnchor(long userId) {
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", userId);
    	map.put("userStatus", ATSUserStatus.NORMAL);
    	map.put("typeCode", ATSLiveRoomType.PUBLIC);
    	map.put("statusCode", ATSLiveRoomStatus.WAIT);
    	
        adminRepository.approvalUserTypeAnchor(map);
    }
    
    

}