package io.goose.api.controller.shooting;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import io.goose.api.utils.HttpReqUtil;
import io.goose.common.base.AjaxResult;
import io.goose.shooting.domain.Advertisement;
import io.goose.shooting.domain.WxSignVO;



@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class WxShareController {


 
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
    //appid  公众号的appId      wx7eff16632b6e3a40     
	public static final String APP_ID = "wxd80c5d7016a1ac63";
	//appsecret   公众号的appSecret         d543dd2a6e5f03ac876378fad07b06ef
	public static final String SECRET = "d4cf245a3b6f655ce6a5747649c45b91";
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String WeixinController() {
		return "123";
	}
	
	/**
	 * 获取微信签名
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getSignature", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult WeixinController(@RequestBody Advertisement advertisement, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");//源网址 
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, HEAD");
		response.setHeader("Access-Control-Allow-Headers", "x-auth-token, x-requested-with, Content-Type");
		response.setHeader("Access-Control-Max-Age", "3600");
 
		if (!"OPTIONS".equals(request.getMethod())) {

			
			String url = advertisement.getUrl();
		   
			url = StringEscapeUtils.unescapeHtml(url);
			
			System.out.println("url = ==========================================================" + url);
			//从缓存中读取token信息，如果没有则获取一个新的token，通过token获取ticket信息
			String access_token = (String)request.getSession().getAttribute("access_token"); 
			if(access_token == null) {
				
				 /** 获取AccessToKen*/
				 String getAccessToKen = "https://api.weixin.qq.com/cgi-bin/token?"
				 		+ "grant_type=client_credential&appid="+APP_ID+"&secret="+ SECRET;
				 JSONObject	 jsonObject = HttpReqUtil.HttpsDefaultExecute("GET", getAccessToKen, null, null, null, JSONObject.class);
				
				 /**获取jsapi_ticket*/
				String getTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+jsonObject.get("access_token").toString()+"&type=jsapi";
				JSONObject rest = HttpReqUtil.HttpsDefaultExecute("GET", getTicket, null, null, null, JSONObject.class);
				
				/**将信息保存入缓存中*/
				request.getSession().setAttribute("token", jsonObject.get("access_token").toString());
				request.getSession().setAttribute("ticket", rest.get("ticket").toString());
			}        
				/**生成签名*/
				String ticket = (String)request.getSession().getAttribute("ticket"); //微信返回的ticket		
				System.out.println("ticket = ===========================================================" + ticket);

				WxSignVO signature = sign(url,ticket); 

		        AjaxResult ret = new AjaxResult();
		        ret.put("data", signature);
		        ret.put("msg", "success");
		        ret.put("code", 0);
		        return ret;
		} else {
		}
		return null;

	}

	public static WxSignVO sign(String url,String ticket) {
        String nonceStr = UUID.randomUUID().toString();
        String timestamp = System.currentTimeMillis()/1000 + "";

        String string1 = "jsapi_ticket="+ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;

        WxSignVO sign = WxSignVO.builder()
                .appId(APP_ID)
                .nonceStr(nonceStr)
                .timestamp(timestamp)
                .signature(DigestUtils.sha1Hex(string1))
                .build();
        return sign;
    }
	
	/**
	 * 进入主页
	 * @return
	 */
	@RequestMapping(value="doIndex")
	public String index(){
		return "index";
	}
	
	/**
	 * 进入分享成功后跳转页面
	 * @return
	 */
	@RequestMapping(value="doResult")
	public String doResult(){
		return "share_result";
	}
	
}
