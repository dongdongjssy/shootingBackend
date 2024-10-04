package io.goose.framework.rest.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;

import io.goose.common.config.Global;
import io.goose.common.constant.FileUploadConstants;
import io.goose.common.utils.Md5Utils;
import io.goose.framework.util.FileUploadUtils;


@Service
public class FileUploadDownloadOSSService {

   @Autowired
   Global global;

   @Value( "${aliyun.endpoint}" )
   private String endpoint;

   @Value( "${aliyun.accessKeyId}" )
   private String accessKeyId;

   @Value( "${aliyun.accessKeySecret}" )
   private String accessKeySecret;

   @Value( "${aliyun.bucketName}" )
   private String bucketName;

//   @Value( "${aliyun.objectName}" )
//   private String objectName;

   @Value( "${upload.category}" )
   private Integer uploadCategory;

   private static int counter = 0;

   public String upload( MultipartFile multipartFile, String aliyunObjectName,String objectName,boolean isNotRichText) throws IOException {
      String filePath = "";
      if ( FileUploadConstants.UploadCategory.AliyunOss.getValue() == uploadCategory ) {
         if ( aliyunObjectName == null )
            aliyunObjectName = objectName;
         filePath = FileUploadUtils.uploadToAliyunOss( multipartFile, endpoint, accessKeyId,
               accessKeySecret, bucketName, aliyunObjectName,isNotRichText );
      } else if ( FileUploadConstants.UploadCategory.LOCAL.getValue() == uploadCategory ) {
         filePath = FileUploadUtils.upload( global.getShooting(), multipartFile );
      } else if ( FileUploadConstants.UploadCategory.AWS.getValue() == uploadCategory ) {

         // TODO

      }

      return filePath;
   }

    public String uploadAliOss( MultipartFile multipartFile, String aliyunObjectName,String objectName,boolean isNotRichText) throws IOException {
            if ( aliyunObjectName == null ){
                aliyunObjectName = objectName;
            }
        String filePath = FileUploadUtils.uploadToAliyunOss( multipartFile, endpoint, accessKeyId,
                    accessKeySecret, bucketName, aliyunObjectName,isNotRichText );
        return filePath;
    }


   public String extractVideo(String baseDir, String videoFileName,String objectName) {
//
//       if (!new File(baseDir + videoFileName).exists())
//           return null;
       FFmpegFrameGrabber g = new FFmpegFrameGrabber("https://cpsa-oss.oss-cn-beijing.aliyuncs.com/" + objectName + videoFileName);
       Java2DFrameConverter c = new Java2DFrameConverter();

       String coverFileName = "";

       try {
           g.start();
           coverFileName = Md5Utils.hash(videoFileName + System.nanoTime() + counter++) + ".png";
           File f = new File(baseDir + coverFileName);
           ImageIO.write(c.convert(g.grabImage()), "png", f);
           g.stop();
            coverFileName = new SecureRandom().nextInt(0x0400)
                   + UUID.randomUUID().toString().replace("-", "") + ".png";
           //TODO  
           OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
           ossClient.putObject("cpsa-oss", objectName + "/" + coverFileName, f);
           ossClient.shutdown();
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }

       return coverFileName;
   }


    public String extractALiVideo(String videoFileName,String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600,m_fast";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
        // 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl.toString();
    }


   public BufferedImage composeImageFullPath( String imageName ) throws IOException {
      String filePath = "";
      BufferedImage logoImage = null;
      if ( FileUploadConstants.UploadCategory.AliyunOss.getValue() == uploadCategory ) {
         URL url = new URL( imageName );
         logoImage = ImageIO.read( url );

      } else if ( FileUploadConstants.UploadCategory.LOCAL.getValue() == uploadCategory ) {
         filePath = global.getShooting() + imageName;
         File logoFile = new File( filePath );
         if ( logoFile.exists() ) {
            logoImage = ImageIO.read( logoFile );
         }

      } else if ( FileUploadConstants.UploadCategory.AWS.getValue() == uploadCategory ) {

         // TODO

      }

      return logoImage;
   }

}
