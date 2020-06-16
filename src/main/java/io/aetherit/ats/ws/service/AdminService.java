package io.aetherit.ats.ws.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.aetherit.ats.ws.application.support.FileStorageProperties;
import io.aetherit.ats.ws.exception.FileStorageException;
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.admin.ATSMovieAttach;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSMovieActorRel;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;
import io.aetherit.ats.ws.model.movie.ATSMovieActor;
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
    @Transactional
    public void setMovieWithAttach(ATSSimpleUser user, ATSMovieAttach movieAttach,MultipartFile[] files,MultipartFile[] coverChnFiles,MultipartFile[] coverEngFiles,MultipartFile[] coverJpnFiles) {
//    public void setMovieWithAttach(ATSSimpleUser user, ATSMovieAttach movieAttach,MultipartFile files,MultipartFile coverChnFiles,MultipartFile coverEngFiles,MultipartFile coverJpnFiles) {
        
    	/**
    	 * movie tag list combine
    	 */
    	List<ATSMovieTagDtl> tagListAll = ListUtils.union(ListUtils.union(movieAttach.getTagListChn(),movieAttach.getTagListEng()),movieAttach.getTagListJpn());
    	List<ATSMovieActorRel> movieActorRelList = new ArrayList<ATSMovieActorRel>();
    	List<ATSMovieTagDtl> movieTagList = new ArrayList<ATSMovieTagDtl>();
    	List<ATSMovieCoverImage> coverImageList = new ArrayList<ATSMovieCoverImage>();
    	List<ATSMovieCoverImage> movieCoverImageList = new ArrayList<ATSMovieCoverImage>();
    	
    	coverImageList.add(movieAttach.getCoverChn());
    	coverImageList.add(movieAttach.getCoverEng());
    	coverImageList.add(movieAttach.getCoverJpn());
    	
    	/**
    	 * insert movie_bas : get movie id
    	 */
    	ATSMovieBas movieBas = ATSMovieBas.builder()
    									  .movName(movieAttach.getMovName())
    									  .mins(movieAttach.getMins())
    									  .playCnt(movieAttach.getPlayCnt())
    									  .playCntStr(movieAttach.getPlayCntStr())
    									  .downCnt(movieAttach.getDownCnt())
    									  .hasDown(movieAttach.isHasDown())
    									  .loveCnt(0)
    									  .hasLove(movieAttach.isHasLove())
    									  .upCnt(0)
    									  .hasUp(movieAttach.isHasUp())
    									  .dissCnt(0)
    									  .hasDiss(movieAttach.isHasDiss())
    									  .movSn(movieAttach.getMovSn())
    									  .movSnOri(movieAttach.getMovSnOri())
    									  .movType(movieAttach.getMovType())
    									  .domain(movieAttach.getDomain())
//    									  .P240()
//    									  .P360()
//    									  .P480()
//    									  .P720()
//    									  .P1080()
    									  .movDesc(movieAttach.getMovDesc())
    									  .isMosaic(movieAttach.isMosaic())
    									  .movScore(0)
    									  .giftTotal(0)
    									  .movProvider(movieAttach.isMovProvider())
    									  .showYn(movieAttach.isShowYn())
    									  .showOrder(movieAttach.getShowOrder())
    									  .gmtCreateId(user.getUserId())
    									  .modId(user.getUserId())
    									  .build();
    	
    	adminRepository.insertMovieBas(movieBas);
    	
    	Path moveFileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(),fileStorageProperties.getMoviePath(),movieBas.getMovId()+"")
                .toAbsolutePath().normalize();
    	
    	System.out.println("movieAttach.getF240P() ======================= " + movieAttach.getF240p());
    	
    	HashMap<String,Object> fileMap = new HashMap<String,Object>();
    	fileMap.put("movId", movieBas.getMovId());
    	fileMap.put("p240", moveFileStorageLocation.toString()+File.separator+movieAttach.getF240p());
    	fileMap.put("p360", moveFileStorageLocation.toString()+File.separator+movieAttach.getF360p());
    	fileMap.put("p480", moveFileStorageLocation.toString()+File.separator+movieAttach.getF480p());
    	fileMap.put("p720", moveFileStorageLocation.toString()+File.separator+movieAttach.getF720p());
    	fileMap.put("p1080", moveFileStorageLocation.toString()+File.separator+movieAttach.getF1080p());
    	
    	adminRepository.updateMovieBas(fileMap);
    	
//    	System.out.println("movieBas ======================= " + movieBas);
//    	System.out.println("movId ======================= " + movieBas.getModId());
//    	System.out.println("movieAttach.getActorList() ======================= " + movieAttach.getActorList());    	
    	
    	/**
    	 * ATSMovieActorRel set
    	 */
    	for(ATSMovieActor actor:movieAttach.getActorList()) {
    		ATSMovieActorRel movieActor = ATSMovieActorRel.builder()
									    				  .movId(movieBas.getMovId())
									    				  .actorIdx(actor.getActorIdx())
									    				  .modId(user.getUserId())
									    				  .build();
    		movieActorRelList.add(movieActor);
    	}
    	
