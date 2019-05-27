package com.xuecheng.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqProducer {

    private static  final String QUENE = "helloworld";

    public static void main(String[] args) {

        //通过连接工厂创建新的连接和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.146");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);
        //设置虚拟机,一个mq服务可以多个虚拟机,每个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            //设置会话通道
            channel = connection.createChannel();
            /**
             * 参数1:quene 队列名称
             * 参数2:durable 是否持久化
             * 参数3:exclusive 是否独占连接
             * 参数4:autoDelete 是否自动删除
             * 参数5:队列参数
             */
             channel.queueDeclare(QUENE, true, false, false, null);
             //发送消息
            String message = "hello world LQQ4";
            /**  String exchange, String routingKey, BasicProperties props, byte[] body
             * 参数1: exchange 交换机
             * 参数2: routingKey 路由key,交换机根据路由key将消息发送到指定队列,如果使用默认交换机,routingKey设置为队列名称
             * 参数3: props 消息属性
             * 参数4: body 消息内容
             */
            channel.basicPublish("",QUENE,null,message.getBytes());
            System.out.println("send message "+message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
