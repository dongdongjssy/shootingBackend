package io.goose.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

   private Map<String, String> emailCodeMap = new ConcurrentHashMap<>(32);

   public boolean containCode(String email, String smsCode) {
	   email = StringUtils.trim(email);
	      return email != null && emailCodeMap.containsKey(email)
	            && emailCodeMap.get(email).equals(smsCode);
	   }

   
   public void putCode(String email, String smsCode) {
	   email = StringUtils.trim(email);
	      emailCodeMap.put(email, smsCode);
	   }

   public void removeCode(String email) {
	   emailCodeMap.remove(email);
   }
}
