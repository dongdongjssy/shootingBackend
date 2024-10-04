package io.goose.web.controller.shooting;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.config.Global;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;

@RestController
@RequestMapping("/shooting/oss")
public class OssController {
	
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	
    @Autowired
    private Global global;
    
	/**
	 * 上传
	 * @throws IOException 
	 */
	@PostMapping("/upload")
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public String upload(@RequestParam("file") MultipartFile file,@RequestParam("type") Integer type) throws IOException
	{
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
	        }
		String pic = fileUploadDownloadService.upload(file,null,objectName,true);
		return pic;
	}
	
	@PostMapping("/uploadRichText")
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public String uploadRichText(@RequestParam("file") MultipartFile file,@RequestParam("type") Integer type) throws IOException
	{
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
		   	} else if(type == UploadTypeEnums.Coach.getCode()) {
			   objectName = UploadTypeEnums.Coach.getValue();
		   	} else if(type == UploadTypeEnums.Judge.getCode()) {
			   objectName = UploadTypeEnums.Judge.getValue();
		    }else if(type == UploadTypeEnums.StartAdvertisement.getCode()) {
			   objectName = UploadTypeEnums.StartAdvertisement.getValue();
		    }else if(type == UploadTypeEnums.PaymentCode.getCode()) {
			   objectName = UploadTypeEnums.PaymentCode.getValue();
		    }else if(type == UploadTypeEnums.Honor.getCode()) {
			   objectName = UploadTypeEnums.Honor.getValue();
		    }else if(type == UploadTypeEnums.Role.getCode()) {
			   objectName = UploadTypeEnums.Role.getValue();
		    }else if(type == UploadTypeEnums.Goods.getCode()) {
			   objectName = UploadTypeEnums.Goods.getValue();
		    }else if(type == UploadTypeEnums.GoodsCarousel.getCode()) {
			   objectName = UploadTypeEnums.GoodsCarousel.getValue();
		    }else if(type == UploadTypeEnums.Order.getCode()) {
			   objectName = UploadTypeEnums.Order.getValue();
		    }
		String pic = fileUploadDownloadService.upload(file,null,objectName,false);
		return pic;
	}
	
}
