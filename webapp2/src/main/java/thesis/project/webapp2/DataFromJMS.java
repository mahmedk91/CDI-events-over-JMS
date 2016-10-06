package thesis.project.webapp2;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataFromJMS {
     
   private String eventData;
   
   void setEventData(String eventData){
      this.eventData=eventData;
   }
   
   String getEventData(){
      return this.eventData;
   }
}
