package cn.linbin.worklog.queue;

import cn.linbin.worklog.config.GetHttpSessionConfigurator;
import cn.linbin.worklog.constant.MQConstant;
import cn.linbin.worklog.domain.User;
import cn.linbin.worklog.utils.DateUtil;
import cn.linbin.worklog.utils.LbMap;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint(value = "/webSocket", configurator = GetHttpSessionConfigurator.class)
@Component
public class WebSocketServer {

    /*@Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtualhost}")
    private String virtualhost;*/

    private final static Logger logger = (Logger) LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private static CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<Session>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 使用spring boot时，使用的是spring-boot的内置容器，
     * 如果要使用WebSocket，需要注入ServerEndpointExporter
     *
     * @return
     */
    /*@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }*/

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        /*User user = (User) httpSession1.getAttribute("user");
        System.out.println(user.toString());*/

        sessions.add(session);
        webSocketSet.add(this);     //加入set中
        addOnlineCount();
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());

        try {
            ConnectionFactory factory = new ConnectionFactory();

            //RabbitConfig rabbitConfig = new RabbitConfig();

            /*String host = env.getProperty("spring.rabbitmq.host");
            String port = env.getProperty("spring.rabbitmq.port");
            String username = env.getProperty("spring.rabbitmq.username");
            String password = env.getProperty("spring.rabbitmq.password");
            String virtualHost = env.getProperty("spring.rabbitmq.virtual-host");*/

            /*String host = rabbitConfig.getHost();
            Integer port = rabbitConfig.getPort();
            String username = rabbitConfig.getUsername();
            String password = rabbitConfig.getPasssword();
            String virtualHost = rabbitConfig.getVirtualhost();*/

            /*factory.setHost(host);
            factory.setPort(Integer.parseInt(port));
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost(virtualhost);*/

            String rabbitSetting = (String) httpSession.getAttribute(MQConstant.RABBIT_MQ_SETTING);
            LbMap rabbitMap = LbMap.fromObject(rabbitSetting);

            factory.setHost(rabbitMap.getString("rbHost"));
            factory.setPort(rabbitMap.getInt("rbPort"));
            factory.setUsername(rabbitMap.getString("rbUsername"));
            factory.setPassword(rabbitMap.getString("rbPassword"));
            factory.setVirtualHost(rabbitMap.getString("rbVirtualHost"));

            //3.创建新的连接
            Connection connection = factory.newConnection();
            //4.创建通道
            Channel channel = connection.createChannel();

            //5.创建队列
            channel.queueDeclare(MQConstant.DEVELOP_QUEUE, true, false, false, null);

            // 绑定队列到交换机
            channel.queueBind(MQConstant.DEVELOP_QUEUE, MQConstant.DEVELOP_EXCHANGE, MQConstant.DEVELOP_KEY);

            //创建队列消费者
            //QueueingConsumer consumer = new QueueingConsumer(channel);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //处理监听得到的消息
                    String message = null;
                    boolean needSend = false;
                    try {
                        message = new String(body, "UTF-8");

                        if (message!=null){
                            LbMap receiveMap = LbMap.fromObject(message);

                            if (httpSession!=null){
                                User user = (User) httpSession.getAttribute("user");
                                String developUserId = receiveMap.getString("developUserId");

                                //登录用户是开发人员时，才发送消息
                                if (user.getUserId().equals(developUserId)){
                                    needSend = true;

                                    String developer = receiveMap.getString("developer");
                                    String customerName = receiveMap.getString("customerName");
                                    String feedbackId = receiveMap.getString("feedbackId");
                                    String requireDate = receiveMap.getString("requireDate");

                                    String date = DateUtil.timeStamp2Date(requireDate, "yyyy-MM-dd");

                                    String msg = developer + "，您好 <br><br> 您有一项开发任务需要处理<br>["+customerName+"]客户反馈["+feedbackId+"]<br>要求完成日期["+date+"]<br>请在[开发完成]中查看并按要求开发";
                                    logger.info(requireDate+"发送消息到客户端："+msg);
                                    //消息处理逻辑
                                    sendMessage(msg);
                                }
                            }

                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        channel.abort();
                    } finally {
                        if (needSend){
                            logger.info("Message Send Done.");
                            //发送了才进行回执
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }else{
                            //执行拒绝策略
                            channel.basicReject(envelope.getDeliveryTag(), true);
                        }
                    }
                    if (needSend){
                        logger.info("Received '" + message + "'");
                    }
                }
            };

            //autoAck是否自动回复，如果为true的话，每次生产者只要发送信息就会从内存中删除，那么如果消费者程序异常退出，
            // 那么就无法获取数据，我们当然是不希望出现这样的情况，所以才去手动回复，每当消费者收到并处理信息然后在通知
            // 生成者。最后从队列中删除这条信息。如果消费者异常退出，如果还有其他消费者，那么就会把队列中的消息发送给其
            // 他消费者，如果没有，等消费者启动时候再次发送。
            boolean autoAck = false;

            //6.消费队列
            channel.basicConsume(MQConstant.DEVELOP_QUEUE, autoAck, consumer);

            /*while (true) {
                //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
                *//*QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());*//*

                Thread.sleep(60000);

                String message = consumer.getMsg();
                System.out.println("Received '" + message + "'" + new Date());

                if (!StringUtils.isEmpty(message)){
                    sendMessage(message);
                }
            }*/

        }catch (Exception e){
            e.printStackTrace();
        }

        /*String QUEUE_NAME = "queue";
        try {
            //打开连接和创建频道，与发送端一样
            ConnectionFactory factory = new ConnectionFactory();
            //设置MabbitMQ所在主机ip或者主机名
            factory.setHost("127.0.0.1");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages. To exit press CTRL+C");

            //创建队列消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //指定消费队列
            channel.basicConsume(QUEUE_NAME, true, consumer);

            while (true) {
                //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());

                System.out.println("Received '" + message + "'");

                sendMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        sessions.remove(session);
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
         logger.info("来自客户端的消息:" + message);
        //群发消息
        for(WebSocketServer item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                logger.info(e.getMessage());
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        logger.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        /*if (!StringUtils.isEmpty(message)&&this.session!=null){
            this.session.getBasicRemote().sendText(message);
        }*/

        if (sessions.size() != 0) {
            for (Session s : sessions) {
                if (s != null) {
                    s.getBasicRemote().sendText(message);
                }
            }
        }

        logger.info("推送消息："+message);

        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }


    /**
     * 监听队列
     * @param msg
     */
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQConstant.DEVELOP_QUEUE, durable = "true"),
            exchange = @Exchange(value = MQConstant.DEVELOP_EXCHANGE, type= ExchangeTypes.TOPIC),
            key = MQConstant.DEVELOP_KEY
    ))
    public void receiveMsg(String msg){
        try {
            if (!StringUtils.isEmpty(msg)){
                LbMap receiveMap = LbMap.fromObject(msg);

                String developer = receiveMap.getString("developer");
                String customerName = receiveMap.getString("customerName");
                String feedbackId = receiveMap.getString("feedbackId");
                String requireDate = receiveMap.getString("requireDate");

                String date = DateUtil.timeStamp2Date(requireDate, "yyyy-MM-dd HH:mm:ss");

                String message = developer + " 您好，您有一项开发任务需要处理，<br>["+customerName+"] 客反["+feedbackId+"] 要求完成日期["+date+"]";
                logger.info(message);
                sendMessage(message);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }

    }*/
}


/*class QueueingConsumer extends DefaultConsumer {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Channel channel;
    //构造接收通道
    public QueueingConsumer(Channel channel) {
        super(channel);
        this.channel=channel;
    }

    //获取消息的方法,当监听到队列中有消息的时候 默认执行
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body);

        if (!StringUtils.isEmpty(msg)){
            setMsg(msg);
            System.out.println("获得数据:"+msg);
        }else {
            setMsg("");
            System.out.println("未获得数据");
        }

        //确认收到一个或多个消息
        //channel.basicAck(envelope.getDeliveryTag() , false);
    }
}*/
