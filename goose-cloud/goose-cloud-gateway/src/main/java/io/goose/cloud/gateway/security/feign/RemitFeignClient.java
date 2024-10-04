package io.goose.cloud.gateway.security.feign;

import io.goose.common.base.AjaxResult;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "remit-service")
public interface RemitFeignClient {

    @PostMapping(value = "/remit/customer/add",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    AjaxResult createCustomer( Map<String, String> param );
    
    @PostMapping("/remit/sms/sendSms")
    AjaxResult sendSms(@RequestParam("phoneNum")  String phoneNum,
    		@RequestParam(name="isRegister",required=false) Boolean isRegister );
    
    @PostMapping("/remit/sms/checkVerifyCode")
    Boolean checkVerifyCode(@RequestParam("phoneNum") String phoneNum, 
			@RequestParam("smsCode") String smsCode);


}
