package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;
import io.goose.common.constant.Constants;
import io.goose.common.enums.BusinessType;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Message;
import io.goose.shooting.domain.Post;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IMessageService;
import io.goose.shooting.service.IPostService;
import io.goose.shooting.service.impl.ext.ServiceUtils;
import io.goose.web.controller.service.JpushService;


/**
 * 动态 信息操作处理
 *
 * @author goose
 * @date 2020-05-09
 */
@Controller
@RequestMapping("/shooting/post")
public class PostController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/post";

    @Autowired
    private IPostService postService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private JpushService pushService;

    @Autowired
    private IMessageService messageService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

    @RequiresPermissions("shooting:post:view")
    @GetMapping()
    public String post(ModelMap mmap) {
        mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
        return prefix + "/post";
    }


    /**
     * 查询动态列表
     */
    @RequiresPermissions("shooting:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Post post) {
        startPage();
        List<Post> list = postService.selectPostListAssocFullDisplay(post);
        return getDataTable(list);
    }


    /**
     * 导出动态列表
     */
    @RequiresPermissions("shooting:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Post post) {
        List<Post> list = postService.selectPostList(post);
        ExcelUtil<Post> util = new ExcelUtil<Post>(Post.class);
        return util.exportExcel(list, "post");
    }


    /**
     * 导入动态列表
     */
    @RequiresPermissions("shooting:post:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<Post> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Post.class);
            for (Post obj : list) {
                postService.insertPost(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }


    /**
     * 新增动态
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
        return prefix + "/add";
    }


    /**
     * 新增保存动态
     */
    @RequiresPermissions("shooting:post:add")
    @Log(title = "动态", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Post post,
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
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Post.getValue() , true);
                post.setVideo(filePath);
                String videoCoverFilePath=post.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                post.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        post.setCreateBy(ShiroUtils.getLoginName());
        post.setContentShort(ServiceUtils.trimContentRichText(post.getContent()));

        return toAjax(postService.insertPost(post));
    }


    /**
     * 修改动态
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Post post = postService.selectPostByIdAssoc(id);
        mmap.put("post", post);
        mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        return prefix + "/edit";
    }


    /**
     * 修改保存动态
     */
    @RequiresPermissions("shooting:post:edit")
    @Log(title = "动态", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Post post,
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
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Post.getValue() , true);
                post.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Post.getValue() , true);
                post.setVideo(filePath);
                String videoCoverFilePath=post.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                post.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        post.setUpdateBy(ShiroUtils.getLoginName());
        post.setContentShort(ServiceUtils.trimContentRichText(post.getContent()));

        // 查这条数据的状态是否发生了改变
        Post newPost = postService.selectPostById(post.getId());
        int success = 0;
        if (newPost.getStatus() == post.getStatus()) {
            // 没发生改变 只走修改
            return toAjax(postService.updatePost(post));
        } else {
            // 修改后 如果是未通过或已发布状态 发消息和推送给用户
            success = postService.updatePost(post);
            if (success > 0) {
                Message message = new Message();
                if (post.getStatus() != 0) {
                    message.setCreateBy(ShiroUtils.getLoginName());
                    message.setTitle("动态审核结果");
                    if (post.getStatus().toString().equals(Constants.POST_STATUS_NO)) {
                        message.setContent("您有一条动态审核未通过");
                    } else {
                        message.setContent("您有一条动态审核通过,已发布");
                    }
                }
                message.setType(2);
                success = messageService.insertMessage(message);
                if (success > 0) {
                    ClientUser clientUser =
                            clientUserService.selectClientUserById(post.getClientUserId());
                    if (clientUser != null) {
                        messageService.insertMessageUser(message.getId(), clientUser.getId(),
                                message.getCreateBy());
                        // 推送
                        pushService.jpush(message.getTitle(), message.getContent(),
                                clientUser.getId().toString(), "4", "2", String.valueOf(post.getId()));
                    }
                } else {
                    throw new RuntimeException("编辑失败");
                }
            }
        }
        return toAjax(success);
    }


    /**
     * 删除动态
     */
    @RequiresPermissions("shooting:post:remove")
    @Log(title = "动态", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(postService.deletePostByIds(ids));
    }


    /**
     * 查询动态分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody Post post) {
        startPage(post.getPd());

        List<Post> list = postService.selectPostListAssoc(post);
        return getDataTable(list);
    }
}
