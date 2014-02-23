package pubsub;

import java.io.IOException;
import java.util.StringTokenizer;

import siena.Filter;
import siena.Notification;
import siena.Op;
import siena.SienaException;
import siena.ThinClient;
import siena.comm.*;

public class SienaPubSubEngine implements PubSubEngine{
	
	private ThinClient SienaClient;
	private String ClientID;
	private String BrokerID;
	private Filter AdvFilter;
	private SienaSubscriber Subscriber;
	
	public int start(String ClientID, String BrokerID) {
		this.ClientID = ClientID;
		this.BrokerID = BrokerID;
		
		return 0;
	}

	public int connect(String host, int port) {
		try {
			String uri = "ka:"+host+":"+port;
			SienaClient = new ThinClient (uri,ClientID);
			try {
				SienaClient.setReceiver(new KAPacketReceiver());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InvalidSenderException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}  
	
	public void addConstraint (String predicates){
		
		StringTokenizer strtok = new StringTokenizer(predicates,",");
		AdvFilter = new Filter();
		
		while(strtok.hasMoreElements()){
			String key = strtok.nextToken().replace("[","").replace("]","").replace("'","");
			String operator = strtok.nextToken().replace("[","").replace("]","").replace("'","");
			String value = strtok.nextToken().replace("[","").replace("]","").replace("'","");
				
				if(operator.equals("eq"))
					if(!isNumeric(value))
						AdvFilter.addConstraint(key, Op.EQ, value);
					else
						AdvFilter.addConstraint(key,Op.EQ, Double.parseDouble(value));
				else if (operator.equals("<"))
					if(!isNumeric(value))
						AdvFilter.addConstraint(key, Op.LT, value);
					else
						AdvFilter.addConstraint(key,Op.LT, Double.parseDouble(value));
				else if (operator.equals(">"))
					if(!isNumeric(value))
						AdvFilter.addConstraint(key, Op.GT, value);
					else
						AdvFilter.addConstraint(key,Op.GT, Double.parseDouble(value));
				else if (operator.equals("<="))
					if(!isNumeric(value))
						AdvFilter.addConstraint(key, Op.LE, value);
					else
						AdvFilter.addConstraint(key,Op.LE, Double.parseDouble(value));
				else if (operator.equals(">="))
					if(!isNumeric(value))
						AdvFilter.addConstraint(key, Op.GE, value);
					else
						AdvFilter.addConstraint(key,Op.GE, Double.parseDouble(value));
				else
					AdvFilter.addConstraint(key,Op.ANY,value);
		}
	}

	public int advertise(String predicates) {
		
		addConstraint (predicates);
System.out.println("AdvConstraints: " + AdvFilter.toString());
		try {
			SienaClient.advertise(AdvFilter, ClientID);
			System.out.println("AdvConstraints: " + AdvFilter.toString());
		} catch (SienaException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public int unadvertise(int id) {
		
		return 0;
	}

	public void subscribe(String predicates) {
		System.out.println("Subscribing...");
		addConstraint (predicates);
		System.out.println("AdvFilter: " + AdvFilter.toString());
		try {
			Subscriber = new SienaSubscriber();
			SienaClient.subscribe(AdvFilter, Subscriber);
		} catch (SienaException e) {
			e.printStackTrace();
		}

	}

	public int unsubscribe(int id) {
		return 0;
	}

	public int publish(String predicates) {
		
		System.out.println("Publishing...");
		Notification n = new Notification();
		 
		StringTokenizer strtok = new StringTokenizer(predicates,",");
		
		while(strtok.hasMoreElements()){			
			String key = strtok.nextToken().replace("[","").replace("]","").replace("'","");
			String value = strtok.nextToken().replace("[","").replace("]","").replace("'","");
			if(!isNumeric(value))
				n.putAttribute(key, value);
			else
				n.putAttribute(key, Double.parseDouble(value));
		}
		
		System.out.println("pub-notif: " + n.toString());
			
		 try {
			SienaClient.publish(n);
		} catch (SienaException e) {
			e.printStackTrace();
		}
		    
		 return 0;
	}
	

	public int registerListener() {
		return 0;
	}


	public int disconnect() {
		return 0;
	}

	public int stop() {
		return 0;
	}

}
