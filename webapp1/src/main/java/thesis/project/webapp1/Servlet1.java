package thesis.project.webapp1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet1 extends HttpServlet {

   private static final long serialVersionUID = -8147003083340869689L;
   
   @Inject
   private HttpSession       httpSession;

   @Inject
   @CDIEventHi
   private Event<String>     cdiEventHi;
   
   private JMSService jmsService;
   
   @Override
   public void init(){
      jmsService = new JMSService();
      jmsService.setUp();
   }
   
   @Override
   public void destroy(){
      jmsService.close();
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      response.setContentType("text/html");

      PrintWriter writer = response.getWriter();

      StringBuilder sb = new StringBuilder();
      sb.append("<h1>Servlet 1</h1>");

      // Form to fire CDI event
      sb.append(
            "<form action=\"./Servlet1\" method=\"post\" enctype=\"application/x-www-form-urlencoded\">");
      sb.append("Give event value: <input type=\"text\" name=\"value\" value=\"Hello CDI event\"><br><br>");
      sb.append("<button type=\"submit\">Fire event</button>");
      sb.append("</form>");

      // Links to navigate
      sb.append("<a href=\"./Servlet2\">Goto Servlet 2</a>");
      sb.append("<br>");
      sb.append("<a href=\"../webapp2/Servlet3\">Goto Servlet 3</a>");

      sb.append("<h3>");
      if (httpSession.getAttribute("eventData") != null) {
         sb.append("Recieved event data - ");
         sb.append(httpSession.getAttribute("eventData"));
      } else {
         sb.append("No event received");
      }
      sb.append("</h3>");

      writer.write(sb.toString());
   }

   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      
      String eventValue = request.getParameter("value");
      
      System.out.println();
      System.out.println("--------------");
      System.out.println("Event Activity");
      System.out.println("--------------");
      System.out.println("Servlet 1 fired event with value = "+eventValue);
      
      cdiEventHi.fire(eventValue);
      
      response.setContentType("text/html");

      PrintWriter writer = response.getWriter();

      StringBuilder sb = new StringBuilder();
      sb.append("<h1>Servlet 1</h1>");

      // Links to navigate
      sb.append("<a href=\"./Servlet1\">Goto Servlet 1</a>");
      sb.append("<br>");
      sb.append("<a href=\"./Servlet2\">Goto Servlet 2</a>");
      sb.append("<br>");
      sb.append("<a href=\"../webapp2/Servlet3\">Goto Servlet 3</a>");

      writer.write(sb.toString());
   }

   public void eventReceiverCDI(@Observes @CDIEventHi String eventValue) {
      System.out.println("Servlet 1 recieved event with value = " + eventValue);
      httpSession.setAttribute("eventData", eventValue);
   }

}
