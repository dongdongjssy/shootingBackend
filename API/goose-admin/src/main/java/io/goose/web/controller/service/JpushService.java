package io.goose.web.controller.service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import io.goose.web.controller.shooting.ClientUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.*;

@EnableAsync
@Service
public class JpushService {

    private final Logger log = LoggerFactory.getLogger(ClientUserController.class);

    // TBD - should move the constants to config file   key  474bda2cbe8df10288202cf6   secret 856b0e2b8b5c33cd939d29b4
    private final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    private final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

    @Value("${goose.enableJPush}")
    private boolean enabled;


    //title 标题
    //content 内容
    //alias 别名
    //type  1.培训 2.赛事 3.系统消息 4.个人动态 5.身份审核 6.用户角色变化 7.我得赛事（俱乐部赛事）报名状态变化 8.我得培训报名状态变化 9.订单状态变更
    //subType  1.列表 2.详情
    //orderId  通用跳详情用的ID
    public void jpush(String title, String content, String alias, String type, String subType, String orderId) {
        if (enabled) {
            // For push, all you need do is to build PushPayload object.
            List<String> aliasList = new ArrayList<String>();
            aliasList.add(alias);

            Map<String, String> map = new HashMap<String, String>();
            map.put("type", type);
            map.put("subType", subType);
            map.put("orderId", orderId);
            map.put("timestamp", String.valueOf(new Date().getTime()));

            PushPayload payload = buildPushObject_all_alias_alertWithTitle(aliasList, title, title, content, map);
            try {
                PushResult result = jpushClient.sendPush(payload);
                log.debug("***【极光】*** 发送成功: " + result);
            } catch (APIConnectionException e) {
                // Connection error, should retry later
                log.debug("***【极光】*** 发送失败: " + e);
            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                log.debug("***【极光】*** 发送失败: " + e);
                log.debug("***【极光】*** 发送失败: " + e.getStatus());
                log.debug("***【极光】*** 发送失败: " + e.getErrorCode());
                log.debug("***【极光】*** 发送失败: " + e.getErrorMessage());
            }
        }
    }

    ;


    //    public  PushPayload buildPushObject_all_alias_alert(String content,String alias) {
    //    	Message message = new Message("标题", "内容", "1", null, null, null, null);
    //        return PushPayload.newBuilder()
    //                .setPlatform(Platform.all())
    //                .setAudience(Audience.alias(alias))
    //                .setNotification(Notification.alert(content))
    //				.setMessage(message)//使用自定义消息推送
    //                .build();
    //    }


    private PushPayload buildPushObject_all_alias_alertWithTitle(List<String> alias, String notification_title, String msg_title, String msg_content, Map<String, String> extrasparam) {
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();

        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.alias(alias))
                //                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()

                                .setAlert(msg_content)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extrasparam)

                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msg_content)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtras(extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
                                .build())


                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                //                .setMessage(Message.newBuilder()
                //
                //                        .setMsgContent(msg_content)
                //
                //                        .setTitle(msg_title)
                //
                //                        .addExtra("url",extrasparam)
                //
                //                        .build())
                //
                .setOptions(Options.newBuilder()
                        //                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(true)
                        //                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        //                        .setSendno(1)
                        //                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        //                        .setTimeToLive(86400)
                        //
                        .build())

                .build();

    }


    public void jpushMessage(String title, String content, String alias, String type, String subType, String orderId,Integer isImportant) {
        if (enabled) {
            // For push, all you need do is to build PushPayload object.
            List<String> aliasList = new ArrayList<String>();
            aliasList.add(alias);

            Map<String, String> map = new HashMap<String, String>();
            map.put("type", type);
            map.put("subType", subType);
            map.put("orderId", orderId);
            map.put("timestamp", String.valueOf(new Date().getTime()));
            map.put("isImportant", isImportant.toString());

            PushPayload payload = buildPushObject_all_alias_alertWithTitle(aliasList, title, title, content, map);
            try {
                PushResult result = jpushClient.sendPush(payload);
                log.debug("***【极光】*** 发送成功: " + result);
            } catch (APIConnectionException e) {
                // Connection error, should retry later
                log.debug("***【极光】*** 发送失败: " + e);
            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                log.debug("***【极光】*** 发送失败: " + e);
                log.debug("***【极光】*** 发送失败: " + e.getStatus());
                log.debug("***【极光】*** 发送失败: " + e.getErrorCode());
                log.debug("***【极光】*** 发送失败: " + e.getErrorMessage());
            }
        }
    }


}
