package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.service.impl.ext.ServiceUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.shooting.domain.RecommendJudge;
import io.goose.shooting.service.IRecommendJudgeService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 首页裁判 信息操作处理
 *
 * @author goose
 * @date 2020-06-23
 */
@Controller
@RequestMapping("/shooting/recommendJudge")
public class RecommendJudgeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RecommendJudgeController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/recommendJudge";

    @Autowired
    private IRecommendJudgeService recommendJudgeService;
    
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

    @RequiresPermissions("shooting:recommendJudge:view")
    @GetMapping()
    public String recommendJudge(ModelMap mmap) {
        return prefix + "/recommendJudge";
    }

    /**
     * 查询首页裁判列表
     */
    @RequiresPermissions("shooting:recommendJudge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RecommendJudge recommendJudge) {
        startPage();
        List<RecommendJudge> list = recommendJudgeService.selectRecommendJudgeListAssoc(recommendJudge);
        return getDataTable(list);
    }


    /**
     * 导出首页裁判列表
     */
    @RequiresPermissions("shooting:recommendJudge:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RecommendJudge recommendJudge) {
        List<RecommendJudge> list = recommendJudgeService.selectRecommendJudgeList(recommendJudge);
        ExcelUtil<RecommendJudge> util = new ExcelUtil<RecommendJudge>(RecommendJudge.class);
        return util.exportExcel(list, "recommendJudge");
    }

    /**
     * 导入首页裁判列表
     */
    @RequiresPermissions("shooting:recommendJudge:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<RecommendJudge> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), RecommendJudge.class);
            for (RecommendJudge obj : list) {
                recommendJudgeService.insertRecommendJudge(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }

    /**
     * 新增首页裁判
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        return prefix + "/add";
    }

    /**
     * 新增保存首页裁判
     */
    @RequiresPermissions("shooting:recommendJudge:add")
    @Log(title = "首页裁判", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RecommendJudge recommendJudge
            , @RequestParam(value = "image1File", required = false) MultipartFile image1File
            , @RequestParam(value = "image2File", required = false) MultipartFile image2File
            , @RequestParam(value = "image3File", required = false) MultipartFile image3File
            , @RequestParam(value = "image4File", required = false) MultipartFile image4File
            , @RequestParam(value = "image5File", required = false) MultipartFile image5File
            , @RequestParam(value = "image6File", required = false) MultipartFile image6File
            , @RequestParam(value = "image7File", required = false) MultipartFile image7File
            , @RequestParam(value = "image8File", required = false) MultipartFile image8File
            , @RequestParam(value = "image9File", required = false) MultipartFile image9File
            , @RequestParam(value = "videoFile", required = false) MultipartFile videoFile
            , @RequestParam(value = "adMediaUrlFile", required = false) MultipartFile adMediaUrlFile
    ) {
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setVideo(filePath);

                String videoCoverFilePath=recommendJudge.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                recommendJudge.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
            if (adMediaUrlFile != null && !adMediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(adMediaUrlFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setAdMediaUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        recommendJudge.setCreateBy(ShiroUtils.getLoginName());
        recommendJudge.setClientUserId(1L);
        recommendJudge.setContentShort(ServiceUtils.trimContentRichText(recommendJudge.getContent()));

        if(recommendJudge.getCategory() == 3) {
            recommendJudge.setOnTop(1);
        }

        return toAjax(recommendJudgeService.insertRecommendJudge(recommendJudge));
    }

    /**
     * 修改首页裁判
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeByIdAssoc(id);
        mmap.put("recommendJudge", recommendJudge);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        return prefix + "/edit";
    }

    /**
     * 修改保存首页裁判
     */
    @RequiresPermissions("shooting:recommendJudge:edit")
    @Log(title = "首页裁判", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RecommendJudge recommendJudge
            , @RequestParam(value = "image1File", required = false) MultipartFile image1File
            , @RequestParam(value = "image2File", required = false) MultipartFile image2File
            , @RequestParam(value = "image3File", required = false) MultipartFile image3File
            , @RequestParam(value = "image4File", required = false) MultipartFile image4File
            , @RequestParam(value = "image5File", required = false) MultipartFile image5File
            , @RequestParam(value = "image6File", required = false) MultipartFile image6File
            , @RequestParam(value = "image7File", required = false) MultipartFile image7File
            , @RequestParam(value = "image8File", required = false) MultipartFile image8File
            , @RequestParam(value = "image9File", required = false) MultipartFile image9File
            , @RequestParam(value = "videoFile", required = false) MultipartFile videoFile
            , @RequestParam(value = "adMediaUrlFile", required = false) MultipartFile adMediaUrlFile
    ) {
        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setVideo(filePath);
                String videoCoverFilePath=recommendJudge.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                recommendJudge.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
            if (adMediaUrlFile != null && !adMediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(adMediaUrlFile, null,UploadTypeEnums.Recommend.getValue() , true);
                recommendJudge.setAdMediaUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        recommendJudge.setUpdateBy(ShiroUtils.getLoginName());
        recommendJudge.setContentShort( ServiceUtils.trimContentRichText( recommendJudge.getContent() ) );
        return toAjax(recommendJudgeService.updateRecommendJudge(recommendJudge));
    }

    /**
     * 删除首页裁判
     */
    @RequiresPermissions("shooting:recommendJudge:remove")
    @Log(title = "首页裁判", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(recommendJudgeService.deleteRecommendJudgeByIds(ids));
    }


    /**
     * 查询首页裁判分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody RecommendJudge recommendJudge) {
        startPage(recommendJudge.getPd());

        List<RecommendJudge> list = recommendJudgeService.selectRecommendJudgeListAssoc(recommendJudge);
        return getDataTable(list);
    }
}
