package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.github.pagehelper.PageHelper;
import io.goose.common.page.PageDomain;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.Goods;
import io.goose.shooting.domain.GoodsOrder;
import io.goose.shooting.service.IGoodsOrderService;
import io.goose.shooting.service.IGoodsService;
import io.goose.system.domain.SysUser;
import io.goose.system.service.ISysUserService;
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
import io.goose.shooting.domain.GoodsEvaluation;
import io.goose.shooting.service.IGoodsEvaluationService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 商品评价 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/shooting/goodsEvaluation")
public class GoodsEvaluationController extends BaseController
{

    private String prefix = "shooting/goodsEvaluation";
	
	@Autowired
	private IGoodsEvaluationService goodsEvaluationService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private  IGoodsOrderService goodsOrderService;
	@Autowired
	private ISysUserService userService;

	
	
	@RequiresPermissions("shooting:goodsEvaluation:view")
	@GetMapping()
	public String goodsEvaluation(ModelMap mmap)
	{
		Goods goods=new Goods();
		goods.setCreateBy(ShiroUtils.getLoginName());
		mmap.put("goodsList",goodsService.selectGoodsList(goods));
		SysUser user=new SysUser();
		user.setLoginName(ShiroUtils.getLoginName());
		mmap.put("merchantsList", userService.selectMerchantsList(user));

		return prefix + "/goodsEvaluation";
	}
	
	/**
	 * 查询商品评价列表
	 */
	@RequiresPermissions("shooting:goodsEvaluation:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsEvaluation goodsEvaluation)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = pageDomain.getOrderBy();
			if(StringUtils.isBlank(orderBy)){
				orderBy="create_time desc";
			}
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
		if(StringUtils.isBlank(goodsEvaluation.getUserName())){
			goodsEvaluation.setUserName(ShiroUtils.getLoginName());
		}
        List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationListAssoc(goodsEvaluation);
        list.forEach(goodsEvaluation1 -> {
			if(goodsEvaluation1.getGoodsId()!=null){
				Goods goods=goodsService.selectGoodsById(goodsEvaluation1.getGoodsId());
				if(goods!=null){
					goodsEvaluation1.setGoodsName(Optional.ofNullable(goods.getName()).orElse(""));
					goodsEvaluation1.setUserName(Optional.ofNullable(goods.getCreateBy()).orElse(""));
				}
			}
			if(goodsEvaluation1.getGoodsOrderId()!=null){
				GoodsOrder goodsOrder=goodsOrderService.selectGoodsOrderById(goodsEvaluation1.getGoodsOrderId());
				if(goodsOrder!=null){
					goodsEvaluation1.setGoodsOrderName(Optional.ofNullable(goodsOrder.getTitle()).orElse(""));
				}
			}

		});
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品评价列表
	 */
	@RequiresPermissions("shooting:goodsEvaluation:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsEvaluation goodsEvaluation)
    {
    	List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
        ExcelUtil<GoodsEvaluation> util = new ExcelUtil<GoodsEvaluation>(GoodsEvaluation.class);
        return util.exportExcel(list, "goodsEvaluation");
    }
    
    /**
	 * 导入商品评价列表
	 */
	@RequiresPermissions("shooting:goodsEvaluation:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsEvaluation> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsEvaluation.class);
        	for(GoodsEvaluation obj : list) {
        		goodsEvaluationService.insertGoodsEvaluation(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增商品评价
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品评价
	 */
	@RequiresPermissions("shooting:goodsEvaluation:add")
	@Log(title = "商品评价", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsEvaluation goodsEvaluation 
	)
	{		

		goodsEvaluation.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsEvaluationService.insertGoodsEvaluation(goodsEvaluation));
	}

	/**
	 * 修改商品评价
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsEvaluation goodsEvaluation = goodsEvaluationService.selectGoodsEvaluationByIdAssoc(id);
		mmap.put("goodsEvaluation", goodsEvaluation);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品评价
	 */
	@RequiresPermissions("shooting:goodsEvaluation:edit")
	@Log(title = "商品评价", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsEvaluation goodsEvaluation 
	)
	{		
		goodsEvaluation.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsEvaluationService.updateGoodsEvaluation(goodsEvaluation));
	}
	
	/**
	 * 删除商品评价
	 */
	@RequiresPermissions("shooting:goodsEvaluation:remove")
	@Log(title = "商品评价", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsEvaluationService.deleteGoodsEvaluationByIds(ids));
	}
	

	/**
	 * 查询商品评价分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsEvaluation goodsEvaluation)
	{
		PageDomain pageDomain=goodsEvaluation.getPd();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = pageDomain.getOrderBy();
			if(StringUtils.isBlank(orderBy)){
				orderBy="create_time desc";
			}
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
        List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationListAssoc(goodsEvaluation);
		return getDataTable(list);
	}




	
}
