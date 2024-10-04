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
import io.goose.shooting.domain.Comment;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.impl.ext.CommentServiceImplExt;


/**
 * 评论 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/comment" )
public class CommentController extends ClubBaseController {

   private String prefix = "shooting/comment";

   @Autowired
   private CommentServiceImplExt commentService;

   @Autowired
   private IClientUserService clientUserService;


   @RequiresPermissions( "shooting:comment:view" )
   @GetMapping( )
   public String comment( ModelMap mmap ) {
      Long clubId = getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId( clubId );
      Club club = new Club();
      club.setId( clubId );
      mmap.put( "clientUserIdList",
            clubId != null ? clientUserService.selectClientUserListFullDisplay( cu )
                  : clientUserService.selectClientUserAllFullDisplay() );
      return prefix + "/comment";
   }


   /**
    * 查询评论列表
    */
   @RequiresPermissions( "shooting:comment:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Comment comment ) {
      startPage();
      comment = setClubId( comment );
      List<Comment> list = commentService.selectCommentListAssoc( comment );
      return getDataTable( list );
   }


   /**
    * 导出评论列表
    */
   @RequiresPermissions( "shooting:comment:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Comment comment ) {
      comment = setClubId( comment );
      List<Comment> list = commentService.selectCommentList( comment );
      ExcelUtil<Comment> util = new ExcelUtil<Comment>( Comment.class );
      return util.exportExcel( list, "comment" );
   }


   /**
    * 导入评论列表
    */
   @RequiresPermissions( "shooting:comment:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Comment> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Comment.class );
         for ( Comment comment : list ) {
            comment = setClubId( comment );
            commentService.insertComment( comment );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增评论
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
      return prefix + "/add";
   }


   /**
    * 新增保存评论
    */
   @RequiresPermissions( "shooting:comment:add" )
   @Log( title = "评论", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Comment comment ) {

      comment.setCreateBy( ShiroUtils.getLoginName() );
      comment = setClubId( comment );
      return toAjax( commentService.insertComment( comment ) );
   }


   /**
    * 修改评论
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Comment comment = commentService.selectCommentByIdAssoc( id );
      Long clubId = comment.getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId( clubId );
      Club club = new Club();
      club.setId( clubId );
      mmap.put( "clientUserIdList",
            clubId != null ? clientUserService.selectClientUserListFullDisplay( ( cu ) )
                  : clientUserService.selectClientUserAllFullDisplay() );
      mmap.put( "comment", comment );
      return prefix + "/edit";
   }


   /**
    * 修改保存评论
    */
   @RequiresPermissions( "shooting:comment:edit" )
   @Log( title = "评论", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Comment comment ) {
      comment.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( commentService.updateComment( comment ) );
   }


   /**
    * 删除评论
    */
   @RequiresPermissions( "shooting:comment:remove" )
   @Log( title = "评论", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( commentService.deleteCommentByIds( ids ) );
   }


   /**
    * 查询评论分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Comment comment ) {
      startPage( comment.getPd() );
      comment = setClubId( comment );
      List<Comment> list = commentService.selectCommentListAssoc( comment );
      return getDataTable( list );
   }

}
