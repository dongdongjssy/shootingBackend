package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.domain.ContestContestRanking;
import io.goose.shooting.domain.ContestContestRankingCoeff;
import io.goose.shooting.domain.ContestGroup;
import io.goose.shooting.domain.ContestLevelCoeff;
import io.goose.shooting.domain.ContestRankingCoeff;
import io.goose.shooting.domain.ContestRankingCoeffDefault;
import io.goose.shooting.domain.ContestStats;
import io.goose.shooting.mapper.ContestContestRankingMapper;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IContestContestRankingCoeffService;
import io.goose.shooting.service.IContestContestRankingService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.IContestService;
import io.goose.shooting.service.IContestStatsService;
import io.goose.system.service.ISysDictDataService;
import io.goose.shooting.service.IContestGroupService;
import io.goose.shooting.service.IContestLevelCoeffService;
import io.goose.shooting.service.IContestRankingCoeffDefaultService;
import io.goose.shooting.service.IContestRankingCoeffService;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 成绩 信息操作处理
 *
 * @author goose
 * @date 2020-05-27
 */
@Controller
@RequestMapping("/shooting/contestContestRanking")
public class ContestContestRankingController extends BaseController
{

    private String prefix = "shooting/contestContestRanking";

	@Autowired
	private IContestContestRankingService contestContestRankingService;

	@Autowired
	private IContestService contestService;

	@Autowired
	private IContestGroupService contestGroupService;

	@Autowired
	private IClientUserService clientUserService;

	@Autowired
	private ISysDictDataService dictDataService;
	
	@Autowired
	private IContestLevelCoeffService levelService;
	
	@Autowired
	private IContestRankingCoeffService rankingService;
	
	@Autowired
	private IContestRankingCoeffDefaultService rankingDefaultService;

	@Autowired
	private IContestContestRankingCoeffService rankingCoeffService;
	
	@Autowired
	private IContestStatsService contestStatsService;

	private int DATA_NUM = 1;   //表头有几行不处理数据

	private int CELL_NOTE = 13; //除 动态参数外 最后一行数




	@RequiresPermissions("shooting:contestContestRanking:view")
	@GetMapping()
	public String contestContestRanking(ModelMap mmap)
	{
		
	    mmap.put("contestIdList", contestService.selectContestListAssoc(new Contest()));
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
		mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
	    return prefix + "/contestContestRanking";
	}

	/**
	 * 查询成绩列表
	 */
	@RequiresPermissions("shooting:contestContestRanking:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestContestRanking contestContestRanking)
	{
		startPage();
        List<ContestContestRanking> list = contestContestRankingService.selectContestContestRankingListAssoc(contestContestRanking);
		return getDataTable(list);
	}


//	/**
//	 * 导出成绩列表
//	 */
//	@RequiresPermissions("shooting:contestContestRanking:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(ContestContestRanking contestContestRanking)
//    {
//    	List<ContestContestRanking> list = contestContestRankingService.selectContestContestRankingList(contestContestRanking);
//        ExcelUtil<ContestContestRanking> util = new ExcelUtil<ContestContestRanking>(ContestContestRanking.class);
//        return util.exportExcel(list, "contestContestRanking");
//    }
//
//    /**
//	 * 导入成绩列表
//	 */
//	@RequiresPermissions("shooting:contestContestRanking:import")
//    @PostMapping("/import")
//    @ResponseBody
//    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
//    {
//    	try {
//        	List<ContestContestRanking> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestContestRanking.class);
//        	for(ContestContestRanking obj : list) {
//        		contestContestRankingService.insertContestContestRanking(obj);
//        	}
//        }
//        catch(ExcelUtilException | IOException e) {
//        	return AjaxResult.error(e.getMessage());
//        }
//
//        return AjaxResult.success("成功导入全部数据");
//
//    }

  /**
	 * 导入成绩列表
 * @throws InvalidFormatException
 * @throws EncryptedDocumentException
	 */
    @PostMapping("/importByContestId")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl,@RequestParam(value="contestId", required=false) Long contestId) throws EncryptedDocumentException, InvalidFormatException
    {
    	AjaxResult result = dataCollation(excl,contestId);

	    return result;

   }

