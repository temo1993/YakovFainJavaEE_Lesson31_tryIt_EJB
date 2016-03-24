package try_it;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

@Stateless
public class StockServerBean {
	
@Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
ConnectionFactory factory;

@Resource(lookup = "MyJMSTestQueue")
Destination testQueue;

private List<String> nasdaqSymbols = new ArrayList<>();
public StockServerBean(){
// Define some hard-coded NASDAQ symbols
nasdaqSymbols.add("AAPL");
nasdaqSymbols.add("MSFT");
nasdaqSymbols.add("YHOO");
nasdaqSymbols.add("AMZN");
}

@Schedule(second = "*", minute = "*", hour = "*")
public void getQuote(){
		String symbol = "AAPL";
		
		// Generate a random stock price
		String price = (Double.toString(Math.random() * 100));
		
		// Build a quote based on symbol and price
		String quote = "The price of" + symbol + " is" + price;
		
		// Send to JMS queue
		try(JMSContext context = factory.createContext("admin", "admin")){
			JMSProducer producer = context.createProducer();
			
			// Send stock quote to the queue
			producer.send(testQueue, quote);
			
			System.out.println("Sent stock quote to the queue: " + quote);
		}
}
}