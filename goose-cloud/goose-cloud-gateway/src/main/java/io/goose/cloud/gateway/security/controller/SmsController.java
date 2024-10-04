package io.goose.cloud.gateway.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import io.goose.cloud.gateway.security.domain.ResultCode;
import io.goose.cloud.gateway.security.feign.SmsClient;
import io.goose.cloud.gateway.security.utils.SmsUtil;
import io.goose.common.base.AjaxResult;
import io.goose.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shooting/sms")
public class SmsController {
   private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
   private static final String SMS_SUCCESS = "2000 = SUCCESS";

   @Value("${sms.username}")
   private String un;

   @Value("${sms.password}")
   private String pwd;

   @Value("${sms.url}")
   private String url;

   @Value("${sms.expire}")
   private Integer expire;

   @Value("${sms.sendid}")
   private String sendid;

   @Value("${sms.type}")
   private Integer type;

   @Autowired
   SmsClient smsClient;

   @Autowired
   private SmsUtil smsUtil;

   @SuppressWarnings("static-access")
   @PostMapping("/sendSms")
   public AjaxResult sendSms(@RequestBody String phoneNum) throws ClientException {
      logger.info("[Shooting API Call] send sms code");

      // 生成6位随机验证码
      final String phone = StringUtils.replace(phoneNum, "\"", "");
      String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
      String formattedPhoneNumber = smsUtil.formatPhone(phone);

      //发送短信
      CommonResponse response = smsUtil.sendSms(formattedPhoneNumber, verifyCode, Constants.TMPLATE1);
      JSONObject jsonRes = JSONObject.parseObject(response.getData());

      if ("OK".equals(jsonRes.getString("Code"))) {
         logger.info("[Shooting API Call End] >>>>>>>{}短信验证码已发送：{}，3分钟倒计时", formattedPhoneNumber, verifyCode);
         smsUtil.putCode(formattedPhoneNumber, verifyCode);

         // 倒计时，3分钟后验证码失效
         final Timer timer = new Timer();
         timer.schedule(new TimerTask() {
            @Override
            public void run() {
               smsUtil.removeCode(formattedPhoneNumber);
               logger.info(">>>>>>>{}短信验证码：{}已删除", formattedPhoneNumber, verifyCode);
               timer.cancel();
            }
         }, expire);

         return AjaxResult.success().put("phone", formattedPhoneNumber);
      } else {
         logger.error("[Shooting API Call End] >>>>>>>短信发送失败");
         return AjaxResult.error(ResultCode.EXCEPTION_SEND_SMS_CODE.getMsg())
               .put("smsServerMsg", jsonRes.getString("Message"));
      }
   }
}
