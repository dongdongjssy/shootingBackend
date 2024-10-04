package io.goose.api.controller.common;

import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.utils.EmailUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/shooting/email")
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String pwd;

	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.subject}")
	private String subject;
	
	@Autowired
	JavaMailSenderImpl mailSender;

	@Autowired
	EmailUtil emailUtil;
	
	@PostMapping("/sendEmail")
	public AjaxResult sendEmail(@RequestParam String email) {
		try {
			String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
			String text = "【CPSA】验证码" + verifyCode + "，您正在进行身份验证，打死不要告诉别人哦！";

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(username);
			message.setTo(email);
			message.setSubject(subject);
			message.setText(text);
			// 发送邮件,由于阿里云禁用25端口，所以设置为465,并加入ssl验证
			final String smtpPort = "465";
			Properties prop=new Properties();
			prop.setProperty("mail.smtp.port", smtpPort);
			prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			prop.setProperty("mail.smtp.socketFactory.fallback", "false");
			prop.setProperty("mail.smtp.socketFactory.port", smtpPort);
			mailSender.setJavaMailProperties(prop);
			mailSender.send(message);
			logger.info("[News API Call End] >>>>>>>{}邮箱验证码已发送：{}，3分钟倒计时", email, verifyCode);
			emailUtil.putCode(email, verifyCode);
			// 倒计时，3分钟后验证码失效
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					emailUtil.removeCode(email);
					logger.info(">>>>>>>{}短信验证码：{}已删除", email, verifyCode);
					timer.cancel();
				}
			}, 180000);

		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.error(e.getMessage());
		}
		return AjaxResult.success("发送成功").put("email", email);
	}

}
