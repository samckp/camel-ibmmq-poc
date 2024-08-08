package com.camel.ibmmq.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaToMqRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

            // Route from Kafka to IBM MQ
            from("kafka:my-topic?brokers=localhost:9092")
                    .to("jms:queue:myQueue");
    }

}
