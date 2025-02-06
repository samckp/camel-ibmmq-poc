package com.sam.ibmmq.config;

    import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

    public class IBMMQJNDIConnector {
        private static final String JNDI_PROVIDER_URL = "file:/C:/mqbindings/"; // Path to .bindings file
        private static final String JNDI_FACTORY = "com.sun.jndi.fscontext.RefFSContextFactory";
        private static final String CONNECTION_FACTORY_NAME = "jms/QCF"; // Defined in .bindings file
        private static final String QUEUE_NAME = "jms/MY_QUEUE"; // Defined in .bindings file

        public static void main(String[] args) {
            new IBMMQJNDIConnector().connectToMQ();
        }

        public void connectToMQ() {
            try {
                // Set up JNDI environment
                Hashtable<String, String> env = new Hashtable<>();
                env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
                env.put(Context.PROVIDER_URL, JNDI_PROVIDER_URL);

                // Create JNDI context
                Context context = new InitialContext(env);

                // Lookup ConnectionFactory and Queue
                QueueConnectionFactory connectionFactory =
                        (QueueConnectionFactory) context.lookup(CONNECTION_FACTORY_NAME);
                Queue queue = (Queue) context.lookup(QUEUE_NAME);

                // Create Connection
                QueueConnection connection = connectionFactory.createQueueConnection();
                connection.start();

                // Create Session
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create Message Producer and Consumer
                QueueSender sender = session.createSender(queue);
                TextMessage message = session.createTextMessage("Hello IBM MQ!");
                sender.send(message);
                System.out.println("Message sent to MQ: " + message.getText());

                // Create Message Consumer
                QueueReceiver receiver = session.createReceiver(queue);
                Message receivedMessage = receiver.receive(5000); // 5 seconds timeout

                if (receivedMessage instanceof TextMessage) {
                    System.out.println("Received Message: " + ((TextMessage) receivedMessage).getText());
                }

                // Cleanup
                session.close();
                connection.close();
                context.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
