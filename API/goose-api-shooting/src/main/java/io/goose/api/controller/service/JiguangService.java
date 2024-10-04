package io.goose.api.controller.service;

import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JiguangService {

    private static final Logger log = LoggerFactory.getLogger(JiguangService.class);

    // TODO - should move the constants to config file
    private static final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private static final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    // 极光API
    private final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

    public boolean addJgUser(String username){
        return this.addJgUser(username, null, null);
    }
    
    public boolean addJgUser(String username, String nickname, String avatarPath){
        if(username == null) return false;

        // 添加用户到极光
        try {
            RegisterInfo registerInfo = RegisterInfo.newBuilder()
                    .setUsername(username)
                    .setPassword(username)
                    .setNickname(nickname != null ? nickname : username)
                    .build();

            JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
            log.info("***【极光】*** 添加用户到极光成功: " + registerInfo);

            return true;
        } catch (Exception e) {
            JSONArray jsonArray= JSONArray.parseArray(e.getMessage());
            JSONObject jsonObject =  (JSONObject)jsonArray.get(0);
            Map<String,Object> mapError = (Map<String, Object>) jsonObject.get("error");
            if(!mapError.get("code").toString().equals("899001")) {
                log.error("***【极光】*** 添加用户到极光失败!");
                log.error("***【极光】*** " + e.getMessage());
            }

            return false;
        }
    }
}
