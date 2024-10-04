package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Post;
import io.goose.shooting.service.impl.ClientUserServiceImpl;
import io.goose.shooting.service.impl.ext.PostServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 动态 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/shooting/post")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "动态 ", description = "动态")
public class PostRestController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(PostRestController.class);

    @Autowired
    private PostServiceImplExt postService;

    @Autowired
    private ClientUserServiceImpl clientUserService;

    @Autowired
    private Global global;
    
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

    /**
     * 查询动态列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询动态列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "post", value = "动态",
            required = false, dataType = "Post")})
    public TableDataInfo list(@RequestBody Post post) {
        startPage(post.getPd());

        Optional<Long> userId = getUserIdFromHeader();
        if (userId.isPresent()) {
            return getDataTable(postService.selectPostSummaryList(userId.get()));
        } else {
            return getDataTable(postService.selectPostList(post));
        }
    }


    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Post getById(@PathVariable("id") Long id) {
        return postService.selectPostById(id);
    }

    /**
     * 导出动态列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出动态列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "post", value = "动态", required = false, dataType =
     * "Post") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Post post) {
     * List<Post> list = postService.selectPostList(post); ExcelUtil<Post> util = new
     * ExcelUtil<Post>(Post.class); return util.exportExcel(list, "post"); }
     */

    /**
     * 导入动态列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入动态列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "动态文件", required = false, dataType
     * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try { List<Post>
     * list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Post.class); for(Post obj : list)
     * { postService.insertPost(obj); } } catch(ExcelUtilException | IOException e) { return
     * AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存动态
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存动态")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "post", value = "动态",
            required = true, dataType = "Post")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody Post post) {
        return toAjax(postService.insertPost(post));
    }


    /**
     * 修改保存动态
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存动态")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "post", value = "动态",
            required = true, dataType = "Post")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Post post) {
        return toAjax(postService.updatePost(post));
    }


    /**
     * 删除动态
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除动态")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(postService.deletePostByIds(ids));
    }


    /**
     * 根据id删除动态
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除动态")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(postService.deletePostById(id));
    }


    /**
     * 查询动态分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询动态分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "post", value = "动态",
            required = true, dataType = "Post")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Post post) {
        startPage(post.getPd());
        List<Post> list = postService.selectPostList(post);
        return getDataTable(list);
    }


    /**
     * 查询动态分页列表
     */
    @PostMapping("/uploadPic")
    @ApiOperation(value = "图片上传")
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String,String> uploadPic(
            @RequestParam(value = "image1File", required = false) MultipartFile image1File,
          @RequestParam(value = "image2File", required = false) MultipartFile image2File,
          @RequestParam(value = "image3File", required = false) MultipartFile image3File,
          @RequestParam(value = "image4File", required = false) MultipartFile image4File,
          @RequestParam(value = "image5File", required = false) MultipartFile image5File,
          @RequestParam(value = "image6File", required = false) MultipartFile image6File,
          @RequestParam(value = "image7File", required = false) MultipartFile image7File,
          @RequestParam(value = "image8File", required = false) MultipartFile image8File,
          @RequestParam(value = "image9File", required = false) MultipartFile image9File,
          @RequestParam(value = "videoFile", required = false) MultipartFile videoFile){

        Map<String,String> map=new HashMap<>();
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image1File, null, UploadTypeEnums.Post.getValue(),true);
                map.put("filePath0",filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image2File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片2... " + filePath);
                map.put("filePath1",filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image3File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片3... " + filePath);
                map.put("filePath2",filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image4File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片4... " + filePath);
                map.put("filePath3",filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image5File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片5... " + filePath);
                map.put("filePath4",filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image6File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片6... " + filePath);
                map.put("filePath5",filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image7File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片7... " + filePath);
                map.put("filePath6",filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image8File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片8... " + filePath);
                map.put("filePath7",filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(image9File, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传图片9... " + filePath);
                map.put("filePath8",filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.uploadAliOss(videoFile, null, UploadTypeEnums.Post.getValue(),true);
                log.debug("上传视频... " + filePath);
                String videoCoverFilePath=filePath+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                map.put("filePath9",filePath);
                map.put("videoCoverFilePath",videoCoverFilePath);
            }
        } catch (Exception e) {
            log.error("媒体上传失败！", e);
            map.put("res","error");
            return map;
        }
        map.put("res","success");
        return map;
    }


    /**
     * 新增保存动态
     */
    @PostMapping("/addPost")
    @ApiOperation(value = "客户端发布动态")
    public AjaxResult addPost(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "image1File", required = false) MultipartFile image1File,
            @RequestParam(value = "image2File", required = false) MultipartFile image2File,
            @RequestParam(value = "image3File", required = false) MultipartFile image3File,
            @RequestParam(value = "image4File", required = false) MultipartFile image4File,
            @RequestParam(value = "image5File", required = false) MultipartFile image5File,
            @RequestParam(value = "image6File", required = false) MultipartFile image6File,
            @RequestParam(value = "image7File", required = false) MultipartFile image7File,
            @RequestParam(value = "image8File", required = false) MultipartFile image8File,
            @RequestParam(value = "image9File", required = false) MultipartFile image9File,
            @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) {
        log.debug("【发布新动态】 开始");

        ClientUser user = clientUserService.selectClientUserById(userId);
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }

        Post post = new Post();
        post.setClientUserId(userId);

        try {
            if (image1File != null && !image1File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image1File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage1(filePath);
                log.debug("上传图片1... " + filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image2File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage2(filePath);
                log.debug("上传图片2... " + filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image3File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage3(filePath);
                log.debug("上传图片3... " + filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image4File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage4(filePath);
                log.debug("上传图片4... " + filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image5File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage5(filePath);
                log.debug("上传图片5... " + filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image6File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage6(filePath);
                log.debug("上传图片6... " + filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image7File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage7(filePath);
                log.debug("上传图片7... " + filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image8File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage8(filePath);
                log.debug("上传图片8... " + filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(image9File, null, UploadTypeEnums.Post.getValue(),true);
                post.setImage9(filePath);
                log.debug("上传图片9... " + filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
            	String filePath = fileUploadDownloadService.upload(videoFile, null, UploadTypeEnums.Post.getValue(),true);
                post.setVideo(filePath);
                log.debug("上传视频... " + filePath);
            }

        } catch (Exception e) {
            log.error("媒体上传失败！", e);
            return error(e.getMessage());
        }

        post.setCreateBy(user.getUserName());
        if (content != null) {
            post.setContent(content);
        }

        //TODO  目前根据小帅说的   先写死APP端发布动态默认 通过
        post.setStatus(1);
        //TODO 之后如果需要审核就把status 1 去掉
        int result = postService.insertPost(post);

        if (result > 0) {
            if (post.getVideo() != null) {
               /* String dd=UploadTypeEnums.Post.getValue()+"/";
            	String videoCoverFilePath = fileUploadDownloadService.extractALiVideo(post.getVideo(), dd);
                if (videoCoverFilePath == null) {
                    if (post.getId() != null)
                        postService.deletePostById(post.getId());
                    return AjaxResult.error("视频上传失败");
                }*/
                String videoCoverFilePath=post.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                post.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);

                int updateResult = postService.updatePost(post);
                if (updateResult > 0) {
                    log.debug("【发布新动态】 完成");
                    return AjaxResult.success("发布成功");
                } else {
                    if (post.getId() != null)
                        postService.deletePostById(post.getId());
                    return AjaxResult.error("视频上传失败");
                }
            } else {
                log.debug("【发布新动态】 完成");
                return AjaxResult.success("发布成功");
            }
        } else {
            log.debug("【发布新动态】 失败");
            return AjaxResult.error("发布失败");
        }
    }
}
