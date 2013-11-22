package pubsub;

import siena.Notifiable;
import siena.Notification;
import siena.SienaException;

public class SienaSubscriber implements Notifiable {

    public void notify(Notification e) {
        System.out.println("Got notification: " + e.toString());
    }

    public void notify(Notification s[]) {
        // I never subscribe for patterns anyway. 
    }
	
} 
