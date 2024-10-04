package io.goose.api.controller.shooting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.PublishWithImage;
import io.goose.shooting.service.IPublishWithImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 图片发布 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/publishWithImage" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "图片发布 ", description = "图片发布" )
public class PublishWithImageRestController extends BaseController {

   @Autowired
   private IPublishWithImageService publishWithImageService;


   /**
    * 查询图片发布列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询图片发布列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "publishWithImage",
         value = "图片发布", required = false, dataType = "PublishWithImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody PublishWithImage publishWithImage ) {
      startPage();

      List<PublishWithImage> list =
            publishWithImageService.selectPublishWithImageList( publishWithImage );
      return getDataTable( list );
   }


   /**
    * 根据ID查询
    */
   @PostMapping( "/getById/{id}" )
   @ApiOperation( value = " 根据ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "id", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public PublishWithImage getById( @PathVariable( "id" ) Long id ) {
      return publishWithImageService.selectPublishWithImageById( id );
   }

   /**
    * 导出图片发布列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出图片发布列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "publishWithImage", value = "图片发布", required =
    * false, dataType = "PublishWithImage") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public
    * AjaxResult export(PublishWithImage publishWithImage) { List<PublishWithImage> list =
    * publishWithImageService.selectPublishWithImageList(publishWithImage);
    * ExcelUtil<PublishWithImage> util = new ExcelUtil<PublishWithImage>(PublishWithImage.class);
    * return util.exportExcel(list, "publishWithImage"); }
    */

   /**
    * 导入图片发布列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入图片发布列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "图片发布文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<PublishWithImage> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(),
    * PublishWithImage.class); for(PublishWithImage obj : list) {
    * publishWithImageService.insertPublishWithImage(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存图片发布
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存图片发布" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "publishWithImage",
         value = "图片发布", required = true, dataType = "PublishWithImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody PublishWithImage publishWithImage ) {
      return toAjax( publishWithImageService.insertPublishWithImage( publishWithImage ) );
   }


   /**
    * 修改保存图片发布
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存图片发布" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "publishWithImage",
         value = "图片发布", required = true, dataType = "PublishWithImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody PublishWithImage publishWithImage ) {
      return toAjax( publishWithImageService.updatePublishWithImage( publishWithImage ) );
   }


   /**
    * 删除图片发布
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除图片发布" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( publishWithImageService.deletePublishWithImageByIds( ids ) );
   }


   /**
    * 根据id删除图片发布
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除图片发布" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( publishWithImageService.deletePublishWithImageById( id ) );
   }


   /**
    * 查询图片发布分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询图片发布分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "publishWithImage",
         value = "图片发布", required = true, dataType = "PublishWithImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody PublishWithImage publishWithImage ) {
      startPage( publishWithImage.getPd() );
      List<PublishWithImage> list =
            publishWithImageService.selectPublishWithImageList( publishWithImage );
      return getDataTable( list );
   }

}
