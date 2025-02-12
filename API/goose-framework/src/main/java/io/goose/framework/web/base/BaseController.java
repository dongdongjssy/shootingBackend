package io.goose.framework.web.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.base.BaseEntity;
import io.goose.common.constant.Constants;
import io.goose.common.page.PageDomain;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.DateUtils;
import io.goose.common.utils.StringUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.page.TableSupport;
import io.goose.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;


/**
 * web层通用数据处理
 *
 * @author goose
 */
public class BaseController {

   @Autowired
   protected HttpServletRequest request;
   

   /**
    * 在header中取userId
    **/
   protected Optional<Long> getUserIdFromHeader() {

      Long userId = null;

      Enumeration<?> headerNames = request.getHeaderNames();

      while (headerNames.hasMoreElements()) {
         String key = (String) headerNames.nextElement();

         if (key != null && Constants.USER_ID.equalsIgnoreCase(key)) {
            userId = request.getHeader(key) == null ? null : Long.valueOf(request.getHeader(key));
         }
      }

      return Optional.ofNullable(userId);
   }

   /**
    * 在header中取id
    **/
   protected Optional<Long> getIdFromHeader() {

      Long id = null;

      Enumeration<?> headerNames = request.getHeaderNames();

      while (headerNames.hasMoreElements()) {
         String key = (String) headerNames.nextElement();

         if (key != null && Constants.ID.equalsIgnoreCase(key)) {
            id = request.getHeader(key) == null ? null : Long.valueOf(request.getHeader(key));
         }
      }

      return Optional.ofNullable(id);
   }

   /**
    * 将前台传递过来的日期格式的字符串，自动转化为Date类型
    */
   @InitBinder
   public void initBinder(WebDataBinder binder) {
      // Date 类型转换
      binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {

         @Override
         public void setAsText(String text) {
            setValue(DateUtils.parseDate(text));
         }
      });
   }


   /**
    * 设置请求分页数据
    */
   protected void startPage() {
      PageDomain pageDomain = TableSupport.buildPageRequest();
      Integer pageNum = pageDomain.getPageNum();
      Integer pageSize = pageDomain.getPageSize();
      if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
         String orderBy = pageDomain.getOrderBy();
         PageHelper.startPage(pageNum, pageSize, orderBy);
      }
   }


   protected void startPage(PageDomain pageDomain) {
      Integer pageNum = pageDomain.getPageNum();
      Integer pageSize = pageDomain.getPageSize();
      if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
         String orderBy = pageDomain.getOrderBy();
         PageHelper.startPage(pageNum, pageSize, orderBy);
      }
   }


   /**
    * 响应请求分页数据
    */
   @SuppressWarnings({"rawtypes", "unchecked"})
   protected TableDataInfo getDataTable(List<?> list) {
      TableDataInfo rspData = new TableDataInfo();
      rspData.setCode(0);
      rspData.setRows(list);
      rspData.setTotal(new PageInfo(list).getTotal());
      return rspData;
   }


   /**
    * 响应返回结果
    *
    * @param rows
    *       影响行数
    * @return 操作结果
    */
   protected AjaxResult toAjax(int rows) {
      return rows > 0 ? success() : error();
   }


   /**
    * 响应返回结果
    *
    * @param result
    *       结果
    * @return 操作结果
    */
   protected AjaxResult toAjax(boolean result) {
      return result ? success() : error();
   }


   /**
    * 返回成功
    */
   public AjaxResult success() {
      return AjaxResult.success();
   }


   /**
    * 返回失败消息
    */
   public AjaxResult error() {
      return AjaxResult.error();
   }


   /**
    * 返回成功消息
    */
   public AjaxResult success(String message) {
      return AjaxResult.success(message);
   }


   /**
    * 返回失败消息
    */
   public AjaxResult error(String message) {
      return AjaxResult.error(message);
   }


   /**
    * 返回错误码消息
    */
   public AjaxResult error(int code, String message) {
      return AjaxResult.error(code, message);
   }


   /**
    * 页面跳转
    */
   public String redirect(String url) {
      return StringUtils.format("redirect:{}", url);
   }


   public SysUser getSysUser() {
      return ShiroUtils.getSysUser();
   }


   public void setSysUser(SysUser user) {
      ShiroUtils.setSysUser(user);
   }


   public Long getUserId() {
      return getSysUser().getUserId();
   }


   public String getLoginName() {
      return getSysUser().getLoginName();
   }


   protected Object getImageUrlPrefix() {
      // TODO Auto-generated method stub
      return "";
   }
}
