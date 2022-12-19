package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.Bean.Bin;
import com.example.demo.Link.AmqpClient;
import com.example.demo.mapper.userInfMapper;
import com.example.demo.util.sqlMaker;
import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.session.SqlSession;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {
    private final static Logger logger = LoggerFactory.getLogger(AmqpClient.class);
    //部分服务器内部账密相关代码省略，部分只能在服务器上运行代码注释掉了

    private static final String iotInstanceId = "iot-06z00i08h0dlc4f";
    private static final String clientId = "HUAQIIOT0001";
    private static final String host = "1828743161474463.iot-amqp.cn-shanghai.aliyuncs.com";
    private static final int connectionCount = 4;

    private final static ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue(50000));
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < connectionCount; i++) {
            long timeStamp = System.currentTimeMillis();
            String signMethod = "hmacsha1";
            String userName = clientId + "-" + i + "|authMode=aksign"
                    + ",signMethod=" + signMethod
                    + ",timestamp=" + timeStamp
                    + ",authId=" + accessKey
                    + ",iotInstanceId=" + iotInstanceId
                    + ",consumerGroupId=" + consumerGroupId
                    + "|";
            String signContent = "authId=" + accessKey + "&timestamp=" + timeStamp;
            String password = doSign(signContent, accessSecret, signMethod);
            String connectionUrl = "failover:(amqps://" + host + ":5671?amqp.idleTimeout=80000)"
                    + "?failover.reconnectDelay=30";
            Hashtable<String, String> hashtable = new Hashtable<>();
            hashtable.put("connectionfactory.SBCF", connectionUrl);
            hashtable.put("queue.QUEUE", "default");
            hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
            Context context = new InitialContext(hashtable);
            ConnectionFactory cf = (ConnectionFactory)context.lookup("SBCF");
            Destination queue = (Destination)context.lookup("QUEUE");
            Connection connection = cf.createConnection(userName, password);
            connections.add(connection);
            ((JmsConnection)connection).addConnectionListener(myJmsConnectionListener);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(messageListener);
        }
    }
    private static final MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessage(final Message message) {
            try {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        processMessage(message);
                    }
                });
            } catch (Exception e) {
                logger.error("submit task occurs exception ", e);
            }
        }
    };
    private static void processMessage(Message message) {
        try {
            byte[] body = message.getBody(byte[].class);
            String content = new String(body);
            JSONObject json_test = JSON.parseObject(content);
            JSONObject items = json_test.getJSONObject("items");
            JSONObject height = items.getJSONObject("height");
            JSONObject per = items.getJSONObject("per");
            String valueh = height.getString("value");
            String valuep = per.getString("value");
            float hei = Float.parseFloat(valueh);
            float pe = Float.parseFloat(valuep);
            System.out.println(pe+"  "+hei);
//            SqlSession session1 = sqlMaker.getSession();
//            userInfMapper mapper = session1.getMapper(userInfMapper.class);
//            mapper.changeLevel(1,pe);
//            session1.commit();
//            session1.close();
            String topic = message.getStringProperty("topic");
            String messageId = message.getStringProperty("messageId");
        } catch (Exception e) {
            logger.error("processMessage occurs error ", e);
        }
    }
    private static final JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            logger.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        @Override
        public void onConnectionFailure(Throwable error) {
            logger.error("onConnectionFailure, {}", error.getMessage());
        }

        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            logger.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        @Override
        public void onConnectionRestored(URI remoteURI) {
            logger.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {}

        @Override
        public void onSessionClosed(Session session, Throwable cause) {}

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {}

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {}
    };
    private static String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }
}
