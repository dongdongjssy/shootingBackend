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
import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.CommentFeedback;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.impl.ext.CommentFeedbackServiceImplExt;


/**
 * 回复 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/commentFeedback" )
public class CommentFeedbackController extends ClubBaseController {

   private String prefix = "shooting/commentFeedback";

   @Autowired
   private CommentFeedbackServiceImplExt commentFeedbackService;

   @Autowired
   private IClientUserService clientUserService;


   @RequiresPermissions( "shooting:commentFeedback:view" )
   @GetMapping( )
   public String commentFeedback( ModelMap mmap ) {
      Long clubId = getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId( clubId );
      Club club = new Club();
      club.setId( clubId );
      mmap.put( "clientUserIdList",
            clubId != null ? clientUserService.selectClientUserListFullDisplay( cu )
                  : clientUserService.selectClientUserAllFullDisplay() );
      // mmap.put( "commentIdList", commentService.selectCommentAll() );
      return prefix + "/commentFeedback";
   }


   /**
    * 查询回复列表
    */
   @RequiresPermissions( "shooting:commentFeedback:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( CommentFeedback commentFeedback ) {
      startPage();
      commentFeedback = setClubId( commentFeedback );
      List<CommentFeedback> list =
            commentFeedbackService.selectCommentFeedbackListAssoc( commentFeedback );
      return getDataTable( list );
   }


   /**
    * 导出回复列表
    */
   @RequiresPermissions( "shooting:commentFeedback:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( CommentFeedback commentFeedback ) {
      commentFeedback = setClubId( commentFeedback );
      List<CommentFeedback> list =
            commentFeedbackService.selectCommentFeedbackList( commentFeedback );
      ExcelUtil<CommentFeedback> util = new ExcelUtil<CommentFeedback>( CommentFeedback.class );
      return util.exportExcel( list, "commentFeedback" );
   }


   /**
    * 导入回复列表
    */
   @RequiresPermissions( "shooting:commentFeedback:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<CommentFeedback> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), CommentFeedback.class );
         for ( CommentFeedback commentFeedback : list ) {
            commentFeedback = setClubId( commentFeedback );
            commentFeedbackService.insertCommentFeedback( commentFeedback );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增回复
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      Long clubId = getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId( clubId );
      Club club = new Club();
      club.setId( clubId );
      mmap.put( "clientUserIdList",
            clubId != null ? clientUserService.selectClientUserListFullDisplay( cu )
                  : clientUserService.selectClientUserAllFullDisplay() );
      // mmap.put( "commentIdList", commentService.selectCommentAll() );
      return prefix + "/add";
   }


   /**
    * 新增保存回复
    */
   @RequiresPermissions( "shooting:commentFeedback:add" )
   @Log( title = "回复", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( CommentFeedback commentFeedback ) {

      commentFeedback.setCreateBy( ShiroUtils.getLoginName() );
      commentFeedback = setClubId( commentFeedback );
      return toAjax( commentFeedbackService.insertCommentFeedback( commentFeedback ) );
   }


   /**
    * 修改回复
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      CommentFeedback commentFeedback = commentFeedbackService.selectCommentFeedbackByIdAssoc( id );
      Long clubId = commentFeedback.getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId( clubId );
      Club club = new Club();
      club.setId( clubId );
      mmap.put( "clientUserIdList",
            clubId != null ? clientUserService.selectClientUserListFullDisplay( cu )
                  : clientUserService.selectClientUserAllFullDisplay() );
      mmap.put( "commentFeedback", commentFeedback );
      // mmap.put( "commentIdList", commentService.selectCommentAll() );
      return prefix + "/edit";
   }


   /**
    * 修改保存回复
    */
   @RequiresPermissions( "shooting:commentFeedback:edit" )
   @Log( title = "回复", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( CommentFeedback commentFeedback ) {
      commentFeedback.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( commentFeedbackService.updateCommentFeedback( commentFeedback ) );
   }


   /**
    * 删除回复
    */
   @RequiresPermissions( "shooting:commentFeedback:remove" )
   @Log( title = "回复", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( commentFeedbackService.deleteCommentFeedbackByIds( ids ) );
   }


   /**
    * 查询回复分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody CommentFeedback commentFeedback ) {
      startPage( commentFeedback.getPd() );
      commentFeedback = setClubId( commentFeedback );
      List<CommentFeedback> list =
            commentFeedbackService.selectCommentFeedbackListAssoc( commentFeedback );
      return getDataTable( list );
   }

}
