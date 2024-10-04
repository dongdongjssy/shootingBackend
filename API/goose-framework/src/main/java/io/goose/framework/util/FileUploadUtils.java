package io.goose.framework.util;

import com.aliyun.oss.OSSClient;
import io.goose.common.exception.file.FileNameLengthLimitExceededException;
import io.goose.common.utils.Md5Utils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 文件上传工具类
 *
 * @author goose
 */
public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 52428800;

    /**
     * 默认的文件名最大长度
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 200;

    /**
     * 默认文件类型jpg
     */
    public static final String IMAGE_JPG_EXTENSION = ".jpg";

    public static final String IMAGE_GIF_EXTENSION = ".gif";

    private static int counter = 0;


    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, FileUploadUtils.IMAGE_JPG_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String uploadGif(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, FileUploadUtils.IMAGE_GIF_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param files   上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final List<String> uploadFile(String baseDir, List<MultipartFile> files)
            throws IOException {
        List<String> list = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                list.add(upload(baseDir, file));
            }
            return list;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


    /**
     * 文件上传
     *
     * @param baseDir   相对应用的基目录
     * @param file      上传的文件
     * @param extension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String extension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
                    FileUploadUtils.DEFAULT_FILE_NAME_LENGTH
            );
        }

        assertAllowed(file);

        String fileName = encodingFilename(file, extension);

        File desc = getAbsoluteFile(baseDir, baseDir + fileName);
        file.transferTo(desc);
        return fileName;
    }


    public static String uploadMediaFile(String mediaPath, MultipartFile mediaFile)
            throws IOException {

        String filePath = null;

        if (mediaFile != null && !mediaFile.isEmpty()) {
            filePath = upload(mediaPath, mediaFile);
        }

        return filePath;
    }


    private static final File getAbsoluteFile(String uploadDir, String filename)
            throws IOException {
        File desc = new File(File.separator + filename);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }


    /**
     * 编码文件名
     */
    private static final String encodingFilename(MultipartFile file, String extension) {

        String[] fileType = file.getContentType().split("/");

        if ("video".equals(fileType[0])) {
            extension = "." + fileType[1];
        }

        String fileName = file.getOriginalFilename().replace("_", " ");
        fileName = Md5Utils.hash(fileName + System.nanoTime() + counter++) + extension;

        return fileName;
    }


    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file)
            throws FileSizeLimitExceededException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException("not allowed upload upload", size,
                    DEFAULT_MAX_SIZE
            );
        }
    }


    public static String uploadToAliyunOss(MultipartFile multipartFile, String endpoint,
                                           String accessKeyId, String accessKeySecret, String bucketName, String objectName,boolean isNotRichText)
            throws FileNotFoundException, IOException {
        // 获取文件的后缀名
        String fname = multipartFile.getOriginalFilename();
        int flength = fname.lastIndexOf(".");
        String suffixName = fname.substring(flength);
        // 生成上传文件名
        String finalFileName = new SecureRandom().nextInt(0x0400)
                + UUID.randomUUID().toString().replace("-", "") + suffixName;
        // String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400)
        // + suffixName;
        String originalFileName = multipartFile.getOriginalFilename().trim();
        File file = new File(originalFileName);
        // try with resources to handle file input/output
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
            os.close();
            // multipartFile.transferTo(file);//保存到磁盘
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
             ossClient.putObject(bucketName, objectName + "/" + finalFileName, file);
            ossClient.shutdown();
        }

        // 图片的网络路径
        String pictureUrl;
        if(isNotRichText) {
             pictureUrl = finalFileName;
        }else {
             pictureUrl = "https://" + bucketName + "." + endpoint.substring(8, endpoint.length())
          + "/" + objectName + "/" + finalFileName;
        }
        return pictureUrl;
    }

    public static String extractFrameFromVideo(String baseDir, String videoFileName) {
        log.debug("【开始】　截取视频封面...");
//
        if (!new File(baseDir + videoFileName).exists())
            return null;
        
        String coverFileName = "";
        try (FFmpegFrameGrabber g = new FFmpegFrameGrabber(baseDir + videoFileName);Java2DFrameConverter c = new Java2DFrameConverter();){
        	//Java2DFrameConverter c = new Java2DFrameConverter();
            
            g.start();
            coverFileName = Md5Utils.hash(videoFileName + System.nanoTime() + counter++) + ".png";
            ImageIO.write(c.convert(g.grabImage()), "png", new File(baseDir + coverFileName));
            g.stop();
            log.debug("【结束】　截取视频封面成功");
        } catch (Exception e) {
            log.debug("【结束】　截取视频封面失败");
            e.printStackTrace();
            return null;
        }

        return coverFileName;
    }
}
