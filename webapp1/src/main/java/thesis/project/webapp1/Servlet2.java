package thesis.project.webapp1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Servlet2 extends HttpServlet {

   private static final long serialVersionUID = -8147003083340869688L;
   
   @Inject
   private HttpSession httpSession;

   public void doGet(HttpServletRequest request,
                     HttpServletResponse response)
             throws ServletException, IOException
   {
      response.setContentType("text/html");

      PrintWriter writer = response.getWriter();
      
      StringBuilder sb = new StringBuilder();
      sb.append("<h1>Servlet 2</h1>");

      // Links to navigate
      sb.append("<a href=\"./Servlet1\">Goto Servlet 1</a>");
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
   
   public void eventReceiverCDI(@Observes @CDIEventHi String eventValue) {
      System.out.println("Servlet 2 recieved event with value = " + eventValue);
      httpSession.setAttribute("eventData", eventValue);
   }
   
}
