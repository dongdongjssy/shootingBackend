package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.github.pagehelper.PageHelper;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.page.PageDomain;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.shooting.domain.StartAdvertisement;
import io.goose.shooting.service.IStartAdvertisementService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.IContestService;
import io.goose.shooting.service.ITrainingService;
import io.goose.shooting.service.IRecommendCoachService;
import io.goose.shooting.service.IRecommendJudgeService;
import io.goose.shooting.service.IRecommendService;
import io.goose.shooting.service.IClubPostService;
import io.goose.shooting.service.IClubActivityService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 启动页广告 信息操作处理
 * 
 * @author goose
 * @date 2021-01-06
 */
@Controller
@RequestMapping("/shooting/startAdvertisement")
public class StartAdvertisementController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(StartAdvertisementController.class);
    
    @Autowired
    private Global global;

    private String prefix = "shooting/startAdvertisement";
	
	@Autowired
	private IStartAdvertisementService startAdvertisementService;

	@Autowired
	private IContestService contestService;

	@Autowired
	private ITrainingService trainingService;

	@Autowired
	private IRecommendCoachService recommendCoachService;

	@Autowired
	private IRecommendJudgeService recommendJudgeService;

	@Autowired
	private IRecommendService recommendService;

	@Autowired
	private IClubPostService clubPostService;

	@Autowired
	private IClubActivityService clubActivityService;
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;


	
	
	@RequiresPermissions("shooting:startAdvertisement:view")
	@GetMapping()
	public String startAdvertisement(ModelMap mmap)
	{
	    mmap.put("contestIdList", contestService.selectContestAll());
	    mmap.put("trainingIdList", trainingService.selectTrainingAll());
	    mmap.put("coachIdList", recommendCoachService.selectRecommendCoachAll());
	    mmap.put("judgeIdList", recommendJudgeService.selectRecommendJudgeAll());
	    mmap.put("recommendIdList", recommendService.selectRecommendAll());
	    mmap.put("clubPostIdList", clubPostService.selectClubPostAll());
	    mmap.put("clubActivityIdList", clubActivityService.selectClubActivityAll());
	    return prefix + "/startAdvertisement";
	}
	
	/**
	 * 查询启动页广告列表
	 */
	@RequiresPermissions("shooting:startAdvertisement:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(StartAdvertisement startAdvertisement)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = pageDomain.getOrderBy();
			if(orderBy==null||orderBy.equals("")){
				orderBy="effect_time desc";
			}
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
        List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementListAssoc(startAdvertisement);
		return getDataTable(list);
	}

	
	/**
	 * 导出启动页广告列表
	 */
	@RequiresPermissions("shooting:startAdvertisement:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StartAdvertisement startAdvertisement)
    {
    	List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementList(startAdvertisement);
        ExcelUtil<StartAdvertisement> util = new ExcelUtil<StartAdvertisement>(StartAdvertisement.class);
        return util.exportExcel(list, "startAdvertisement");
    }
    
    /**
	 * 导入启动页广告列表
	 */
	@RequiresPermissions("shooting:startAdvertisement:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<StartAdvertisement> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), StartAdvertisement.class);
        	for(StartAdvertisement obj : list) {
        		startAdvertisementService.insertStartAdvertisement(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增启动页广告
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    mmap.put("contestIdList", contestService.selectContestAll());
	    mmap.put("trainingIdList", trainingService.selectTrainingAll());
	    mmap.put("coachIdList", recommendCoachService.selectRecommendCoachAll());
	    mmap.put("judgeIdList", recommendJudgeService.selectRecommendJudgeAll());
	    mmap.put("recommendIdList", recommendService.selectRecommendAll());
	    mmap.put("clubPostIdList", clubPostService.selectClubPostAll());
	    mmap.put("clubActivityIdList", clubActivityService.selectClubActivityAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存启动页广告
	 */
	@RequiresPermissions("shooting:startAdvertisement:add")
	@Log(title = "启动页广告", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(StartAdvertisement startAdvertisement 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{
		List<StartAdvertisement> ss=	startAdvertisementService.findDataByTime(startAdvertisement);
		if(!ss.isEmpty()){
			StringBuilder sb=new StringBuilder();
			ss.forEach(s->{
				sb.append(s.getDescribes()+",");
			});
			return AjaxResult.error("时间段与所选时间段冲突,冲突数据条数："+ss.size()+",描述是："+sb.toString());
		}
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				Integer length=pictureUrlFile.getOriginalFilename().length();
				String last=pictureUrlFile.getOriginalFilename().substring(length-3,length);
				String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.StartAdvertisement.getValue() , true);

				startAdvertisement.setPictureUrl(filePath);
				/*if(last.equals("gif")){
					filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.StartAdvertisement.getValue() , true);

					//filePath = FileUploadUtils.uploadGif(global.getMediaPathStartAdvertisement(), pictureUrlFile);
					startAdvertisement.setPictureUrl(filePath);
				}else {
					//filePath = FileUploadUtils.upload(global.getMediaPathStartAdvertisement(), pictureUrlFile);
					startAdvertisement.setPictureUrl(filePath);
				}*/
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		startAdvertisement.setCreateBy(ShiroUtils.getLoginName());
		startAdvertisement.setUploadTime(new Date());
		return toAjax(startAdvertisementService.insertStartAdvertisement(startAdvertisement));
	}

	/**
	 * 修改启动页广告
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		StartAdvertisement startAdvertisement = startAdvertisementService.selectStartAdvertisementByIdAssoc(id);
		mmap.put("startAdvertisement", startAdvertisement);
	    mmap.put("imageUrlPrefix", getImageUrlPrefix());
	    mmap.put("contestIdList", contestService.selectContestAll());
	    mmap.put("trainingIdList", trainingService.selectTrainingAll());
	    mmap.put("coachIdList", recommendCoachService.selectRecommendCoachAll());
	    mmap.put("judgeIdList", recommendJudgeService.selectRecommendJudgeAll());
	    mmap.put("recommendIdList", recommendService.selectRecommendAll());
	    mmap.put("clubPostIdList", clubPostService.selectClubPostAll());
	    mmap.put("clubActivityIdList", clubActivityService.selectClubActivityAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存启动页广告
	 */
	@RequiresPermissions("shooting:startAdvertisement:edit")
	@Log(title = "启动页广告", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(StartAdvertisement startAdvertisement 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				Integer length=pictureUrlFile.getOriginalFilename().length();
				String last=pictureUrlFile.getOriginalFilename().substring(length-3,length);
				String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.StartAdvertisement.getValue() , true);

				startAdvertisement.setPictureUrl(filePath);
				startAdvertisement.setUploadTime(new Date());
				/*String filePath="";
				if(last.equals("gif")){
					filePath = FileUploadUtils.uploadGif(global.getMediaPathStartAdvertisement(), pictureUrlFile);
					startAdvertisement.setPictureUrl(filePath);
				}else {
					filePath = FileUploadUtils.upload(global.getMediaPathStartAdvertisement(), pictureUrlFile);
					startAdvertisement.setPictureUrl(filePath);
				}*/
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	
		startAdvertisement.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(startAdvertisementService.updateStartAdvertisement(startAdvertisement));
	}
	
	/**
	 * 删除启动页广告
	 */
	@RequiresPermissions("shooting:startAdvertisement:remove")
	@Log(title = "启动页广告", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(startAdvertisementService.deleteStartAdvertisementByIds(ids));
	}
	

	/**
	 * 查询启动页广告分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody StartAdvertisement startAdvertisement)
	{
		startPage(startAdvertisement.getPd());
		
        List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementListAssoc(startAdvertisement);
		return getDataTable(list);
	}




	
}