    public AjaxResult dataCollation(MultipartFile excl,Long contestId) throws EncryptedDocumentException, InvalidFormatException {
		try {
			Workbook workbook = WorkbookFactory.create(excl.getInputStream());
			//只查第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			//查询有几行需要导入的数据
			int first = sheet.getFirstRowNum() + DATA_NUM;
			int last = sheet.getLastRowNum();
			List<ContestContestRanking> list = new ArrayList<ContestContestRanking>();


			Long userId = ShiroUtils.getUserId();

			List<String> strList = new ArrayList<>();

			Calendar c = Calendar.getInstance();

			Double point = new Double(1);
			
			for(int i = first; i<=last; i++) {
				int count = 0;
				StringBuffer str = new StringBuffer();
				str.append("第"+ (i+1) + "行,");

				ContestContestRanking ranking = new ContestContestRanking();
				ranking.setContestId(contestId);
				ranking.setCreateBy(userId.toString());
				Row row = sheet.getRow(i);


				int lastCellNum = row.getLastCellNum();

		
				

				//判断组别逻辑
				if(row.getCell(0) == null) {
					ranking.setGroupId(null);
				}else {
					ContestGroup contestGroup = new ContestGroup();
					contestGroup.setName(row.getCell(0).toString());
					List<ContestGroup> groupList = contestGroupService.selectContestGroupList(contestGroup);
					if(groupList.size() > 0) {
						ranking.setGroupId(groupList.get(0).getId());
					}else {
						str.append("未找到名为" + row.getCell(0).toString() +"的组别，");
						count = count + 1;
					}
				}

				double d = 0.00;
				//判断CPSA名次
				if(row.getCell(1) == null) {
					str.append("CPSA名次不能为空，");
					count = count + 1;
				}else {
					if(row.getCell(1).getNumericCellValue()==0){
						ranking.setCpsaRank(999999);
					}else {
						d = row.getCell(1).getNumericCellValue();
						ranking.setCpsaRank((new Double(d)).intValue());
					}
				}

				//判断总名次
				if(row.getCell(2) == null) {
					ranking.setTotalRank(999999);
					str.append("总名次不能为空，");
					count = count + 1;
				}else {
					if(row.getCell(1).getNumericCellValue()==0){
						ranking.setTotalRank(999999);
					}else {
					d = row.getCell(2).getNumericCellValue();
					ranking.setTotalRank((new Double(d)).intValue());
					}
				}

				//射手编号  
				if(row.getCell(3) == null) {
					str.append("射手编号不能为空,");
					count = count + 1;
				}else {
					ClientUser clientUser = new ClientUser();
					String memberId = row.getCell(3).toString();
					if(memberId.indexOf(".") != -1) {
						memberId = memberId.substring(0,memberId.length() - 2);
					}
					clientUser.setMemberId(memberId);
					List<ClientUser> userList = clientUserService.selectClientUserList(clientUser);
					if(userList.size() > 0) {
						ranking.setClientUserId(userList.get(0).getId());
						ContestContestRanking con = new ContestContestRanking();
						con.setClientUserId(ranking.getClientUserId());
						con.setContestId(contestId);
						con.setGroupId(ranking.getGroupId());
						List<ContestContestRanking> ll = contestContestRankingService.selectContestContestRankingListAssoc(con);
						if(ll.size() > 0) {
							continue;
						}
					}else {
						str.append("未找到编号为" + row.getCell(3).toString() +"的射手，");
						count = count + 1;
					}
				}

				//判断姓名
				if(row.getCell(4) == null) {
					ranking.setImportName(null);
				}else {
					ranking.setImportName(row.getCell(4).toString());
				}

				//判断分数
				if(row.getCell(5) == null) {
					ranking.setScore(0.00);
					str.append("分数不能为空，");
					count = count + 1;
				}else {
					ranking.setScore(row.getCell(5).getNumericCellValue());
				}

				//判断百分比
				if(row.getCell(6) == null) {
					ranking.setPercentage(new Double(0));
				}else {
					d = row.getCell(6).getNumericCellValue();
					ranking.setPercentage((new Double(d)));
				}

				//判断平均系数
				if(row.getCell(7) == null) {
					ranking.setAvgCoeff(0.00);
				}else {
					ranking.setAvgCoeff(row.getCell(7).getNumericCellValue());
				}

				//判断平均时间
				if(row.getCell(8) == null) {
					ranking.setAvgTime(null);
				}else {
					ranking.setAvgTime(row.getCell(8).getNumericCellValue());
				}

				//判断平均得分
				if(row.getCell(9) == null) {
					ranking.setAvgScore(0.00);
				}else {
					ranking.setAvgScore(row.getCell(9).getNumericCellValue());
				}

				//判断年龄组别
				if(row.getCell(10) == null) {
					str.append("年龄组别不能为空,");
					count = count + 1;
				}else {
					String value = dictDataService.selectDictValue("age_group", row.getCell(10).toString());
					if(value == null) {
						str.append("未找到名为" + row.getCell(10).toString() +"的年龄组别，");
						count = count + 1;
					}
					ranking.setAgeGroup(Integer.valueOf(value));
				}

				//判断是否DQ
				if(row.getCell(11) == null) {
					str.append("是否DQ不能为空,");
					count = count + 1;
				}else {
					String value = dictDataService.selectDictValue("is_dq", row.getCell(11).toString());
					if(value == null) {
						str.append("未找到名为" + row.getCell(11).toString() +"的是否DQ，");
						count = count + 1;
					}
					ranking.setIsDq(Integer.valueOf(value));
				}

				//判断备注
				if(row.getCell(12) != null) {
					ranking.setNote(row.getCell(12).toString());
				}

				if(count > 0) {
					strList.add(str.toString());
					continue;
				}else {				
					//先查出当前赛事
					
					Contest contest = contestService.selectContestById(contestId);
					
					//再差对应级别系数  不存在为0
					
					ContestLevelCoeff level =  levelService.selectContestLevelCoeffById(contest.getLevelId());
					Double levelCoeff = new Double(0);
					if(level != null) {
						levelCoeff = level.getLevelCoeff();
					}
					
					//再查名次系数  不存在则查 名次基础系数   再不存在为0
					ContestRankingCoeff contestRankingCoeff = new ContestRankingCoeff();
					c.setTime(contest.getStartDate());
					c.get(Calendar.YEAR);
					contestRankingCoeff.setYear(Long.valueOf(c.get(Calendar.YEAR)));
					contestRankingCoeff.setCourseId(contest.getCourseId());
					contestRankingCoeff.setTypeId(contest.getTypeId());
					contestRankingCoeff.setGroupId(ranking.getGroupId());
					contestRankingCoeff.setRank(ranking.getCpsaRank());
					List<ContestRankingCoeff> mcxsList = rankingService.selectContestRankingCoeffList(contestRankingCoeff);
					Double mcCoeff = new Double(0);
					if(mcxsList.size() > 0) {
						mcCoeff = mcxsList.get(0).getRankCoeff();
					}else {
						ContestRankingCoeffDefault contestRankingCoeffDefault = new ContestRankingCoeffDefault();
						contestRankingCoeffDefault.setYear(Long.valueOf(c.get(Calendar.YEAR)));
						contestRankingCoeffDefault.setCourseId(contest.getCourseId());
						contestRankingCoeffDefault.setTypeId(contest.getTypeId());
						contestRankingCoeffDefault.setGroupId(ranking.getGroupId());
						List<ContestRankingCoeffDefault> mcxsmrList = rankingDefaultService.selectContestRankingCoeffDefaultList(contestRankingCoeffDefault);
						if(mcxsmrList.size() > 0) {
							mcCoeff = mcxsmrList.get(0).getRankCoeff();
						}
					}
					
					point = levelCoeff * mcCoeff;
					if(ranking.getIsDq() == 1) {
						point = 0.00;
					}
					ranking.setPoint(point);
					
					int success = contestContestRankingService.insertContestContestRanking(ranking);
					if(success == 0) {
						AjaxResult.error("新增成绩失败，请联系管理人员处理");
					}else {
						ContestContestRankingCoeff contestContestRankingCoeff = new ContestContestRankingCoeff();
						contestContestRankingCoeff.setMarkId(ranking.getId());
						contestContestRankingCoeff.setClientUserId(ranking.getClientUserId());
						for(int x = 13; x < lastCellNum; x++) {
							contestContestRankingCoeff.setId(null);
							if(row.getCell(x) == null) {
								contestContestRankingCoeff.setCoefficient(0.00);
								rankingCoeffService.insertContestContestRankingCoeff(contestContestRankingCoeff);
							}else {
								contestContestRankingCoeff.setCoefficient(row.getCell(x).getNumericCellValue());
								rankingCoeffService.insertContestContestRankingCoeff(contestContestRankingCoeff);
							}
						}
					}
					
					
					//接下来是要把这条数据记录到排名表
//					ContestStats s = new ContestStats();
//					s.setYear(c.get(Calendar.YEAR));
//					s.setClientUserId(ranking.getClientUserId());
//					List<ContestStats> statsList = contestStatsService.selectContestStatsList(s);
//					if(statsList.size() > 0) {
//						//修改
//					}else {
//						//新增
//						s.setCourseId(contest.getCourseId());
//						s.setTypeId(contest.getTypeId());
//						s.setContestGroupId(ranking.getGroupId());
//						s.setAgeGroup(ranking.getAgeGroup());
//						s.setPoint(point);
//						s.setTotalScore(point);
//						s.setTotalCount(1);
//						s.setTotalAvgScore(point);
//						s.setBestAvgScore(0);
//						s.setBestCount(0);
//						s.setBestScore(0.00);
//						
//					}
					
					
				}

			}

			AjaxResult result = new AjaxResult();
			result.put("code", 0);
			if(strList.size() > 0) {
				result.put("msg", strList.toString());
			}else {
				result.put("msg", "成功导入全部数据");
			}
			return result;

        }
        catch(IOException e) {
        	return AjaxResult.error(e.getMessage());
        }

	}

