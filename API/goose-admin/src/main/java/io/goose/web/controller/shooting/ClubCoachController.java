package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.shooting.domain.*;
import io.goose.system.domain.SysDictData;
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
import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.service.IClubCoachService;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.ICoachService;


/**
 * 俱乐部教官 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/clubCoach" )
public class ClubCoachController extends ClubBaseController {

   private String prefix = "shooting/clubCoach";

   @Autowired
   private IClubCoachService clubCoachService;

   @Autowired
   private IClubService clubService;

   @Autowired
   private ICoachService coachService;


   @RequiresPermissions( "shooting:clubCoach:view" )
   @GetMapping( )
   public String clubCoach( ModelMap mmap ) {
	  Long clubId = getClubId();
	  mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      mmap.put( "coachIdList", coachService.selectCoachAll() );
      return prefix + "/clubCoach";
   }


   /**
    * 查询俱乐部教官列表
    */
   @RequiresPermissions( "shooting:clubCoach:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( ClubCoach clubCoach ) {
      startPage();
      clubCoach = setClubId(clubCoach);
      List<ClubCoach> list = clubCoachService.selectClubCoachListAssoc( clubCoach );
      return getDataTable( list );
   }


   /**
    * 导出俱乐部教官列表
    */
   @RequiresPermissions( "shooting:clubCoach:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( ClubCoach clubCoach ) {
	   clubCoach = setClubId(clubCoach);
      List<ClubCoach> list = clubCoachService.selectClubCoachList( clubCoach );
      if(list.size()>0){
         List<Club> clubList=clubService.selectClubAll();
         List<Coach> coachList=coachService.selectCoachAll();
         for(int i =0;i<list.size();i++) {
            for (Coach coach:coachList){
               if(coach.getId()==list.get(i).getCoachId()){
                  list.get(i).setCoachName(coach.getNickname());
               }
            }
            for (Club club:clubList){
               if(club.getId()==list.get(i).getClubId()){
                  list.get(i).setClubName(club.getTitle());
               }
            }
         }
      }
      ExcelUtil<ClubCoach> util = new ExcelUtil<ClubCoach>( ClubCoach.class );
      return util.exportExcel( list, "clubCoach" );
   }


   /**
    * 导入俱乐部教官列表
    */
   @RequiresPermissions( "shooting:clubCoach:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<ClubCoach> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), ClubCoach.class );
         for ( ClubCoach clubCoach : list ) {
        	 clubCoach = setClubId(clubCoach);
            clubCoachService.insertClubCoach( clubCoach );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增俱乐部教官
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
	  Long clubId = getClubId();
      mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      mmap.put( "coachIdList", coachService.selectCoachAll() );
      return prefix + "/add";
   }


   /**
    * 新增保存俱乐部教官
    */
   @RequiresPermissions( "shooting:clubCoach:add" )
   @Log( title = "俱乐部教官", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( ClubCoach clubCoach ) {

      clubCoach.setCreateBy( ShiroUtils.getLoginName() );
      clubCoach = setClubId(clubCoach);
      return toAjax( clubCoachService.insertClubCoach( clubCoach ) );
   }


   /**
    * 修改俱乐部教官
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      ClubCoach clubCoach = clubCoachService.selectClubCoachByIdAssoc( id );
      mmap.put( "clubCoach", clubCoach );
	  Long clubId = getClubId();
      mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      mmap.put( "coachIdList", coachService.selectCoachAll() );
      return prefix + "/edit";
   }


   /**
    * 修改保存俱乐部教官
    */
   @RequiresPermissions( "shooting:clubCoach:edit" )
   @Log( title = "俱乐部教官", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( ClubCoach clubCoach ) {
      clubCoach.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( clubCoachService.updateClubCoach( clubCoach ) );
   }


   /**
    * 删除俱乐部教官
    */
   @RequiresPermissions( "shooting:clubCoach:remove" )
   @Log( title = "俱乐部教官", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( clubCoachService.deleteClubCoachByIds( ids ) );
   }


   /**
    * 查询俱乐部教官分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody ClubCoach clubCoach ) {
      startPage( clubCoach.getPd() );
      clubCoach = setClubId(clubCoach);
      List<ClubCoach> list = clubCoachService.selectClubCoachListAssoc( clubCoach );
      return getDataTable( list );
   }

}
