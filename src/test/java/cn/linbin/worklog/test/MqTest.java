package cn.linbin.worklog.test;

import cn.linbin.worklog.constant.MQConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testMq(){
        //第一个参数：交换机名称
        //第二个参数：路由key（消息分组id）
        //第三个参数：发送的消息
        rabbitTemplate.convertAndSend(MQConstant.DEVELOP_EXCHANGE, MQConstant.DEVELOP_KEY, "this is a message");
    }
}
