package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.domain.*;
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
import io.goose.shooting.service.IUserIntegralService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 用户电子积分卡 信息操作处理
 * 
 * @author goose
 * @date 2021-03-31
 */
@Controller
@RequestMapping("/shooting/userIntegral")
public class UserIntegralController extends BaseController
{

    private String prefix = "shooting/userIntegral";
	
	@Autowired
	private IUserIntegralService userIntegralService;


	
	
	@RequiresPermissions("shooting:userIntegral:view")
	@GetMapping()
	public String userIntegral(ModelMap mmap)
	{
	    return prefix + "/userIntegral";
	}
	
	/**
	 * 查询用户电子积分卡列表
	 */
	@RequiresPermissions("shooting:userIntegral:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(UserIntegral userIntegral)
	{
		startPage();
        List<UserIntegral> list = userIntegralService.selectUserIntegralListAssoc(userIntegral);
		return getDataTable(list);
	}

	/**
	 * 导入用户电子积分卡列表
	 */
	@RequiresPermissions("shooting:userIntegral:import")
	@PostMapping("/importUserIntegralExcel")
	@ResponseBody
	public AjaxResult importUserIntegralExcel(@RequestParam(value="file", required=false) MultipartFile excl)throws EncryptedDocumentException, InvalidFormatException
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
				String totalScore="";
				if(row.getCell(4)!=null&&row.getCell(4).getCellTypeEnum().name().equals("NUMERIC")){
					DataFormatter dataFormatter=new DataFormatter();
					totalScore=	dataFormatter.formatCellValue(row.getCell(4));
				}
				String teamNo="";
				if(row.getCell(4)!=null&&row.getCell(5).getCellTypeEnum().name().equals("NUMERIC")){
					DataFormatter dataFormatter=new DataFormatter();
					teamNo=	dataFormatter.formatCellValue(row.getCell(5));
				}else{
					teamNo=row.getCell(4).toString();
				}
				UserIntegral userIntegral=new UserIntegral();
				userIntegral.setMember(Optional.ofNullable(row.getCell(1).toString()).orElse(null));
				List<UserIntegral> userIntegralList=userIntegralService.selectUserIntegralList(userIntegral);
				userIntegral.setName(Optional.ofNullable(row.getCell(0).toString()).orElse(null));
				userIntegral.setAgeGroup(Optional.ofNullable(row.getCell(2).toString()).orElse(null));
				userIntegral.setGunGroup(Optional.ofNullable(row.getCell(3).toString()).orElse(null));
				userIntegral.setTotalScore(Optional.ofNullable(totalScore).orElse(null));
				userIntegral.setTeamNo(Optional.ofNullable(teamNo).orElse(null));
				if(userIntegralList.isEmpty()){
					userIntegralService.insertUserIntegral(userIntegral);
					insertCount++;
				}else {
					userIntegralService.updateUserIntegralByMember(userIntegral);
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
	 * 新增用户电子积分卡
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户电子积分卡
	 */
	@RequiresPermissions("shooting:userIntegral:add")
	@Log(title = "用户电子积分卡", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(UserIntegral userIntegral 
	)
	{		

		userIntegral.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(userIntegralService.insertUserIntegral(userIntegral));
	}

	/**
	 * 修改用户电子积分卡
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		UserIntegral userIntegral = userIntegralService.selectUserIntegralByIdAssoc(id);
		mmap.put("userIntegral", userIntegral);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户电子积分卡
	 */
	@RequiresPermissions("shooting:userIntegral:edit")
	@Log(title = "用户电子积分卡", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UserIntegral userIntegral 
	)
	{		
		userIntegral.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(userIntegralService.updateUserIntegral(userIntegral));
	}
	
	/**
	 * 删除用户电子积分卡
	 */
	@RequiresPermissions("shooting:userIntegral:remove")
	@Log(title = "用户电子积分卡", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(userIntegralService.deleteUserIntegralByIds(ids));
	}
	

	/**
	 * 查询用户电子积分卡分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody UserIntegral userIntegral)
	{
		startPage(userIntegral.getPd());
		
        List<UserIntegral> list = userIntegralService.selectUserIntegralListAssoc(userIntegral);
		return getDataTable(list);
	}




	
}
