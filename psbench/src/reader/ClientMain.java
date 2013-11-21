package reader;

import java.io.FileNotFoundException;
import pubsub.InvalidEngineException;

public class ClientMain {
		
	public static void main(String args[]){
		if(args.length < 6){
			System.out.println("Expected: Client <workload_file> <host> <port> <engine> <clientID> <brokerID> ");
			System.exit(0);
		}
		
		try {
			String engine = parseEngine(args[3]);
			new Client(engine,args[0],args[1],Integer.parseInt(args[2]),args[4],args[5]).start();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidEngineException e) {
			e.printStackTrace();
		}

	}
	
	public static String parseEngine(String engine) throws InvalidEngineException{
		if(engine.equals("padres") || engine.equals("siena"))
				return engine;
		else throw new InvalidEngineException(engine);
	}
}

