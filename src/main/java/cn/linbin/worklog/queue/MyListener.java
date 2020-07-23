package cn.linbin.worklog.queue;

import cn.linbin.worklog.constant.MQConstant;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class MyListener {

    /**
     * 监听队列
     * @param msg
     */
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQConstant.DEVELOP_QUEUE, durable = "true"),
            exchange = @Exchange(value = MQConstant.DEVELOP_EXCHANGE, type= ExchangeTypes.TOPIC),
            key = MQConstant.DEVELOP_KEY
    ))*/
    public void getMsg(String msg){
        System.out.println("================================================"+msg+"================================================");
    }
}
