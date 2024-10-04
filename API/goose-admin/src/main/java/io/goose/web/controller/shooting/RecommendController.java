package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.goose.common.page.PageDomain;
import io.goose.framework.web.page.TableSupport;
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
import io.goose.shooting.domain.Recommend;
import io.goose.shooting.service.IRecommendService;
import io.goose.shooting.service.impl.ext.ServiceUtils;

import static java.util.Comparator.comparing;


/**
 * 推荐 信息操作处理
 *
 * @author goose
 * @date 2020-05-19
 */
@Controller
@RequestMapping("/shooting/recommend")
public class RecommendController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(RecommendController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/recommend";

    @Autowired
    private IRecommendService recommendService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

    @RequiresPermissions("shooting:recommend:view")
    @GetMapping()
    public String recommend(ModelMap mmap) {
        // mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
        return prefix + "/recommend";
    }


    /**
     * 上传
     *
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public String addSave(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "type") Integer type) throws IOException {

        String filePath = "";
        if (type == 1) {
            filePath = FileUploadUtils.upload(global.getMediaPathRecommend(), file);
        } else if (type == 2) {
            filePath = FileUploadUtils.upload(global.getMediaPathAdvertisement(), file);
        } else if (type == 3) {
            filePath = FileUploadUtils.upload(global.getMediaPathCarousel(), file);
        } else if (type == 4) {
            filePath = FileUploadUtils.upload(global.getMediaPathClub(), file);
        } else if (type == 5) {
            filePath = FileUploadUtils.upload(global.getMediaPathClubActivity(), file);
        } else if (type == 6) {
            filePath = FileUploadUtils.upload(global.getMediaPathClubPost(), file);
        } else if (type == 7) {
            filePath = FileUploadUtils.upload(global.getMediaPathContest(), file);
        } else if (type == 8) {
            filePath = FileUploadUtils.upload(global.getMediaPathMessage(), file);
        } else if (type == 9) {
            filePath = FileUploadUtils.upload(global.getMediaPathPost(), file);
        } else if (type == 10) {
            filePath = FileUploadUtils.upload(global.getMediaPathPublish(), file);
        } else if (type == 11) {
            filePath = FileUploadUtils.upload(global.getMediaPathTraining(), file);
        }
        return filePath;
    }


    /**
     * 查询推荐列表
     */
    @RequiresPermissions("shooting:recommend:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Recommend recommend) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        startPage();
        List<Recommend> list = recommendService.selectRecommendListAssoc(recommend);
        if (pageDomain.getOrderByColumn().equals("")) {
            list.sort(comparing(Recommend::getOnTop).reversed());
        }
        return getDataTable(list);
    }


    /**
     * 导出推荐列表
     */
    @RequiresPermissions("shooting:recommend:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Recommend recommend) {
        List<Recommend> list = recommendService.selectRecommendList(recommend);
        ExcelUtil<Recommend> util = new ExcelUtil<Recommend>(Recommend.class);
        return util.exportExcel(list, "recommend");
    }


    /**
     * 导入推荐列表
     */
    @RequiresPermissions("shooting:recommend:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<Recommend> list =
                    ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Recommend.class);
            for (Recommend obj : list) {
                recommendService.insertRecommend(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }


    /**
     * 新增推荐
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        // mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
        return prefix + "/add";
    }


    /**
     * 新增保存推荐
     */
    @RequiresPermissions("shooting:recommend:add")
    @Log(title = "推荐", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Recommend recommend,
                              @RequestParam(value = "image1File", required = false) MultipartFile image1File,
                              @RequestParam(value = "image2File", required = false) MultipartFile image2File,
                              @RequestParam(value = "image3File", required = false) MultipartFile image3File,
                              @RequestParam(value = "image4File", required = false) MultipartFile image4File,
                              @RequestParam(value = "image5File", required = false) MultipartFile image5File,
                              @RequestParam(value = "image6File", required = false) MultipartFile image6File,
                              @RequestParam(value = "image7File", required = false) MultipartFile image7File,
                              @RequestParam(value = "image8File", required = false) MultipartFile image8File,
                              @RequestParam(value = "image9File", required = false) MultipartFile image9File,
                              @RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
                              @RequestParam(value = "adMediaUrlFile",
                                      required = false) MultipartFile adMediaUrlFile) {
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setVideo(filePath);
                String videoCoverFilePath=recommend.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                recommend.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
            if (adMediaUrlFile != null && !adMediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(adMediaUrlFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setAdMediaUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        recommend.setClientUserId(1L);

        recommend.setCreateBy(ShiroUtils.getLoginName());
        recommend.setContentShort(ServiceUtils.trimContentRichText(recommend.getContent()));

        return toAjax(recommendService.insertRecommend(recommend));
    }


    /**
     * 修改推荐
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Recommend recommend = recommendService.selectRecommendByIdAssoc(id);
        mmap.put("recommend", recommend);
        // mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        return prefix + "/edit";
    }


    /**
     * 修改保存推荐
     */
    @RequiresPermissions("shooting:recommend:edit")
    @Log(title = "推荐", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Recommend recommend,
                               @RequestParam(value = "image1File", required = false) MultipartFile image1File,
                               @RequestParam(value = "image2File", required = false) MultipartFile image2File,
                               @RequestParam(value = "image3File", required = false) MultipartFile image3File,
                               @RequestParam(value = "image4File", required = false) MultipartFile image4File,
                               @RequestParam(value = "image5File", required = false) MultipartFile image5File,
                               @RequestParam(value = "image6File", required = false) MultipartFile image6File,
                               @RequestParam(value = "image7File", required = false) MultipartFile image7File,
                               @RequestParam(value = "image8File", required = false) MultipartFile image8File,
                               @RequestParam(value = "image9File", required = false) MultipartFile image9File,
                               @RequestParam(value = "videoFile", required = false) MultipartFile videoFile,
                               @RequestParam(value = "adMediaUrlFile",
                                       required = false) MultipartFile adMediaUrlFile) {
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setVideo(filePath);
                String videoCoverFilePath=recommend.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                recommend.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
            if (adMediaUrlFile != null && !adMediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(adMediaUrlFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommend.setAdMediaUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        recommend.setUpdateBy(ShiroUtils.getLoginName());
        recommend.setContentShort(ServiceUtils.trimContentRichText(recommend.getContent()));

        return toAjax(recommendService.updateRecommend(recommend));
    }


    /**
     * 删除推荐
     */
    @RequiresPermissions("shooting:recommend:remove")
    @Log(title = "推荐", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(recommendService.deleteRecommendByIds(ids));
    }


    /**
     * 查询推荐分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody Recommend recommend) {
        startPage(recommend.getPd());

        List<Recommend> list = recommendService.selectRecommendListAssoc(recommend);
        return getDataTable(list);
    }
}
