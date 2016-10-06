package thesis.project.webapp2;

import java.net.URI;

import javax.enterprise.event.Event;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class JMSService {

   private BrokerService   broker;
   private Connection      connection;
   private Session         session;
   private MessageConsumer consumer;

   public JMSService(Event<String> cdiEventHi, DataFromJMS eventData) {
      try{
         broker = BrokerFactory
               .createBroker(new URI("broker:(tcp://localhost:61616)"));
         broker.start();
      } catch (Exception e){
         System.out.println("Broker already exists. Using the already running broker.");
      }
      try {
         ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
               "tcp://localhost:61616");
         connection = connectionFactory.createConnection();
         connection.start();
         session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue("Event.Queue");
         consumer = session.createConsumer(destination);
         consumer.setMessageListener(new ConsumerMessageListener(cdiEventHi, eventData));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void close() {
      try {
         consumer.close();
         session.close();
         connection.close();
         broker.stop();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
