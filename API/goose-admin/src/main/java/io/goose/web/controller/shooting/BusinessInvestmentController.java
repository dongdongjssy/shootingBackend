package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
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
import io.goose.shooting.domain.BusinessInvestment;
import io.goose.shooting.service.IBusinessInvestmentService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 招商 信息操作处理
 *
 * @author goose
 * @date 2020-06-18
 */
@Controller
@RequestMapping("/shooting/businessInvestment")
public class BusinessInvestmentController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BusinessInvestmentController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/businessInvestment";

    @Autowired
    private IBusinessInvestmentService businessInvestmentService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

    @RequiresPermissions("shooting:businessInvestment:view")
    @GetMapping()
    public String businessInvestment(ModelMap mmap) {
        return prefix + "/businessInvestment";
    }

    /**
     * 查询招商列表
     */
    @RequiresPermissions("shooting:businessInvestment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusinessInvestment businessInvestment) {
        startPage();
        List<BusinessInvestment> list = businessInvestmentService.selectBusinessInvestmentListAssoc(businessInvestment);
        return getDataTable(list);
    }


    /**
     * 导出招商列表
     */
    @RequiresPermissions("shooting:businessInvestment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusinessInvestment businessInvestment) {
        List<BusinessInvestment> list = businessInvestmentService.selectBusinessInvestmentList(businessInvestment);
        ExcelUtil<BusinessInvestment> util = new ExcelUtil<BusinessInvestment>(BusinessInvestment.class);
        return util.exportExcel(list, "businessInvestment");
    }

    /**
     * 导入招商列表
     */
    @RequiresPermissions("shooting:businessInvestment:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<BusinessInvestment> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), BusinessInvestment.class);
            for (BusinessInvestment obj : list) {
                businessInvestmentService.insertBusinessInvestment(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }

    /**
     * 新增招商
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        return prefix + "/add";
    }

    /**
     * 新增保存招商
     */
    @RequiresPermissions("shooting:businessInvestment:add")
    @Log(title = "招商", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusinessInvestment businessInvestment
            , @RequestParam(value = "mediaUrlFile", required = false) MultipartFile mediaUrlFile
    ) {
        try {
            if (mediaUrlFile != null && !mediaUrlFile.isEmpty()) {
            	
                String filePath = fileUploadDownloadService.upload(mediaUrlFile, null,UploadTypeEnums.Advertisement.getValue() , true);
                businessInvestment.setMediaUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        businessInvestment.setCreateBy(ShiroUtils.getLoginName());

        return toAjax(businessInvestmentService.insertBusinessInvestment(businessInvestment));
    }

    /**
     * 修改招商
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BusinessInvestment businessInvestment = businessInvestmentService.selectBusinessInvestmentByIdAssoc(id);
        mmap.put("businessInvestment", businessInvestment);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        return prefix + "/edit";
    }

    /**
     * 修改保存招商
     */
    @RequiresPermissions("shooting:businessInvestment:edit")
    @Log(title = "招商", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusinessInvestment businessInvestment
            , @RequestParam(value = "mediaUrlFile", required = false) MultipartFile mediaUrlFile
    ) {
        try {
            if (mediaUrlFile != null && !mediaUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(mediaUrlFile, null,UploadTypeEnums.Advertisement.getValue() , true);
                businessInvestment.setMediaUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        businessInvestment.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(businessInvestmentService.updateBusinessInvestment(businessInvestment));
    }

    /**
     * 删除招商
     */
    @RequiresPermissions("shooting:businessInvestment:remove")
    @Log(title = "招商", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(businessInvestmentService.deleteBusinessInvestmentByIds(ids));
    }


    /**
     * 查询招商分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody BusinessInvestment businessInvestment) {
        startPage(businessInvestment.getPd());

        List<BusinessInvestment> list = businessInvestmentService.selectBusinessInvestmentListAssoc(businessInvestment);
        return getDataTable(list);
    }
}