	/**
	 * 新增成绩
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    mmap.put("contestIdList", contestService.selectContestListAssoc(new Contest()));
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/add";
	}

	/**
	 * 新增保存成绩
	 */
	@RequiresPermissions("shooting:contestContestRanking:add")
	@Log(title = "成绩", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestContestRanking contestContestRanking
	)
	{

		contestContestRanking.setCreateBy(ShiroUtils.getLoginName());

		return toAjax(contestContestRankingService.insertContestContestRanking(contestContestRanking));
	}

	/**
	 * 修改成绩
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestContestRanking contestContestRanking = contestContestRankingService.selectContestContestRankingByIdAssoc(id);
		mmap.put("contestContestRanking", contestContestRanking);
	    mmap.put("contestIdList", contestService.selectContestAll());
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/edit";
	}

	/**
	 * 修改保存成绩
	 */
	@RequiresPermissions("shooting:contestContestRanking:edit")
	@Log(title = "成绩", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestContestRanking contestContestRanking
	)
	{
		contestContestRanking.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(contestContestRankingService.updateContestContestRanking(contestContestRanking));
	}

	/**
	 * 删除成绩
	 */
	@RequiresPermissions("shooting:contestContestRanking:remove")
	@Log(title = "成绩", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(contestContestRankingService.deleteContestContestRankingByIds(ids));
	}


	/**
	 * 查询成绩分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestContestRanking contestContestRanking)
	{
		startPage(contestContestRanking.getPd());

        List<ContestContestRanking> list = contestContestRankingService.selectContestContestRankingListAssoc(contestContestRanking);
		return getDataTable(list);
	}





}
