package com.mail.service;

/**
 * @ClassName MailService
 * @Description TODO
 * @Author 李明瑶
 * @Date 2020/6/23 12:51
 * @Version 1.0
 */
public interface MailService {

    public void sendSimpleMail(String to, String subject, String context);

    public void sendMimeMail(String to, String subject, String context);

    public void sendAttachMail(String[] to, String subject, String context, String filePath);

    public void sendInlineMail(String to, String subject, String context, String filePath, String resId);


    //监听器调用
    public void sendMail(String[] to, String subject, String context, String filePath, String resId );
}
