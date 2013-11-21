package pubsub;

public class PubSubFactory {

	public static PubSubEngine createPubSubEngine(String type) throws InvalidEngineException {
		if(type.equals("padres")){
			return new PadresPubSubEngine();
		} 
		
		if(type.equals("siena")){
			return new SienaPubSubEngine();
		}
		else
			throw new InvalidEngineException(type);
	}
}
