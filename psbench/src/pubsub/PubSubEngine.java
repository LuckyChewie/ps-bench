package pubsub;


public interface PubSubEngine {
	
	public int start(String ClientID, String BrokerID);

	public int connect(String host, int port);
	
	public int advertise(String predicates);
	
	public int unadvertise(int id);
	
	public void subscribe(String predicates);
	
	public int unsubscribe(int id);
	
	public int publish(String predicates);
	
	public int registerListener();

	public int disconnect();
	
	public int stop();

}
