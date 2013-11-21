package pubsub;

public class PubSubNotification {

	private int latency;
	private String predicate;
	private int id;
	
	public PubSubNotification(){
		
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PubSubNotification [latency=" + latency + ", predicate="
				+ predicate + ", id=" + id + "]";
	}
	
}
