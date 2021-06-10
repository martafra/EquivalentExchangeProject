package logic.support.other;
import java.util.ArrayList;

import javafx.application.Platform;
import logic.support.interfaces.Observer;

public abstract class Subject {

	private ArrayList<Observer> observers = new ArrayList<>();
	
	public void notifyObservers() {
	
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for(Observer ob : observers) {
					ob.update();
				}
			}	
		});
	}
	
	public void register(Observer ob) {
		if(!observers.contains(ob))
			observers.add(ob);
	}
	
	public void unregister(Observer ob) {
		observers.remove(ob);
	}
	
	
	
	
}
