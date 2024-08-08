package com.camel.ibmmq.config;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;

@Configuration
public class MqConfig {

    @Bean
    public MQConnectionFactory mqConnectionFactory() throws JMSException {
        MQQueueConnectionFactory mqConnectionFactory = new MQQueueConnectionFactory();
        mqConnectionFactory.setHostName("your-mq-host");
        mqConnectionFactory.setPort(1414);
        mqConnectionFactory.setQueueManager("your-queue-manager");
        mqConnectionFactory.setChannel("your-channel");
        return mqConnectionFactory;
    }
}
