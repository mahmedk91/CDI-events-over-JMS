package thesis.project.webapp2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet3 extends HttpServlet {

   private static final long serialVersionUID = -8147003083340869689L;

   @Inject
   @CDIEventHi
   private Event<String>     cdiEventHi;

   private JMSService        jmsService;
   
   private DataFromJMS eventData;
   
   @Override
   public void init(ServletConfig config) throws ServletException {
      eventData = new DataFromJMS();
      try {
         jmsService = new JMSService(cdiEventHi, eventData);         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void destroy() {
      jmsService.close();
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      response.setContentType("text/html");

      PrintWriter writer = response.getWriter();

      StringBuilder sb = new StringBuilder();
      sb.append("<h1>Servlet 3</h1>");

      // Links to navigate
      sb.append("<a href=\"../webapp1/Servlet1\">Goto Servlet 1</a>");
      sb.append("<br>");
      sb.append("<a href=\"../webapp1/Servlet2\">Goto Servlet 2</a>");
      
      sb.append("<h3>");
      if (eventData.getEventData() != null) {
         sb.append("Recieved event data - ");
         sb.append(eventData.getEventData());
      } else {
         sb.append("No event received");
      }
      sb.append("</h3>");     
      
      writer.write(sb.toString());
   }

   public void eventReceiverCDI(@Observes @CDIEventHi String eventValue) {
      System.out.println("Servlet 3 recieved event with value = " + eventValue);
   }

}
