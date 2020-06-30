package com.mail.ptp;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
/**
 * @ClassName Consumer
 * @Description TODO
 * @Author 李明瑶
 * @Date 2020/6/23 10:21
 * @Version 1.0
 *消费者
 * 主动消费:
 * 在consumer.receive()之后不会再消费其他消息了，
 * 即便后面再有消息被生产出来也不会再消费。也就是说只能在运行后消费一次消息，这个就是主动消费
 */
public class Consumer {
    public String consumer() throws JMSException {
        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
            connection = factory.createConnection();
            /**
             * 消费者必须启动连接，否则无法消费
             */
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(Producer.QUEUE_NAME);
            consumer = session.createConsumer(destination);
            /**
             * 获取队列消息
             */
            Message message = consumer.receive();
            String text = ((TextMessage) message).getText();
            return text;
        } catch(Exception ex){
            throw ex;
        } finally {
            /**
             * 7.释放资源
             */
            if(consumer != null){
                consumer.close();
            }

            if(session != null){
                session.close();
            }

            if(connection != null){
                connection.close();
            }
        }
    }

    public static void main(String[] args){
        Consumer consumer = new Consumer();
        try{
            String message = consumer.consumer();
            System.out.println("消息消费成功：" + message);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
