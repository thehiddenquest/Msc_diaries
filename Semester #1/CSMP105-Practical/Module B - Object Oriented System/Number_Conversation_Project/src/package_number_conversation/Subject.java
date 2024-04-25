package package_number_conversation;

import java.util.ArrayList;

public abstract class Subject {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	public void attached(Observer o) {
		observers.add(o);
		System.out.print(o);
		o.subject = this;
	}
	public void datached(Observer o) {
		observers.remove(o);
		o.subject = null;
	}
	public void notify_observer() {
		for(Observer o : observers) {
			o.update();
		}
	}
	public abstract Object getState();
	public abstract void setState(Object obj);
}
