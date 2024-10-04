package io.goose.web.controller.shooting;

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
import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.ClubPost;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IClubPostService;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.impl.ext.ServiceUtils;
import io.goose.system.domain.SysDictData;
import io.goose.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * 俱乐部动态 信息操作处理
 *
 * @author goose
 * @date 2020-05-09
 */
@Controller
@RequestMapping("/shooting/clubPost")
public class ClubPostController extends ClubBaseController {

    private static final Logger log = LoggerFactory.getLogger(ClubPostController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/clubPost";

    @Autowired
    private IClubPostService clubPostService;

    @Autowired
    private IClubService clubService;

    @Autowired
    private IClientUserService clientUserService;
    @Autowired
    private ISysDictDataService dictDataService;
    
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

    @RequiresPermissions("shooting:clubPost:view")
    @GetMapping()
    public String clubPost(ModelMap mmap) {
        Long clubId = getClubId();
        ClientUser cu = new ClientUser();
        cu.setClubId(clubId);
        //	   Club club =new Club();
        //	   club.setId(clubId);
        mmap.put("clientUserIdList", clubId != null ? clientUserService.selectClientUserList(cu) : clientUserService.selectClientUserAll());
        mmap.put("clubIdList", clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
        return prefix + "/clubPost";
    }


    /**
     * 查询俱乐部动态列表
     */
    @RequiresPermissions("shooting:clubPost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClubPost clubPost) {
        startPage();
        clubPost = setClubId(clubPost);
        List<ClubPost> list = clubPostService.selectClubPostListAssoc(clubPost);
        return getDataTable(list);
    }


    /**
     * 导出俱乐部动态列表
     */
    @RequiresPermissions("shooting:clubPost:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClubPost clubPost) {
        clubPost = setClubId(clubPost);
        List<ClubPost> list = clubPostService.selectClubPostList(clubPost);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
                List<Club> clubList = clubService.selectClubAll();
                for (SysDictData d : status) {
                    Integer aS = list.get(i).getStatus();
                    if (aS != null) {
                        if (d.getDictValue().equals(aS.toString())) {
                            list.get(i).setStatusName(d.getDictLabel());
                        }
                    }
                }
                for (Club club : clubList) {
                    if (club.getId() == list.get(i).getClubId()) {
                        list.get(i).setClubName(club.getTitle());
                    }
                }
            }

        }
        ExcelUtil<ClubPost> util = new ExcelUtil<ClubPost>(ClubPost.class);
        return util.exportExcel(list, "clubPost");
    }


    /**
     * 导入俱乐部动态列表
     */
    @RequiresPermissions("shooting:clubPost:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<ClubPost> list =
                    ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubPost.class);
            for (ClubPost obj : list) {
                obj = setClubId(obj);
                clubPostService.insertClubPost(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }


    /**
     * 新增俱乐部动态
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Long clubId = getClubId();
        ClientUser cu = new ClientUser();
        cu.setClubId(clubId);
        Club club = new Club();
        club.setId(clubId);
        mmap.put("clientUserIdList", clubId != null ? clientUserService.selectClientUserList(cu) : clientUserService.selectClientUserAll());
        mmap.put("clubIdList", clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
        return prefix + "/add";
    }


    /**
     * 新增保存俱乐部动态
     */
    @RequiresPermissions("shooting:clubPost:add")
    @Log(title = "俱乐部动态", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ClubPost clubPost,
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
        if (image1File == null && image2File == null && image3File == null && image4File == null && image5File == null &&
        image6File == null && image7File == null && image8File == null && image9File == null && videoFile == null) {
            return error("请上传至少一张图片或视频");
        }
        try {

            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setVideo(filePath);
                String videoCoverFilePath=clubPost.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                clubPost.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        clubPost = setClubId(clubPost);
        clubPost.setCreateBy(ShiroUtils.getLoginName());
        clubPost.setContentShort(ServiceUtils.trimContentRichText(clubPost.getContent()));

        return toAjax(clubPostService.insertClubPost(clubPost));
    }


    /**
     * 修改俱乐部动态
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ClubPost clubPost = clubPostService.selectClubPostByIdAssoc(id);
        Long clubId = clubPost.getClubId();
        ClientUser cu = new ClientUser();
        cu.setClubId(clubId);
        Club club = new Club();
        club.setId(clubId);
        mmap.put("clientUserIdList", clubId != null ? clientUserService.selectClientUserList(cu) : clientUserService.selectClientUserAll());
        mmap.put("clubIdList", clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
        mmap.put("clubPost", clubPost);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        return prefix + "/edit";
    }


    /**
     * 修改保存俱乐部动态
     */
    @RequiresPermissions("shooting:clubPost:edit")
    @Log(title = "俱乐部动态", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ClubPost clubPost,
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
        if (image1File == null && image2File == null && image3File == null && image4File == null && image5File == null &&
                image6File == null && image7File == null && image8File == null && image9File == null && videoFile == null) {
            return error("请上传至少一张图片或视频");
        }

        try {
            if (image1File != null && !image1File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage1(filePath);
            }
            if (image2File != null && !image2File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage2(filePath);
            }
            if (image3File != null && !image3File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage3(filePath);
            }
            if (image4File != null && !image4File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage4(filePath);
            }
            if (image5File != null && !image5File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage5(filePath);
            }
            if (image6File != null && !image6File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage6(filePath);
            }
            if (image7File != null && !image7File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage7(filePath);
            }
            if (image8File != null && !image8File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage8(filePath);
            }
            if (image9File != null && !image9File.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setImage9(filePath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.ClubPost.getValue() , true);
                clubPost.setVideo(filePath);
                String videoCoverFilePath=clubPost.getVideo()+"?spm=a2c4g.11186623.2.1.yjOb8V&x-oss-process=video/snapshot,t_1,f_jpg,w_800,h_600,m_fast";
                clubPost.setVideoCover(videoCoverFilePath);
                log.debug("获取视频封面... " + videoCoverFilePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        clubPost.setUpdateBy(ShiroUtils.getLoginName());
        clubPost.setContentShort(ServiceUtils.trimContentRichText(clubPost.getContent()));
        return toAjax(clubPostService.updateClubPost(clubPost));
    }


    /**
     * 删除俱乐部动态
     */
    @RequiresPermissions("shooting:clubPost:remove")
    @Log(title = "俱乐部动态", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(clubPostService.deleteClubPostByIds(ids));
    }


    /**
     * 查询俱乐部动态分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody ClubPost clubPost) {
        startPage(clubPost.getPd());
        clubPost = setClubId(clubPost);
        List<ClubPost> list = clubPostService.selectClubPostListAssoc(clubPost);
        return getDataTable(list);
    }
}
