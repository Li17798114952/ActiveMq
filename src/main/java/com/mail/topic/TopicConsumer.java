package com.mail.topic;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
/**
 * @ClassName TopicConsumer
 * @Description TODO
 * @Author 李明瑶
 * @Date 2020/6/23 11:28
 * @Version 1.0
 *  观察者消费--监听消费
 *
 *  发布/订阅模式：
 *  消息生产者将消息（发布）到topic中，可以同时有多个消息消费者（订阅）消费该消息。
 * 和点对点方式不同，发布到topic的消息会被所有订阅者消费。
 * 当生产者发布消息，不管是否有消费者，都不会保存消息。
 * 一定要先有消息的消费者，后有消息的生产者。
 */
public class TopicConsumer {
    public void consumer() throws JMSException, IOException {
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
            Destination destination = session.createTopic(TopicProducer.QUEUE_NAME);
            consumer = session.createConsumer(destination);
            /**
             * 注册监听器，队列中的消息变化会自动触发监听器，接收并自动处理消息
             *
             * 监听器一旦注册，永久有效，一直到程序关闭
             * 监听器可以注册多个，相当于集群
             * activemq自动轮询多个监听器，实现并行处理
             */
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    try {
                        TextMessage om = (TextMessage) message;
                        String data = om.getText();
                        System.out.println(data);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(Exception ex){
            throw ex;
        }
    }

    public static void main(String[] args){
        TopicConsumer consumer = new TopicConsumer();
        try{
            consumer.consumer();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