//    	System.out.println("tagListAll ======================= " + tagListAll);
    	
    	/**
    	 * ATSMovieTagDtl set
    	 */
    	for(ATSMovieTagDtl tag:tagListAll ) {
    		ATSMovieTagDtl movieTagDtl = ATSMovieTagDtl.builder()
									  				   .movId(movieBas.getMovId())
									  				   .name(tag.getName())
									  				   .langCd(tag.getLangCd())
									  				   .build();
    		movieTagList.add(movieTagDtl);
    	}
    	
    	HashMap<String,Object> tagMap = new HashMap<String,Object>();
    	tagMap.put("list", movieTagList);
    	adminRepository.insertMovieTagDtl(tagMap);
    	
//    	System.out.println("fileStorageProperties.getUploadDir() ======================= " + fileStorageProperties.getUploadDir());
//    	System.out.println("fileStorageProperties.getMoviePath() ======================= " + fileStorageProperties.getMoviePath());
//    	System.out.println("movieBas.getMovId() ======================= " + movieBas.getMovId());
//    	System.out.println("ATSLangCode.CHN.name() ======================= " + ATSLangCode.CHN.name());
    	
    	Path covrerChnFileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(),fileStorageProperties.getMoviePath(),movieBas.getMovId()+"",ATSLangCode.CHN.name()).toAbsolutePath().normalize();
    	Path covrerEngFileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(),fileStorageProperties.getMoviePath(),movieBas.getMovId()+"",ATSLangCode.ENG.name()).toAbsolutePath().normalize();
    	Path covrerJpnFileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(),fileStorageProperties.getMoviePath(),movieBas.getMovId()+"",ATSLangCode.JPN.name()).toAbsolutePath().normalize();
    	
//    	System.out.println("covrerChnFileStorageLocation ======================= " + covrerChnFileStorageLocation);
//    	System.out.println("covrerEngFileStorageLocation ======================= " + covrerEngFileStorageLocation);
//    	System.out.println("covrerJpnFileStorageLocation ======================= " + covrerJpnFileStorageLocation);
    	
    	/**
    	 * ATSMovieCoverImage set
    	 */
    	for(ATSMovieCoverImage coverImage:coverImageList) {
    		Path coverPath = Paths.get(fileStorageProperties.getUploadDir(),fileStorageProperties.getMoviePath(),movieBas.getMovId()+"",coverImage.getLangCd().name()).toAbsolutePath().normalize();
    		ATSMovieCoverImage movieCoverImage = ATSMovieCoverImage.builder()
												  				   .movId(movieBas.getMovId())
												  				   .coverDomain(coverImage.getCoverDomain())
												  				   .horizontalLarge(coverPath.toString()+File.separator+coverImage.getHorizontalLarge())
												  				   .horizontalSmall(coverPath.toString()+File.separator+coverImage.getHorizontalSmall())
												  				   .verticalLarge(coverPath.toString()+File.separator+coverImage.getVerticalLarge())
												  				   .verticalSmall(coverPath.toString()+File.separator+coverImage.getVerticalSmall())
												  				   .langCd(coverImage.getLangCd())
												  				   .modId(user.getUserId())
												  				   .build();
    		movieCoverImageList.add(movieCoverImage);
    	}
    	
    	HashMap<String,Object> coverMap = new HashMap<String,Object>();
    	coverMap.put("list", movieCoverImageList);
    	adminRepository.insertMovieCoverImage(coverMap);
    	
    	
    	

    	/**
    	 * file upload process
    	 */
    	for(MultipartFile file:Arrays.asList(files)){
    		storeFile(file,moveFileStorageLocation);
    	}
    	
    	for(MultipartFile file:Arrays.asList(coverChnFiles)){
    		storeFile(file,covrerChnFileStorageLocation);
    	}
    	
    	for(MultipartFile file:Arrays.asList(coverEngFiles)){
    		storeFile(file,covrerEngFileStorageLocation);
    	}
    	
    	for(MultipartFile file:Arrays.asList(coverJpnFiles)){
    		storeFile(file,covrerJpnFileStorageLocation);
    	}
//    	storeFile(files,moveFileStorageLocation);
//    	storeFile(coverChnFiles,covrerChnFileStorageLocation);
//    	storeFile(coverEngFiles,covrerEngFileStorageLocation);
//    	storeFile(coverJpnFiles,covrerJpnFileStorageLocation);
    	
    }
    

    public void storeFile(MultipartFile file, Path fileStorageLocation) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

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
    
    
    public ATSLiveGiftBas setLiveGift(ATSLiveGiftBas liveGift) {
    	adminRepository.insertLiveGift(liveGift);
    	return liveGift;
    }
    
    

}