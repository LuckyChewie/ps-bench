package pubsub;

public class InvalidEngineException extends Exception {

	private static final long serialVersionUID = -5401382855778664474L;
	
	public InvalidEngineException(String type){
		super("Engine " + type + " does not exist.");
	}

}
