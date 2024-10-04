package io.goose.cloud.gateway.security.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SmsUtil {

   private Map<String, String> smsCodeMap = new ConcurrentHashMap<>(32);

   // check if the captcha exists for the phone
   public boolean containCode(String phone, String smsCode) {
      // HACK:Here is only for play store check.Will remove it later
//      if ("+86123456".equals(phone) && "123456".equals(smsCode)) {
//         return true;
//      }
//
      if ("+86111111".equals(phone) && "111111".equals(smsCode)) {
         return true;
      }
//
//      if ("+86222".equals(phone) && "222".equals(smsCode)) {
//         return true;
//      }
      // End Hack

      phone = StringUtils.trim(phone);
      return phone != null && smsCodeMap.containsKey(phone)
            && smsCodeMap.get(phone).equals(smsCode);
   }

   public void putCode(String phone, String smsCode) {
      phone = StringUtils.trim(phone);
      smsCodeMap.put(phone, smsCode);
   }

   public void removeCode(String phone) {
      smsCodeMap.remove(phone);
   }

   public String formatPhone(String phoneNum) {
      phoneNum = StringUtils.replace(phoneNum, "\"", "");
      phoneNum = StringUtils.trim(phoneNum);

      if (phoneNum.startsWith("+")) {
         return phoneNum;
      } else {
         return "+86" + phoneNum;
      }
   }
   
   
   
   //产品名称:
   @Value("${aliyun.product}")
   private String product;
   
   //产品域名,
   @Value("${aliyun.domain}")
   private String domain;
   
   @Value("${aliyun.accessKeyId}")
   private String accessKeyId;
   
   @Value("${aliyun.accessKeySecret}")
   private String accessKeySecret;
   
   @Value("${aliyun.signName}")
   private String signName;

   public CommonResponse sendSms(String phone,String code,String templateCode) throws ClientException {

	   DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
       IAcsClient client = new DefaultAcsClient(profile);

       CommonRequest request = new CommonRequest();
       request.setSysMethod(MethodType.POST);
       request.setSysDomain("dysmsapi.aliyuncs.com");
       request.setSysVersion("2017-05-25");
       request.setSysAction("SendSms");
       request.putQueryParameter("RegionId", "cn-hangzhou");
       request.putQueryParameter("PhoneNumbers", phone);
       request.putQueryParameter("SignName", signName);
       request.putQueryParameter("TemplateCode", templateCode);
       request.putQueryParameter("TemplateParam", "{\"code\":" + code + "}");
       try {
           CommonResponse response = client.getCommonResponse(request);
           System.out.println(response.getData());
           return response;
       } catch (ServerException e) {
           e.printStackTrace();
       } catch (ClientException e) {
           e.printStackTrace();
       }
	return null;
       
       
   }
   
   
//   public static void main(String[] args) throws Exception {
//	   //发短信
//	   CommonResponse response = sendSms("18649143083","1234563",Constants.TMPLATE1);
//       System.out.println("短信接口返回的数据----------------" + response);
////       System.out.println("Code=" + response.getCode());
////       System.out.println("Message=" + response.getMessage());
////       System.out.println("RequestId=" + response.getRequestId());
////       System.out.println("BizId=" + response.getBizId());
//
////
//   }


   
   
}
