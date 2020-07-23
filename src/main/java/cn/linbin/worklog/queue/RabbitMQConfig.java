package cn.linbin.worklog.queue;

import cn.linbin.worklog.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConfig {

    @Bean
    public Queue developQueue(){
        return new Queue(MQConstant.DEVELOP_QUEUE, true);
    }


    @Bean
    public TopicExchange developExchange(){
        return new TopicExchange(MQConstant.DEVELOP_EXCHANGE, true, false);
    }


    @Bean
    public Binding developBinding(){
        return BindingBuilder.bind(developQueue()).to(developExchange()).with(MQConstant.DEVELOP_KEY);
    }
}
