package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframewor.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.domain.ContestContestRanking;
import io.goose.shooting.domain.ContestContestRankingCoeff;
import io.goose.shooting.domain.ContestGroup;
import io.goose.shooting.domain.ContestRankingCoeff;
import io.goose.shooting.domain.GroupAndRanking;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IContestContestRankingCoeffService;
import io.goose.shooting.service.IContestContestRankingService;
import io.goose.shooting.service.IContestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 成绩 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@RestController
@RequestMapping("/shooting/contestContestRanking")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "成绩 ",description="成绩")
public class ContestContestRankingRestController extends BaseController
{

	@Autowired
	private IContestContestRankingService contestContestRankingService;
	@Autowired
	private IContestService contestService;
	
	@Autowired
	private IContestContestRankingCoeffService coeffService;
	
	@Autowired
	private IClientUserService clientUserService;

	
	  /**
		 * 导入成绩列表
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
		 */
	    @PostMapping("/importByContestId")
	    @ResponseBody
	    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl) throws EncryptedDocumentException, InvalidFormatException
	    {
	    	try {
				Workbook workbook = WorkbookFactory.create(excl.getInputStream());
				//只查第一个sheet
				Sheet sheet = workbook.getSheetAt(0);
				//查询有几行需要导入的数据
				int first = sheet.getFirstRowNum() + 1;
				int last = sheet.getLastRowNum();
				
				List<ContestContestRanking> list = new ArrayList<ContestContestRanking>();

				List<String> strList = new ArrayList<>();

				for(int i = first; i<=last; i++) {
					
					ContestContestRanking ranking = new ContestContestRanking();
					ranking.setCreateBy("1");
					Row row = sheet.getRow(i);


					int lastCellNum = row.getLastCellNum();

					
					double d = row.getCell(0).getNumericCellValue();
					double dx = row.getCell(1).getNumericCellValue();
					ClientUser uu = clientUserService.selectClientUserById(Double.valueOf(d).longValue());

					if(row.getCell(6).toString().equals("1.0")) {
						ranking.setClientUserId(Double.valueOf(d).longValue());
						ranking.setContestId(Double.valueOf(dx).longValue());
						ranking.setGroupId(Double.valueOf(row.getCell(2).getNumericCellValue()).longValue());
						ranking.setCpsaRank(999);
						ranking.setTotalRank(999);
						ranking.setScore(0.00);
						ranking.setPercentage(0.00);
						ranking.setAvgCoeff(1.00);
						ranking.setAvgTime(0.0);
						ranking.setAvgScore(0.0);
						ranking.setAgeGroup(1);
						ranking.setIsDq(1);
						ranking.setImportName(uu.getRealName());
					}else {
						ranking.setClientUserId(Double.valueOf(d).longValue());
						ranking.setContestId(Double.valueOf(dx).longValue());
						ranking.setGroupId(Double.valueOf(row.getCell(2).getNumericCellValue()).longValue());
						
						ranking.setCpsaRank(Double.valueOf(row.getCell(3).toString()).intValue());
						ranking.setTotalRank(Double.valueOf(row.getCell(3).toString()).intValue());
						ranking.setScore(Double.valueOf(row.getCell(4).toString()));
						ranking.setPercentage(Double.valueOf(row.getCell(5).toString()));
						ranking.setAvgCoeff(1.00);
						ranking.setAvgTime(0.0);
						ranking.setAvgScore(0.0);
						ranking.setAgeGroup(1);
						ranking.setIsDq(0);
						ranking.setImportName(uu.getRealName());
					}
					
					contestContestRankingService.insertContestContestRanking(ranking);
				}	
				return AjaxResult.success();
				
	        }
	        catch(IOException e) {
	        	return AjaxResult.error(e.getMessage());
	        }
	   }

	
	/**
	 * 首页赛事排名
	 */
	@PostMapping("/contestRanking")
	@ApiOperation(value="首页赛事排名")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "首页赛事排名", required = false, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public  Map<String,Object> contestRanking( @RequestBody  ContestContestRanking contestContestRanking)
	{
		 Map<String,Object> garList = contestContestRankingService.contestRanking(contestContestRanking);
		return garList;
	}
	
	/**
	 * 赛事页面跳成绩
	 */
	@PostMapping("/contestRankingByContestId")
	@ApiOperation(value="赛事页面跳成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "首页赛事排名", required = false, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public  Map<String,Object> contestRankingByContestId( @RequestBody  ContestContestRanking contestContestRanking)
	{
		 Map<String,Object> garList = contestContestRankingService.contestRankingByContestId(contestContestRanking);
		return garList;
	}

	/**
	 * 查询成绩列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询成绩列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "成绩", required = false, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestContestRanking contestContestRanking)
	{
		startPage();
		
        List<ContestContestRanking> list = contestContestRankingService.selectContestContestRankingList(contestContestRanking);
		return getDataTable(list);
	}
	
	/**
	 * 根据ID查询
	 * */
	@PostMapping("/getById/{id}")
	@ApiOperation(value=" 根据ID查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Long")})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")		
	public ContestContestRanking getById( @PathVariable("id") Long id) {
		
		ContestContestRanking contestContestRanking = contestContestRankingService.selectContestContestRankingById(id);
		
		ContestContestRankingCoeff contestContestRankingCoeff = new ContestContestRankingCoeff();
		contestContestRankingCoeff.setMarkId(contestContestRanking.getId());
		ClientUser clientUser = clientUserService.selectClientUserById(contestContestRanking.getClientUserId());
	
		contestContestRanking.setImportName(clientUser.getRealName());
		List<ContestContestRankingCoeff> coeffList = coeffService.selectContestContestRankingCoeffList(contestContestRankingCoeff);
		String [] item = new String[coeffList.size()];
		
		if(item.length > 0) {
			for(int i = 0 ;i < item.length; i++) {
				item[i] = coeffList.get(i).getCoefficient().toString();
			}
		}

		contestContestRanking.setItem(item);
//		contestContestRanking.setContestRankingCoeff(coeffList);
		
		return contestContestRanking;
	}
	
	/**
	 * 导出成绩列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出成绩列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "成绩", required = false, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestContestRanking contestContestRanking)
    {
    	List<ContestContestRanking> list = contestContestRankingService.selectContestContestRankingList(contestContestRanking);
        ExcelUtil<ContestContestRanking> util = new ExcelUtil<ContestContestRanking>(ContestContestRanking.class);
        return util.exportExcel(list, "contestContestRanking");
    } */
    
    /**
	 * 导入成绩列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入成绩列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "成绩文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestContestRanking> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestContestRanking.class);
        	for(ContestContestRanking obj : list) {
        		contestContestRankingService.insertContestContestRanking(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存成绩
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "成绩", required = true, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestContestRanking contestContestRanking)
	{
		return toAjax(contestContestRankingService.insertContestContestRanking(contestContestRanking));
	}
	
	/**
	 * 修改保存成绩
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "成绩", required = true, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestContestRanking contestContestRanking)
	{
		return toAjax(contestContestRankingService.updateContestContestRanking(contestContestRanking));
	}
	
	/**
	 * 删除成绩
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestContestRankingService.deleteContestContestRankingByIds(ids));
	}
	
		/**
	 * 根据id删除成绩
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestContestRankingService.deleteContestContestRankingById(id));
	}
	
	
		/**
	 * 查询成绩分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询成绩分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRanking", value = "成绩", required = true, dataType = "ContestContestRanking")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestContestRanking contestContestRanking)
	{
		startPage(contestContestRanking.getPd());	
        List<ContestContestRanking> list = contestContestRankingService.selectContestContestRankingList(contestContestRanking);
		return getDataTable(list);
	}
	



	
}
