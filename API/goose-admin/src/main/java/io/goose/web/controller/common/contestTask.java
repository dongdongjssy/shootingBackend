package io.goose.web.controller.common;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.goose.shooting.domain.*;
import io.goose.shooting.mapper.GtMapper;
import io.goose.shooting.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.goose.web.controller.service.JpushService;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class contestTask {
	
	@Autowired
	private IRegisterService registerService;
	
	@Autowired
	private IContestService contestService;
	
    @Autowired
    private JpushService pushService;
    
    @Autowired
    private IMessageService messageService;

	@Autowired
	private IClientUserService clientUserService;
	@Autowired
	private IStartAdvertisementService startAdvertisementService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IGoodsOrderService goodsOrderService;
	@Autowired
	private GtMapper gtMapper;
	
	//3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq= format.format(end);//当前日期
        
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt= format.format(start);//前一天
	        
	        
    	
    	
    	Register register = new Register();
    	register.setFkTable(2);
    	List<Register> list = registerService.selectRegisterListAssoc(register);
    	for(Register register2 : list) {
    		Contest contest = contestService.selectContestById(register2.getFkId());
    		if(contest != null) {
    			c.setTime(contest.getStartDate());
    			c.add(Calendar.DATE, -3);
    	        Date threeday = c.getTime();
    	        String threedaystr = format.format(threeday);
    	        
    	        c.setTime(new Date());
    	        Date today = c.getTime();
    	        String todaystr = format.format(today);
    	        
    	        if(todaystr.equals(threedaystr)) {
	    	          Message message = new Message();
	    	          message.setCreateBy("1");
	    	          message.setTitle( "新赛事发布" );
	    	          message.setContent( "有新的赛事发布,快去报名参加吧" );
	    	          message.setType(2);
	    	          int success = messageService.insertMessage( message );
	    	          if ( success > 0 ) {
	    	                messageService.insertMessageUser( message.getId(),register2.getClientUserId(),
	    	                      message.getCreateBy() );
	    	                // 推送
	    	                pushService.jpush( message.getTitle(), message.getContent(), register2.getClientUserId().toString(),
	    	                      "2", "1", String.valueOf( contest.getId() ) );
	    	          } else {
	    	             throw new RuntimeException( "新增失败" );
	    	          }
    	        }

    		}
    		
    	}
    	System.out.println("执行静态定时任务时间: " + LocalDateTime.now());
    }


	//@Scheduled(cron = "0 0 12 1/1 * ? ")
	private void sendMessageRemind() {
    	List<ClientUser> userList=clientUserService.selectRemindMessage(new ClientUser());
		for(ClientUser user : userList) {
			user.setStatus(4);
			clientUserService.updateClientUser(user);
			Message message = new Message();
			message.setCreateBy("admin");
			message.setTitle( "临期用户提醒" );
			message.setContent( "会员认证即将到期提醒" );
			message.setType(2);
			int success = messageService.insertMessage( message );
			if ( success > 0 ) {
				messageService.insertMessageUser( message.getId(),user.getId(),
						message.getCreateBy() );
				// 推送
				pushService.jpush( message.getTitle(), message.getContent(), user.getId().toString(),
						"3", "1", String.valueOf( user.getId() ) );
			} else {
				throw new RuntimeException( "新增失败" );
			}
		}
	}


	@Scheduled(cron = "0 0 0 1/1 * ? ")
	private void updateStartAdvertisement() {
		List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementListAssoc(new StartAdvertisement());
		list.forEach(s->{
			Date date=new Date();
			Long nowTIme=date.getTime();
			if(nowTIme- s.getEffectTime().getTime()>=0&&s.getFailureTime().getTime()-nowTIme>=0){
				s.setAdvertisementStatus(1);
			}
			if(nowTIme- s.getEffectTime().getTime()<0){
				s.setAdvertisementStatus(0);
			}
			if(s.getFailureTime().getTime()-nowTIme<0){
				s.setAdvertisementStatus(2);
			}
			startAdvertisementService.updateStartAdvertisement(s);
		});
    }


	//@Scheduled(cron = "0/1 * * * * ? ")
	private void updatePyamentStatus() {
		Order order=new Order();
		order.setGoodsOrderStatus(1);
		List<Order> orderList=orderService.selectOrderList(order);
		orderList.forEach(order1 -> {
			Date createTime=order1.getCreateTime();
			Date nowTime=	new Date();
			long createTimeL=createTime.getTime();
			long nowTimeL=nowTime.getTime();
			int s=(int)(nowTimeL-createTimeL)/1000;
			if(s>3600){
				order1.setGoodsOrderStatus(7);
				orderService.updateOrder(order1);
				GoodsOrder goodsOrder=new GoodsOrder();
				goodsOrder.setOrderId(order1.getId());
				List<GoodsOrder> goodsOrderList=goodsOrderService.selectGoodsOrderList(goodsOrder);
				goodsOrderList.forEach(goodsOrder1 -> {
					goodsOrder1.setGoodsOrderStatus(7);
					goodsOrderService.updateGoodsOrder(goodsOrder1);
					Gt gt=new Gt();
					gt.setId(goodsOrder1.getGtId());
					gt.setNum(goodsOrder1.getNum());
					gtMapper.updateGtNum(gt);
					Message message = new Message();
					message.setCreateBy("1");
					message.setTitle( "订单状态改变" );
					message.setContent( "您的订单状态已经改变" );
					message.setType(2);
					int success = messageService.insertMessage( message );
					if ( success > 0 ) {
						messageService.insertMessageUser( message.getId(),goodsOrder1.getClientUserId(),
								message.getCreateBy() );
						// 推送
						pushService.jpush( message.getTitle(), message.getContent(), goodsOrder1.getClientUserId().toString(),
								"9", "1", String.valueOf( goodsOrder1.getId() ) );
					} else {
						throw new RuntimeException( "新增失败" );
					}
				});
			}
		});
	}
}
