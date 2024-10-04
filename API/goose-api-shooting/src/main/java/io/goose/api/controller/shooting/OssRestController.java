package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.shooting.domain.MyClub;
import io.goose.shooting.service.impl.ext.MyClubServiceImplExt;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * oss上传
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/oss" )
@CrossOrigin( origins = "*", maxAge = 3600 )
public class OssRestController {

	  @Autowired
	  private FileUploadDownloadOSSService fileUploadDownloadService;


	   /**
	    * 上传
	 * @throws IOException 
	    */
	   @PostMapping( "/upload" )
	   public String upload(@RequestParam("file") MultipartFile file,@RequestParam("type") Integer type) throws IOException {
		   String objectName = "";
	       if (type == UploadTypeEnums.Recommend.getCode()) {
	    	   objectName = UploadTypeEnums.Recommend.getValue();
	        } else if (type == UploadTypeEnums.Advertisement.getCode()) {
	        	objectName = UploadTypeEnums.Advertisement.getValue();
	        } else if (type == UploadTypeEnums.Carousel.getCode()) {
	        	objectName = UploadTypeEnums.Carousel.getValue();
	        } else if (type == UploadTypeEnums.Club.getCode()) {
	        	objectName = UploadTypeEnums.Club.getValue();
	        } else if (type == UploadTypeEnums.ClubActivity.getCode()) {
	        	objectName = UploadTypeEnums.ClubActivity.getValue();
	        } else if (type == UploadTypeEnums.ClubPost.getCode()) {
	        	objectName = UploadTypeEnums.ClubPost.getValue();
	        } else if (type == UploadTypeEnums.Contest.getCode()) {
	        	objectName = UploadTypeEnums.Contest.getValue();
	        } else if (type == UploadTypeEnums.Message.getCode()) {
	        	objectName = UploadTypeEnums.Message.getValue();
	        } else if (type == UploadTypeEnums.Post.getCode()) {
	        	objectName = UploadTypeEnums.Post.getValue();
	        } else if (type == UploadTypeEnums.Publish.getCode()) {
	        	objectName = UploadTypeEnums.Publish.getValue();
	        } else if (type == UploadTypeEnums.Training.getCode()) {
	        	objectName = UploadTypeEnums.Training.getValue();
	        } else if(type == UploadTypeEnums.ClientUser.getCode()) {
	        	objectName = UploadTypeEnums.ClientUser.getValue();
	        } else if(type == UploadTypeEnums.Zh.getCode()) {
	        	objectName = UploadTypeEnums.Zh.getValue();
	        }else if(type == UploadTypeEnums.Goods.getCode()) {
	        	objectName = UploadTypeEnums.Goods.getValue();
	        }else if(type == UploadTypeEnums.GoodsCarousel.getCode()) {
	        	objectName = UploadTypeEnums.GoodsCarousel.getValue();
	        }
		String pic = fileUploadDownloadService.upload(file,null,objectName,true);
		return pic;
	   }
}
