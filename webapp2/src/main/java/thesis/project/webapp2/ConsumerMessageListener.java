package thesis.project.webapp2;

import javax.enterprise.event.Event;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

   Event<String> cdiEventHi;
   DataFromJMS eventData;

   public ConsumerMessageListener(Event<String> cdiEventHi, DataFromJMS eventData) {
      this.cdiEventHi = cdiEventHi;
      this.eventData = eventData;
   }

   public void onMessage(Message message) {
      TextMessage textMessage = (TextMessage) message;
      try {
         String msg = textMessage.getText();
         System.out.println("Recieving message from JMS = " + msg);
         eventData.setEventData(msg);
         cdiEventHi.fire(msg);
      } catch (JMSException e) {
         e.printStackTrace();
      }
   }

}
