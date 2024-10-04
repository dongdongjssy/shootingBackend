package io.goose.web.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import io.goose.framework.util.ShiroUtils;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.domain.Message;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IMessageService;

@Service
public class AsyncMessageService {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private JpushService pushService;
    @Autowired
    private IClientUserService clientUserService;

    
    @Async
	public void task(Contest contest) {
		   Message message = new Message();
	        message.setCreateBy( ShiroUtils.getLoginName() );
	        message.setTitle( "新赛事发布" );
	        message.setContent( "有新的赛事发布,快去报名参加吧" );
	        message.setType( 1 );
	        int success = messageService.insertMessage( message );
	        if ( success > 0 ) {
	           List<ClientUser> list = clientUserService.selectClientUserAll();
	           for ( ClientUser uu : list ) {
	              messageService.insertMessageUserInAsync( message.getId(), uu.getId(),
	                    message.getCreateBy() );
	              // 推送
//	              Thread.sleep(500);
//	              System.out.println("测试推送成功----------------");
	              pushService.jpush( message.getTitle(), message.getContent(), uu.getId().toString(),
	                    "2", "1", String.valueOf( contest.getId() ) );
	           }
	        } else {
	           throw new RuntimeException( "新增失败" );
	        }
	}
}
