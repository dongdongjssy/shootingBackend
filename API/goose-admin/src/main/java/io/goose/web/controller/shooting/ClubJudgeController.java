package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.web.base.ClubBaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import io.goose.shooting.domain.ClubJudge;
import io.goose.shooting.service.IClubJudgeService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.IJudgeService;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 俱乐部裁判 信息操作处理
 * 
 * @author goose
 * @date 2021-01-15
 */
@Controller
@RequestMapping("/shooting/clubJudge")
public class ClubJudgeController extends ClubBaseController
{

    private String prefix = "shooting/clubJudge";
	
	@Autowired
	private IClubJudgeService clubJudgeService;

	@Autowired
	private IClubService clubService;

	@Autowired
	private IJudgeService judgeService;


	
	
	@RequiresPermissions("shooting:clubJudge:view")
	@GetMapping()
	public String clubJudge(ModelMap mmap)
	{
	    mmap.put("clubIdList", clubService.selectClubAll());
	    mmap.put("judgeIdList", judgeService.selectJudgeAll());
	    return prefix + "/clubJudge";
	}
	
	/**
	 * 查询俱乐部裁判列表
	 */
	@RequiresPermissions("shooting:clubJudge:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ClubJudge clubJudge)
	{
		startPage();
		clubJudge = setClubId(clubJudge);
        List<ClubJudge> list = clubJudgeService.selectClubJudgeListAssoc(clubJudge);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出俱乐部裁判列表
	 */
	@RequiresPermissions("shooting:clubJudge:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClubJudge clubJudge)
    {
    	List<ClubJudge> list = clubJudgeService.selectClubJudgeList(clubJudge);
        ExcelUtil<ClubJudge> util = new ExcelUtil<ClubJudge>(ClubJudge.class);
        return util.exportExcel(list, "clubJudge");
    }
    
    /**
	 * 导入俱乐部裁判列表
	 */
	@RequiresPermissions("shooting:clubJudge:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ClubJudge> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubJudge.class);
        	for(ClubJudge obj : list) {
        		clubJudgeService.insertClubJudge(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增俱乐部裁判
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    mmap.put("clubIdList", clubService.selectClubAll());
	    mmap.put("judgeIdList", judgeService.selectJudgeAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存俱乐部裁判
	 */
	@RequiresPermissions("shooting:clubJudge:add")
	@Log(title = "俱乐部裁判", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ClubJudge clubJudge 
	)
	{		

		clubJudge.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(clubJudgeService.insertClubJudge(clubJudge));
	}

	/**
	 * 修改俱乐部裁判
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ClubJudge clubJudge = clubJudgeService.selectClubJudgeByIdAssoc(id);
		mmap.put("clubJudge", clubJudge);
	    mmap.put("clubIdList", clubService.selectClubAll());
	    mmap.put("judgeIdList", judgeService.selectJudgeAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存俱乐部裁判
	 */
	@RequiresPermissions("shooting:clubJudge:edit")
	@Log(title = "俱乐部裁判", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ClubJudge clubJudge 
	)
	{		
		clubJudge.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(clubJudgeService.updateClubJudge(clubJudge));
	}
	
	/**
	 * 删除俱乐部裁判
	 */
	@RequiresPermissions("shooting:clubJudge:remove")
	@Log(title = "俱乐部裁判", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(clubJudgeService.deleteClubJudgeByIds(ids));
	}
	

	/**
	 * 查询俱乐部裁判分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ClubJudge clubJudge)
	{
		startPage(clubJudge.getPd());
		
        List<ClubJudge> list = clubJudgeService.selectClubJudgeListAssoc(clubJudge);
		return getDataTable(list);
	}




	
}
