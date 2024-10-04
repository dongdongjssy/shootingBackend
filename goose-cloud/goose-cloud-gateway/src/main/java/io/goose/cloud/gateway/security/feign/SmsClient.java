package io.goose.cloud.gateway.security.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sms-service", url = "${sms.url}")
public interface SmsClient {

   @GetMapping(value = "/sendSms")
   String sendSms(
         @RequestParam("un") String un, @RequestParam("pwd") String pwd,
         @RequestParam("dstno") String dstno,
         @RequestParam("msg") String msg, @RequestParam("type") Integer type,
         @RequestParam("sendid") String sendid
   );
}

