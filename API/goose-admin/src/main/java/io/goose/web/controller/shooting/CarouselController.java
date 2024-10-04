package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import io.goose.common.page.PageDomain;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.Club;
import io.goose.shooting.service.*;
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
import io.goose.shooting.domain.Carousel;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.domain.Training;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 轮播媒体 信息操作处理
 * 
 * @author goose
 * @date 2020-06-17
 */
@Controller
@RequestMapping( "/shooting/carousel" )
public class CarouselController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger( CarouselController.class );

   @Autowired
   private Global global;

    private String prefix = "shooting/carousel";
	
	@Autowired
	private ICarouselService carouselService;

	@Autowired
	private IClubService clubService;

	@Autowired
	private IContestService contestService;

	@Autowired
	private ITrainingService trainingService;

	@Autowired
	private IRecommendCoachService recommendCoachService;

	@Autowired
	private IRecommendJudgeService recommendJudgeService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	@Autowired
	private IRecommendService recommendService;

	@Autowired
	private IClubPostService clubPostService;

	@Autowired
	private IClubActivityService clubActivityService;
	
	@RequiresPermissions("shooting:carousel:view")
	@GetMapping()
	public String carousel(ModelMap mmap)
	{
	    mmap.put("clubIdList", clubService.selectClubAll());
	    mmap.put("contestIdList", contestService.selectContestAll());
	    mmap.put("trainingIdList", trainingService.selectTrainingAll());
		mmap.put("coachIdList", recommendCoachService.selectRecommendCoachAll());
		mmap.put("judgeIdList", recommendJudgeService.selectRecommendJudgeAll());
		mmap.put("recommendIdList", recommendService.selectRecommendAll());
		mmap.put("clubPostIdList", clubPostService.selectClubPostAll());
		mmap.put("clubActivityIdList", clubActivityService.selectClubActivityAll());
	    return prefix + "/carousel";
	}
	
	/**
	 * 查询轮播媒体列表
	 */
	@RequiresPermissions("shooting:carousel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Carousel carousel)
	{
		startPage();
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = pageDomain.getOrderBy();
			if(orderBy==null||orderBy.equals("")){
				orderBy="create_time desc";
			}
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
        List<Carousel> list = carouselService.selectCarouselList(carousel);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出轮播媒体列表
	 */
	@RequiresPermissions("shooting:carousel:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Carousel carousel)
    {
    	List<Carousel> list = carouselService.selectCarouselList(carousel);
        ExcelUtil<Carousel> util = new ExcelUtil<Carousel>(Carousel.class);
        return util.exportExcel(list, "carousel");
    }
    
    /**
	 * 导入轮播媒体列表
	 */
	@RequiresPermissions("shooting:carousel:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Carousel> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Carousel.class);
        	for(Carousel obj : list) {
        		carouselService.insertCarousel(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增轮播媒体
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		List<Club> clubs = clubService.selectClubAll();
		List<Club> clubList = new LinkedList<>();
		Club noClub = new Club();
		noClub.setId(-1l);
		noClub.setTitle("请选择");
		clubList.add(noClub);
		clubList.addAll(clubs);
		mmap.put("clubIdList", clubList);

	    mmap.put("contestIdList", contestService.selectContestAllAssoc());
	    mmap.put("trainingIdList", trainingService.selectTrainingAllAssoc());
		mmap.put("coachIdList", recommendCoachService.selectRecommendCoachAllAssoc());
		mmap.put("judgeIdList", recommendJudgeService.selectRecommendJudgeAllAssoc());
		mmap.put("recommendIdList", recommendService.selectRecommendAll());
		mmap.put("clubPostIdList", clubPostService.selectClubPostAll());
		mmap.put("clubActivityIdList", clubActivityService.selectClubActivityAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存轮播媒体
	 */
	@RequiresPermissions("shooting:carousel:add")
	@Log(title = "轮播媒体", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Carousel carousel 
				 , @RequestParam(value="mediaUrlFile", required=false) MultipartFile mediaUrlFile
	)
	{		
		try {
			if (mediaUrlFile != null && !mediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(mediaUrlFile, null,UploadTypeEnums.Carousel.getValue() , true);
				carousel.setMediaUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		carousel.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(carouselService.insertCarousel(carousel));
	}

	/**
	 * 修改轮播媒体
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Carousel carousel = carouselService.selectCarouselById(id);
		mmap.put("carousel", carousel);
	    mmap.put("imageUrlPrefix", getImageUrlPrefix());

		List<Club> clubs = clubService.selectClubAll();
		List<Club> clubList = new LinkedList<>();
		Club noClub = new Club();
		noClub.setId(-1l);
		noClub.setTitle("请选择");
		clubList.add(noClub);
		clubList.addAll(clubs);
		mmap.put("clubIdList", clubList);

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
	 * 修改保存轮播媒体
	 */
	@RequiresPermissions("shooting:carousel:edit")
	@Log(title = "轮播媒体", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Carousel carousel 
				 , @RequestParam(value="mediaUrlFile", required=false) MultipartFile mediaUrlFile
	)
	{		
		try {
			if (mediaUrlFile != null && !mediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(mediaUrlFile, null,UploadTypeEnums.Carousel.getValue() , true);
				carousel.setMediaUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	
		carousel.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(carouselService.updateCarousel(carousel));
	}
	
	/**
	 * 删除轮播媒体
	 */
	@RequiresPermissions("shooting:carousel:remove")
	@Log(title = "轮播媒体", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(carouselService.deleteCarouselByIds(ids));
	}
	

	/**
	 * 查询轮播媒体分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Carousel carousel)
	{
		startPage(carousel.getPd());
		
        List<Carousel> list = carouselService.selectCarouselListAssoc(carousel);
		return getDataTable(list);
	}


}
