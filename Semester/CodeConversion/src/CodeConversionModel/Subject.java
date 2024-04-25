package CodeConversionModel;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
	private List<Observer> observers = new ArrayList<Observer>();
	public void attached(Observer obs) {
		observers.add(obs);
		obs.subject = this;
	}
	public void datached(Observer obs) {
		observers.remove(obs);
		obs.subject = null;
	}
	public void notify_observer() {
		for(Observer o : observers) {
			o.update();
		}
	}
	public abstract Object getState();

}
