package io.goose.api.controller.common;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.goose.api.controller.service.FileUploadDownloadService;
import io.goose.api.utils.FileUploadUtilsNew;
import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;

@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/file")
public class UploadFileRestController {

	
	@Autowired
	Global global;
	
	@Autowired
	FileUploadDownloadService FileUploadService;
	
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

	@PostMapping("/upload")
	public String upload(HttpServletRequest request)
			throws IOException {
		
		MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
		MultipartFile multipartFile =  req.getFile("file");
		String filePath = FileUploadService.upload(multipartFile, objectName);
		return filePath;
	}
	
	/**
	 * 上传多个文件
	 * @throws Exception 
	 */
	@PostMapping("/multiUpload")
	@ResponseBody
	public AjaxResult multiUpload(HttpServletRequest request) throws Exception
	{
		 List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                 .getFiles("file");
		StringBuffer sb = new StringBuffer();
		if(null !=files) {
			
			for (MultipartFile multipartFile : files) {
				String fileUrl = FileUploadService.upload(multipartFile,objectName);
				sb.append(fileUrl).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		
		return AjaxResult.success(sb.toString());
	}
	
	/**
	 * 删除文件
	 * @throws Exception 
	 */
	@PostMapping("/deleteFile")
	@ResponseBody
	public AjaxResult deleteFile(String fileName )
	{
		boolean deleteFile = FileUploadUtilsNew.deleteFile(global.getProfile()+fileName);
		if(deleteFile) {
			return AjaxResult.success();
		}
		return AjaxResult.error();
	}

	/**
	 * 下载
	 * @throws Exception 
	 */
	@GetMapping("/download")
	@ResponseBody
	public void download(String fileName,HttpServletResponse response, HttpServletRequest request) throws Exception
	{

	}


	/**
	 * 批量下载并压缩
	 * @throws Exception 
	 */
	@GetMapping("/downloadZip")
	@ResponseBody
	public void downloadZip(String fileName,HttpServletResponse response, HttpServletRequest request) throws Exception
	{
		
		
	}


}
