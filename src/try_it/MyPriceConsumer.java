package try_it;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(mappedName = "MyJMSTestQueue", activationConfig = { 
		@ActivationConfigProperty(
		propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		})
public class MyPriceConsumer implements MessageListener {

    
    public MyPriceConsumer() {
        
    }
	
	@Override
    public void onMessage(Message message) {
       try{
    	   System.out.println("Got the text message from the stock testQueue: " 
    			   + message.getBody(String.class));
       } catch (JMSException jmsE){
    	   System.out.println("JMS ERROR: " + jmsE.getMessage());
       }
        
    }

}
