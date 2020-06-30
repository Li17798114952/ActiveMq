package com.mail.controller;


import com.mail.service.ActiveMQService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName MailSenderController
 * @Description TODO
 * @Author 李明瑶
 * @Date 2020/6/23 13:36
 * @Version 1.0
 *
 *springboot整合ActiveMQ其实也比较简单，首先配置文件中需要添加ActiveMQ的相关配置，
 * 然后生产者通过注入JmsTemplate发送消息，消费者实现监听消费。
 *
 * controller+ActiveMQService扮演生产者角色，发送消息给消费者；
 *
 * listener扮演消费者角色，接收到消息后调用MailService的接口执行邮件发送。
 */
@RestController
public class MailSenderController {

    @Resource
   private ActiveMQService activeMQService;

    @Value("${mail.to}")
    private String mailTo;

    @RequestMapping("/sendSimpleMail.do")
    public void sendSimpleMail(){
        String[] to = {mailTo};
        String subject = "普通邮件";
        String context = "你好，这是一封普通邮件";
        /**
         * 这是我的git第一次测试
         * sdfsdf
         */

        //这是我的第二次测试
        activeMQService.sendMQ(to, subject, context);
    }

    @RequestMapping("/sendAttachMail.do")
    public void sendAttachMail(){
        String[] to = {mailTo};
        String subject = "带附件的邮件";
        String context = "<html><body>你好，<br>这是一封带附件的邮件，<br>具体请见附件</body></html>";
        String filePath = "D:\\1.png";
        activeMQService.sendMQ(to, subject, context, filePath);
    }

    @RequestMapping("/sendMimeMail.do")
    public void sendMimeMail(){
        String[] to = {mailTo};
        String subject = "普通邮件";

        String filePath = "D:\\1.png";
        String resId = "1.png";
        String context = "<html><body>你好，<br>这是一封带图片的邮件，<br>请见图片<br><img src=\'cid:"+resId+"\'></body></html>";
        activeMQService.sendMQ(to, subject, context, filePath, resId);
    }
}
