package logic.support.other;
import java.util.ArrayList;
import logic.support.interfaces.Observer;

public abstract class Subject {

	private ArrayList<Observer> observers = new ArrayList<>();
	
	public void notifyObservers() {
		
		for(Observer ob : observers) {
			ob.update();
		}
		
	}
	
	public void register(Observer ob) {
		observers.add(ob);
	}
	
	public void unregister(Observer ob) {
		observers.remove(ob);
	}
	
	
	
	
}
