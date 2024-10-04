package io.goose.api.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;

import io.goose.common.exception.file.FileNameLengthLimitExceededException;
import io.goose.common.utils.Md5Utils;
import io.goose.common.utils.file.FileUtils;

/**
 * 文件上传工具类
 * 
 * @author goose
 */
public class FileUploadUtilsNew
{
	
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

    private static int counter = 0;

    private static final Logger LOG = LoggerFactory.getLogger(FileUploadUtilsNew.class);
    
    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
	public static final String upload(String baseDir, MultipartFile file) throws IOException {
		try {
			int begin = file.getOriginalFilename().indexOf(".");
			int last = file.getOriginalFilename().length();
			String fileType = file.getOriginalFilename().substring(begin, last);
			return upload(baseDir, file, fileType);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
	public static final List<String> uploadFile(String baseDir, List<MultipartFile> files) throws IOException {
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
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param needDatePathAndRandomName 是否需要日期目录和随机文件名前缀
     * @param extension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String extension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException
    {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtilsNew.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
                    FileUploadUtilsNew.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file);

        String fileName = encodingFilename(file.getOriginalFilename(), extension);

        File desc = getAbsoluteFile(baseDir, baseDir + fileName);
        file.transferTo(desc);
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException
    {
        File desc = new File(File.separator + filename);
        LOG.info("上传文件>>>>>>>>>>"+desc);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 编码文件名
     */
    private static final String encodingFilename(String filename, String extension)
    {
        filename = filename.replace("_", " ");
        filename = Md5Utils.hash(filename + System.nanoTime() + counter++) + extension;
        return filename;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file) throws FileSizeLimitExceededException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException("not allowed upload upload", size, DEFAULT_MAX_SIZE);
        }
    }
    
    
    
    
    
    
    
    
    
    public static HttpServletResponse downLoadFiles(List<FileInputStream> files, HttpServletResponse response) throws Exception {

    	try {
    		// List<File> 作为参数传进来，就是把多个文件的路径放到一个list里面
    		// 创建一个临时压缩文件

    		// 临时文件可以放在CDEF盘中，但不建议这么做，因为需要先设置磁盘的访问权限，最好是放在服务器上，方法最后有删除临时文件的步骤

    		String zipFilename = "D:/tempFile.zip";
    		File file = new File(zipFilename);
    		file.createNewFile();
    		if (!file.exists()) {
    			file.createNewFile();
    		}
    		response.reset();
    		// response.getWriter()
    		// 创建文件输出流
    		FileOutputStream fous = new FileOutputStream(file);
    		ZipOutputStream zipOut = new ZipOutputStream(fous);
    		zipFile(files, zipOut);
    		zipOut.close();
    		fous.close();
    		return downloadZip(file, response);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return response;
    }

    /**
     * 把接受的全部文件打成压缩包
     * 
     * @param List<File>;
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void zipFile(List<FileInputStream> files, ZipOutputStream outputStream) {
    	int size = files.size();
    	for (int i = 0; i < size; i++) {
    		zipFile(files.get(i), outputStream);
    	}
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     * 
     * @param File
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void zipFile(FileInputStream inputFile, ZipOutputStream ouputStream) {
    	try {
//    		if (inputFile.exists()) {
//    			if (inputFile.isFile()) {
    				FileInputStream IN = inputFile;
    				BufferedInputStream bins = new BufferedInputStream(IN, 512);
    				ZipEntry entry = new ZipEntry("啦啦啦啦");
    				ouputStream.putNextEntry(entry);
    				// 向压缩文件中输出数据
    				int nNumber;
    				byte[] buffer = new byte[512];
    				while ((nNumber = bins.read(buffer)) != -1) {
    					ouputStream.write(buffer, 0, nNumber);
    				}
    				// 关闭创建的流对象
    				bins.close();
    				IN.close();
//    			} else {
//    				try {
//    					File[] files = inputFile.listFiles();
//    					for (int i = 0; i < files.length; i++) {
//    						zipFile(files[i], ouputStream);
//    					}
//    				} catch (Exception e) {
//    					e.printStackTrace();
//    				}
//    			}
//    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
    	if (file.exists() == false) {
    		System.out.println("待压缩的文件目录：" + file + "不存在.");
    	} else {
    		try {
    			// 以流的形式下载文件。
    			InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
    			byte[] buffer = new byte[fis.available()];
    			fis.read(buffer);
    			fis.close();
    			// 清空response
    			response.reset();

    			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
    			response.setContentType("application/octet-stream");

    			// 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
    			response.setHeader("Content-Disposition",
    					"attachment;filename=" + new String(file.getName().getBytes("GB2312"), "ISO8859-1"));
    			toClient.write(buffer);
    			toClient.flush();
    			toClient.close();
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		} finally {
    			try {
    				File f = new File(file.getPath());
    				f.delete();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return response;
    }


    @RequestMapping("/downlo")
    public void downLoad(HttpServletResponse response){
        String filename="劳动合同.doc";
        String filePath = "C:\\Users\\Liang Qizhao\\Desktop\\" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
        	try {
    		filename = java.net.URLEncoder.encode(filename, "UTF-8");
    		filename = new String(filename.getBytes(), "iso-8859-1");
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
            
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            
            OutputStream os = null; //输出流
           
                os = response.getOutputStream();
                fis = new FileInputStream(file); 
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                bis.close();
                fis.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("----------file download" + filename);
        }
    }
    /** 
     *  根据路径删除指定的目录或文件，无论存在与否 
     *@param sPath  要删除的文件 
     *@return 删除成功返回 true，否则返回 false。 
     */  
    public boolean deleteFolder(String sPath) {  
    	boolean flag = false;  
        File file = new File(sPath);  
        // 判断目录或文件是否存在  
        if (!file.exists()) {  // 不存在返回 false  
            return flag;  
        } else {  
            // 判断是否为文件  
            if (file.isFile()) {  // 为文件时调用删除文件方法  
                return deleteFile(sPath);  
            } else { // 为目录时调用删除目录方法  
                return deleteDirectory(sPath);  
}  
        }  
    }
    /** 
     * 删除单个文件 
     * @param   sPath    被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */  
    public static boolean deleteFile(String sPath) {  
    	boolean flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }
    
    
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    
    public static String uploadToAliyunOss(MultipartFile multipartFile,String endpoint,String accessKeyId,String accessKeySecret,String bucketName,String objectName) throws FileNotFoundException, IOException {
		// 获取文件的后缀名
		String fname=multipartFile.getOriginalFilename();
		int flength=fname.lastIndexOf(".");
		String suffixName = fname.substring(flength);
		// 生成上传文件名
		String finalFileName=new SecureRandom().nextInt(0x0400) + UUID.randomUUID().toString().replace("-", "") + suffixName;
		//String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;
		String originalFileName=multipartFile.getOriginalFilename().trim();
		File file=new File(originalFileName);
		//try with resources to handle file input/output 
		try(FileOutputStream os = new FileOutputStream(file)){
			os.write(multipartFile.getBytes());
			os.close();
			//multipartFile.transferTo(file);//保存到磁盘
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			ossClient.putObject(bucketName, objectName + "/" + finalFileName, file);
			ossClient.shutdown();
			FileUtils.deleteFile(originalFileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//图片的网络路径
		String pictureUrl="https://"+bucketName+"."+endpoint.substring(8,endpoint.length())+"/"+objectName + "/" + finalFileName;
		return pictureUrl;
	}
}
