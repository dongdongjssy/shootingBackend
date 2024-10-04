package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Mark;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IMarkService;


/**
 * 成绩 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/mark" )
public class MarkController extends BaseController {

   private String prefix = "shooting/mark";

   @Autowired
   private IMarkService markService;

   @Autowired
   private IClientUserService clientUserService;


   @RequiresPermissions( "shooting:mark:view" )
   @GetMapping( )
   public String mark( ModelMap mmap ) {
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      return prefix + "/mark";
   }


   /**
    * 查询成绩列表
    */
   @RequiresPermissions( "shooting:mark:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Mark mark ) {
      startPage();
      List<Mark> list = markService.selectMarkListAssoc( mark );
      return getDataTable( list );
   }


   /**
    * 导出成绩列表
    */
   @RequiresPermissions( "shooting:mark:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Mark mark ) {
      List<Mark> list = markService.selectMarkList( mark );
      ExcelUtil<Mark> util = new ExcelUtil<Mark>( Mark.class );
      return util.exportExcel( list, "mark" );
   }


   /**
    * 导入成绩列表
    */
   @RequiresPermissions( "shooting:mark:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Mark> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Mark.class );
         for ( Mark obj : list ) {
            markService.insertMark( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增成绩
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      return prefix + "/add";
   }


   /**
    * 新增保存成绩
    */
   @RequiresPermissions( "shooting:mark:add" )
   @Log( title = "成绩", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Mark mark ) {

      mark.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( markService.insertMark( mark ) );
   }


   /**
    * 修改成绩
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Mark mark = markService.selectMarkByIdAssoc( id );
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      mmap.put( "mark", mark );
      return prefix + "/edit";
   }


   /**
    * 修改保存成绩
    */
   @RequiresPermissions( "shooting:mark:edit" )
   @Log( title = "成绩", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Mark mark ) {
      mark.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( markService.updateMark( mark ) );
   }


   /**
    * 删除成绩
    */
   @RequiresPermissions( "shooting:mark:remove" )
   @Log( title = "成绩", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( markService.deleteMarkByIds( ids ) );
   }


   /**
    * 查询成绩分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Mark mark ) {
      startPage( mark.getPd() );

      List<Mark> list = markService.selectMarkListAssoc( mark );
      return getDataTable( list );
   }

}
