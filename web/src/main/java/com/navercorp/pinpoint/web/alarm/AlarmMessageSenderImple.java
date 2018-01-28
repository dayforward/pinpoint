package com.navercorp.pinpoint.web.alarm;

import com.navercorp.pinpoint.web.alarm.checker.AlarmChecker;
import com.navercorp.pinpoint.web.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.InputType.password;

/**
 * Created by Administrator on 2017/9/2.
 */
public class AlarmMessageSenderImple implements AlarmMessageSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserGroupService userGroupService;

    @Override
    public void sendSms(AlarmChecker checker, int sequenceCount) {
        List<String> receivers = userGroupService.selectPhoneNumberOfMember(checker.getuserGroupId());

        if (receivers.size() == 0) {
            return;
        }

        for (String message : checker.getSmsMessage()) {
            logger.info("send SMS : {}", message);

            System.out.println("send SMS : " + message);
        }
    }

    @Override
    public void sendEmail(AlarmChecker checker, int sequenceCount) {
        List<String> receivers = userGroupService.selectEmailOfMember(checker.getuserGroupId());

        if (receivers.size() == 0) {
            return;
        }

        String message1 = checker.getEmailMessage();
        logger.info("send email : {}", message1);
        System.out.println("send email : " + message1);
        sendEmailImpl(receivers, message1);
    }

    private void sendEmailImpl(List<String> receivers, String  messagel) {
        MimeMessage mimeMsg; //MIME邮件对象
        Session session; //邮件会话对象
        Properties props = new Properties(); //系统属性
        // 初始化props
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        session = Session.getDefaultInstance(props,new MyAuthenricator("gxtest@xinguangnet.com","Aa123465"));
        // 创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);
        // 设置发信人
        try {
            message.setFrom(new InternetAddress("gxtest@xinguangnet.com"));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 设置收件人们
        final int num = receivers.size();
        InternetAddress[] addresses = new InternetAddress[num];
        /*for (int i = 0; i < num; i++) {
            try {
                addresses[i] = new InternetAddress("suny@xinguangnet.com");
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }*/
        int index = 0;
        for(String receiver : receivers) {
            try {
                addresses[index] = new InternetAddress(receiver);
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }
        try {
            message.setRecipients(RecipientType.TO, addresses);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 设置主题
        try {
            message.setSubject("SENDMAIL");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 设置邮件内容
        try {
            message.setContent(messagel, "text/html;charset=utf-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 发送
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        //      transport.connect("smtp.exmail.qq.com","suny@xinguangnet.com","xxx");
        try {
            transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    class MyAuthenricator extends Authenticator{
        String user=null;
        String pass="";
        public MyAuthenricator(String user,String pass){
            this.user=user;
            this.pass=pass;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user,pass);
        }

    }

}