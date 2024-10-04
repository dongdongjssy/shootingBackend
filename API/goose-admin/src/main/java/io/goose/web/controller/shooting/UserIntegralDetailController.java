package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.domain.UserIntegral;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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
import io.goose.shooting.domain.UserIntegralDetail;
import io.goose.shooting.service.IUserIntegralDetailService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 用户电子积分卡明细 信息操作处理
 * 
 * @author goose
 * @date 2021-03-31
 */
@Controller
@RequestMapping("/shooting/userIntegralDetail")
public class UserIntegralDetailController extends BaseController
{

    private String prefix = "shooting/userIntegralDetail";
	
	@Autowired
	private IUserIntegralDetailService userIntegralDetailService;


	
	
	@RequiresPermissions("shooting:userIntegralDetail:view")
	@GetMapping()
	public String userIntegralDetail(ModelMap mmap)
	{
	    return prefix + "/userIntegralDetail";
	}
	
	/**
	 * 查询用户电子积分卡明细列表
	 */
	@RequiresPermissions("shooting:userIntegralDetail:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(UserIntegralDetail userIntegralDetail)
	{
		startPage();
        List<UserIntegralDetail> list = userIntegralDetailService.selectUserIntegralDetailListAssoc(userIntegralDetail);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出用户电子积分卡明细列表
	 *//*
	@RequiresPermissions("shooting:userIntegralDetail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserIntegralDetail userIntegralDetail)
    {
    	List<UserIntegralDetail> list = userIntegralDetailService.selectUserIntegralDetailList(userIntegralDetail);
        ExcelUtil<UserIntegralDetail> util = new ExcelUtil<UserIntegralDetail>(UserIntegralDetail.class);
        return util.exportExcel(list, "userIntegralDetail");
    }
    
    *//**
	 * 导入用户电子积分卡明细列表
	 *//*
	@RequiresPermissions("shooting:userIntegralDetail:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<UserIntegralDetail> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserIntegralDetail.class);
        	for(UserIntegralDetail obj : list) {
        		userIntegralDetailService.insertUserIntegralDetail(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	*/

	/**
	 * 导入用户电子积分卡明细
	 */
	@RequiresPermissions("shooting:userIntegralDetail:import")
	@PostMapping("/importUserIntegralDetailExcel")
	@ResponseBody
	public AjaxResult importUserIntegralDetailExcel(@RequestParam(value="file", required=false) MultipartFile excl)throws EncryptedDocumentException, InvalidFormatException
	{
		return dataCollation(excl);
	}



	public AjaxResult dataCollation(MultipartFile excl) throws EncryptedDocumentException, InvalidFormatException {
		try {
			Workbook workbook = WorkbookFactory.create(excl.getInputStream());
			//只查第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			//查询有几行需要导入的数据
			int first = sheet.getFirstRowNum() + 1;
			int last = sheet.getLastRowNum();
			List<UserIntegral> list = new ArrayList<UserIntegral>(16);
			int insertCount = 0;
			int updateCount = 0;
			List<String> strList = new ArrayList<>();

			for(int i = first; i<=last; i++) {

				StringBuffer str = new StringBuffer();


				Row row = sheet.getRow(i);
				if(row.getCell(1)==null){
					str.append("第"+ (i+1) + "行");
					strList.add(str.toString());
					continue;
				}
				String score="";
				if(row.getCell(4)!=null&&row.getCell(4).getCellTypeEnum().name().equals("NUMERIC")){
					DataFormatter dataFormatter=new DataFormatter();
					score=	dataFormatter.formatCellValue(row.getCell(4));
				}
				String rank="";
				if(row.getCell(3)!=null&&row.getCell(3).getCellTypeEnum().name().equals("NUMERIC")){
					DataFormatter dataFormatter=new DataFormatter();
					rank=	dataFormatter.formatCellValue(row.getCell(3));
				}else {
					rank=row.getCell(3).toString();
				}
				UserIntegralDetail userIntegralDetail=new UserIntegralDetail();
				userIntegralDetail.setMember(Optional.ofNullable(row.getCell(1).toString()).orElse(null));
				userIntegralDetail.setContest(Optional.ofNullable(row.getCell(2).toString()).orElse(null));
				List<UserIntegralDetail> userIntegralDetailList=userIntegralDetailService.selectUserIntegralDetailList(userIntegralDetail);
				userIntegralDetail.setName(Optional.ofNullable(row.getCell(0).toString()).orElse(null));
				userIntegralDetail.setRanking(Optional.ofNullable(rank).orElse(null));
				userIntegralDetail.setScore(Optional.ofNullable(score).orElse(null));
				if(userIntegralDetailList.isEmpty()){
					userIntegralDetailService.insertUserIntegralDetail(userIntegralDetail);
					insertCount++;
				}else {
					userIntegralDetailService.updateUserIntegralDetailByMember(userIntegralDetail);
					updateCount++;
				}
			}

			AjaxResult result = new AjaxResult();
			result.put("code", 0);
			if(strList.size() > 0) {
				result.put("msg","数据导入完成，,新增"+insertCount+"条数据，更新"+updateCount+"条数据,其中"+ strList.toString()+"射手编号为空，未能导入");
			}else {
				result.put("msg", "成功导入全部数据,新增"+insertCount+"条数据，更新"+updateCount+"条数据");
			}
			return result;

		}
		catch(IOException e) {
			return AjaxResult.error(e.getMessage());
		}

	}







	/**
	 * 新增用户电子积分卡明细
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户电子积分卡明细
	 */
	@RequiresPermissions("shooting:userIntegralDetail:add")
	@Log(title = "用户电子积分卡明细", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(UserIntegralDetail userIntegralDetail 
	)
	{		

		userIntegralDetail.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(userIntegralDetailService.insertUserIntegralDetail(userIntegralDetail));
	}

	/**
	 * 修改用户电子积分卡明细
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		UserIntegralDetail userIntegralDetail = userIntegralDetailService.selectUserIntegralDetailByIdAssoc(id);
		mmap.put("userIntegralDetail", userIntegralDetail);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户电子积分卡明细
	 */
	@RequiresPermissions("shooting:userIntegralDetail:edit")
	@Log(title = "用户电子积分卡明细", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UserIntegralDetail userIntegralDetail 
	)
	{		
		userIntegralDetail.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(userIntegralDetailService.updateUserIntegralDetail(userIntegralDetail));
	}
	
	/**
	 * 删除用户电子积分卡明细
	 */
	@RequiresPermissions("shooting:userIntegralDetail:remove")
	@Log(title = "用户电子积分卡明细", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(userIntegralDetailService.deleteUserIntegralDetailByIds(ids));
	}
	

	/**
	 * 查询用户电子积分卡明细分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody UserIntegralDetail userIntegralDetail)
	{
		startPage(userIntegralDetail.getPd());
		
        List<UserIntegralDetail> list = userIntegralDetailService.selectUserIntegralDetailListAssoc(userIntegralDetail);
		return getDataTable(list);
	}




	
}
