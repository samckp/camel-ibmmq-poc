package com.sam.ibmmq.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name ="jss.camel.ibmmq.sub.enable", havingValue = "true")
public class MqSubRoute extends RouteBuilder{

        @Override
        public void configure() throws Exception {

            from("jms:DEV.QUEUE.1")
                    .routeId("receiver-route")
                    .delay(10000)
                    .startupOrder(2)
                    .log(LoggingLevel.INFO, "${body}")
            ;
        }
}

