package com.sam.ibmmq.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name ="jss.camel.ibmmq.pub.enable", havingValue = "true")
public class MqPubRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:mytimer?period=1&repeatCount=50")
                .routeId("producer-route")
                .startupOrder(1)
                .transform(simple("Random number ${random(0,100)}"))
                .log(LoggingLevel.INFO, "${body}")
                .to("jms:DEV.QUEUE.1");
    }
}
