package com.sam.ibmmq.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "jss.camel.choice.enabled", havingValue = "true")
public class ChoiceRoute extends RouteBuilder {

    public static final String WIDGET = "widget";
    public static final String GADGET = "gadget";
    public static final String GENERAL = "general";
    public static final String INVENTORY = "inventory";
    private static final String HEADER_INVENTORY = "${header." + INVENTORY + "}";


    @Override
    public void configure() throws Exception {

        from("direct:orders")
                .routeId("orders")
                .choice()
                    .when(simple(HEADER_INVENTORY + " == '" + WIDGET + "'"))
                        .to("direct:widget")
                    .when(simple(HEADER_INVENTORY + " == '" + GADGET + "'"))
                        .to("direct:gadget")
                    .otherwise()
                        .to("direct:general")
                ;

        from("direct:widget")
                .routeId("widget")
                .log(LoggingLevel.INFO,  "Received a " + WIDGET + "order for ${body}")
                ;

        from("direct:gadget")
                .routeId("gadget")
                .log(LoggingLevel.INFO, "Received a " + GADGET + "order for ${body}")
                ;

        from("direct:general")
                .routeId("general")
                .log(LoggingLevel.INFO, "Received a " + GENERAL +  "order for ${body}")
                ;
    }
}
