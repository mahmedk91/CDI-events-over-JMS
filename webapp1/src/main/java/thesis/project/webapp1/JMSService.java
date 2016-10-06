package thesis.project.webapp1;

import java.net.URI;

import javax.enterprise.event.Observes;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class JMSService {

   private BrokerService broker;

   public void setUp() {
      try {
         broker = BrokerFactory
               .createBroker(new URI("broker:(tcp://localhost:61616)"));
         broker.start();
      } catch (Exception e) {
         System.out.println("Broker already exists. Using the already running broker.");
      }
   }

   public void sendMessage(@Observes @CDIEventHi String eventValue) {

      System.out.println("Sending message to JMS = " + eventValue);
      try {
         ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
               "tcp://localhost:61616");
         Connection connection = connectionFactory.createConnection();
         connection.start();
         Session session = connection.createSession(false,
               Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue("Event.Queue");
         MessageProducer producer = session.createProducer(destination);
         producer.setDeliveryMode(DeliveryMode.PERSISTENT);
         TextMessage message = session.createTextMessage(eventValue);
         producer.send(message);
         session.close();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void close() {
      try {
         broker.stop();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
