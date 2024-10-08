package com.sam.ibmmq;

import com.sam.ibmmq.route.ChoiceRoute;

import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.apache.camel.builder.RouteBuilder;

import static org.assertj.core.util.Maps.newHashMap;
import java.util.Map;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.junit.jupiter.api.Test;

import static com.sam.ibmmq.route.ChoiceRoute.*;
import static org.apache.camel.builder.AdviceWith.adviceWith;


@MockEndpoints
class ChoiceRouteTest extends CamelTestSupport {

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    public RouteBuilder createRouteBuilder() {
        return new ChoiceRoute();
    }

    @Test
    void givenGadgetOrderRequest_route_WillProcessGadgetOrder() throws Exception {

        MockEndpoint mockGadget = mockEndpoint(GADGET, 1);
        MockEndpoint mockWidget = mockEndpoint(WIDGET, 0);
        MockEndpoint mockGeneral = mockEndpoint(GENERAL, 0);

        context.start();

        String body = "Airpods";
        Map<String, Object> headers = newHashMap(INVENTORY, GADGET);
        template.sendBodyAndHeaders("direct:orders", body, headers);

        assertAllSatisfied(mockGadget, mockWidget, mockGeneral);

    }


    private MockEndpoint mockEndpoint(String orderType, int expectedCount) throws Exception {

        RouteDefinition route = context.getRouteDefinition(orderType);
        adviceWith(
                    route,
                    context,
                    new AdviceWithRouteBuilder() {
                        @Override
                        public void configure() throws Exception {
                            weaveAddLast().to("mock:" + orderType);
                        }
                    });
            MockEndpoint mockEndpoint = getMockEndpoint("mock:" + orderType);
            mockEndpoint.expectedMessageCount(expectedCount);

            return mockEndpoint;
        }

        private void assertAllSatisfied(MockEndpoint... mockEndpoint) throws InterruptedException {
            for (MockEndpoint endpoint : mockEndpoint) {
                endpoint.assertIsSatisfied();
            }

    }
}
