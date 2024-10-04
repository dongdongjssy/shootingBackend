package io.goose.web.controller.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.group.GroupClient;
import cn.jmessage.api.group.MemberListResult;
import cn.jpush.api.JPushClient;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.goose.common.base.AjaxResult;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Club;
import io.goose.shooting.service.IClientUserService;
import io.goose.web.controller.shooting.ClientUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@EnableAsync
@Service
public class JMessageService {
    private final Logger log = LoggerFactory.getLogger(ClientUserController.class);

    // TBD - should move the constants to config file   key  679e8b46d1f50eaf8a0f056c   secret c2a6bbb947e9a687eceacff1
    private final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    // 极光聊天API
    private final GroupClient JMSG_GROUP_CLIENT = new GroupClient(APP_KEY, MASTER_SECRET);
    private final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

    @Autowired
    private IClientUserService clientUserService;

    /**
     * 添加用户到极光聊天服务
     */
    public boolean addUserToJMessage(ClientUser clientUser) {
        if (clientUser.getPhone() != null) {
            try {
                RegisterInfo registerInfo = RegisterInfo.newBuilder()
                        .setUsername(clientUser.getPhone())
                        .setPassword(clientUser.getPhone())
                        .setNickname(clientUser.getNickname() != null ? clientUser.getNickname() : clientUser.getPhone())
                        .setAddress(clientUser.getAddress() != null ? clientUser.getAddress() : "")
                        .setAvatar(clientUser.getAvatar() != null ? clientUser.getAvatar() : "")
                        .addExtra("phone", clientUser.getPhone())
                        .addExtra("avatar", clientUser.getAvatar() != null ? clientUser.getAvatar() : "")
                        .build();

                JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
                log.debug("***【极光】*** 添加用户到极光成功: " + registerInfo);
            } catch (APIConnectionException e) {
                log.error("***【极光】*** 添加用户到极光失败." + clientUser.getJgUsername());
                e.printStackTrace();

                return false;
            } catch (APIRequestException e) {
                JSONArray jsonArray = JSONArray.parseArray(e.getMessage());
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                Map<String, Object> mapError = (Map<String, Object>) jsonObject.get("error");
                if (!mapError.get("code").toString().equals("899001")) {
                    log.error("***【极光】*** 添加用户到极光失败." + clientUser.getJgUsername());
                    log.error("***【极光】*** Error Code: " + e.getErrorCode() + " Error Message: "
                            + e.getErrorMessage() + " Status: " + e.getStatus());
                    e.printStackTrace();

                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * 添加用户到聊天群
     */
    public boolean addUserToGroup(Club club, ClientUser user) {
        Members members = Members.newBuilder().addMember(user.getJgUsername()).build();

        try {
            boolean isInGroup = Arrays.stream(JMSG_GROUP_CLIENT.getGroupMembers(Long.parseLong(club.getJgGroupId()))
                    .getMembers())
                    .anyMatch(member -> member.getUsername().equals(user.getPhone()));

            if (!isInGroup) {
                JMSG_GROUP_CLIENT.addOrRemoveMembers(Long.parseLong(club.getJgGroupId()), members, null);
            }
            log.debug("***【极光聊天】*** 添加用户 【" + user.getNickname() + "】 到群组 【" + club.getTitle() + "】 成功");
        } catch (APIConnectionException e) {
            log.error("***【极光聊天】*** 添加用户 【" + user.getNickname() + "】 到群组 【" + club.getTitle() + "】 失败");
            e.printStackTrace();
        } catch (APIRequestException e) {
            log.error("***【极光聊天】*** 添加用户 【" + user.getNickname() + "】 到群组 【" + club.getTitle() + "】 失败");
            log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
                    " Error Message: " + e.getErrorMessage() +
                    " Status: " + e.getStatus());
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 把用户从聊天群里删除
     */
    public boolean removeUserFromGroup(Club club, ClientUser user) {
        Members members = Members.newBuilder().addMember(user.getJgUsername()).build();

        try {
            JMSG_GROUP_CLIENT.addOrRemoveMembers(Long.parseLong(club.getJgGroupId()), null, members);
            log.debug("***【极光聊天】*** 移除用户 【" + user.getNickname() + "】 从群组 【" + club.getTitle() + "】 成功");
        } catch (APIConnectionException e) {
            log.error("***【极光聊天】*** 移除用户 【" + user.getNickname() + "】 从群组 【" + club.getTitle() + "】 失败");
            e.printStackTrace();
        } catch (APIRequestException e) {
            log.error("***【极光聊天】*** 移除用户 【" + user.getNickname() + "】 从群组 【" + club.getTitle() + "】 失败");
            log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
                    " Error Message: " + e.getErrorMessage() +
                    " Status: " + e.getStatus());
            e.printStackTrace();
        }
        return true;
    }
}
