package com.xuecheng.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitmqConsumer {

    private static final String QUENE = "helloworld";

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.146");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUENE,true,false,false,null);

        //实现消费方法
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            //当接收到消息执行此方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String exchange = envelope.getExchange();
                //消费id,mq在channel中用来标识消息的id,可用于确认消息已接收
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body,"UTF-8");
                System.out.println("收到的消息:"+message);
            }
        };

        channel.basicConsume(QUENE,true,defaultConsumer);
    }
}
