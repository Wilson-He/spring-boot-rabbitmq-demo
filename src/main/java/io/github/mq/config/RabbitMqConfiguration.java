package io.github.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * RabbitMqConfiguration
 *
 * @author Wilson
 * @date 2019/2/13
 */
@Configuration
public class RabbitMqConfiguration {
    @Resource
    private CachingConnectionFactory cachingConnectionFactory;

    /**
     * 单一消费者
     *
     * @return
     */
/*
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean(name = "simpleContainer")
    public SimpleMessageListenerContainer simpleContainer(@Qualifier("helloQueue") Queue simpleQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(cachingConnectionFactory);
        container.setMessageConverter(new Jackson2JsonMessageConverter());

        //TODO：并发配置
        container.setConcurrentConsumers(10);
        container.setMaxConcurrentConsumers(20);
        container.setPrefetchCount(5);

        //TODO：消息确认-确认机制种类
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(simpleQueue);
        container.setMessageListener(simpleListener);

        return container;
    }
*/

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(cachingConnectionFactory);
    }


    @Bean
    public Queue helloQueue() {
        return new Queue("helloQueue");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }

    //===============以下是验证topic Exchange的队列==========
    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }
    //===============以上是验证topic Exchange的队列==========


    //===============以下是验证Fanout Exchange的队列==========
    @Bean
    public Queue fanoutAMsg() {
        return new Queue("fanout.a");
    }

    @Bean
    public Queue fanoutBMsg() {
        return new Queue("fanout.b");
    }

    @Bean
    public Queue fanoutCMsg() {
        return new Queue("fanout.c");
    }


    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     *
     * @param queueMessage
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(@Qualifier("queueMessages") Queue queueMessages, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");
    }

    @Bean
    Binding fanoutA(@Qualifier("fanoutAMsg") Queue AMessage,@Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding fanoutB(Queue fanoutBMsg, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutBMsg).to(fanoutExchange);
    }

    @Bean
    Binding fanoutC(Queue fanoutCMsg, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutCMsg).to(fanoutExchange);
    }

}
