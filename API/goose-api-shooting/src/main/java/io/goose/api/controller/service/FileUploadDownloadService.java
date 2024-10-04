package io.goose.api.controller.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.goose.api.utils.FileUploadUtilsNew;
import io.goose.common.config.Global;
import io.goose.common.constant.FileUploadConstants;

@Service
public class FileUploadDownloadService {
	
	@Autowired
	Global global;

	@Value("${aliyun.endpoint}")
	private String endpoint;

	@Value("${aliyun.accessKeyId}")
	private String accessKeyId;

	@Value("${aliyun.accessKeySecret}")
	private String accessKeySecret;

	@Value("${aliyun.bucketName}")
	private String bucketName;

	@Value("${aliyun.objectName}")
	private String objectName;
	
	@Value("${upload.category}")
	private Integer uploadCategory;

	public String upload(MultipartFile multipartFile,String aliyunObjectName)
			throws IOException {
		String filePath = "";
		if (FileUploadConstants.UploadCategory.AliyunOss.getValue() == uploadCategory) {
			if(aliyunObjectName==null) aliyunObjectName = objectName;
			filePath = FileUploadUtilsNew.uploadToAliyunOss(multipartFile, endpoint, accessKeyId, accessKeySecret,
					bucketName, aliyunObjectName);
		} else if ( FileUploadConstants.UploadCategory.LOCAL.getValue()==uploadCategory) {
			filePath = FileUploadUtilsNew.upload(global.getBasePicLocation(), multipartFile);
		} else if ( FileUploadConstants.UploadCategory.AWS.getValue()==uploadCategory) {

			//TODO

		}

		return filePath;
	}

}
