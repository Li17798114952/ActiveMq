package com.mail.service;

/**
 * @ClassName ActiveMQService
 * @Description TODO
 * @Author 李明瑶
 * @Date 2020/6/23 13:17
 * @Version 1.0
 */
public interface ActiveMQService {
    public void sendMQ(String[] to, String subject, String content);
    public void sendMQ(String[] to, String subject, String content, String filePath);
    public void sendMQ(String[] to, String subject, String content, String filePath, String srcId);
}
