package canteen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

public class FoodBuffer  implements DisplayObject {
	
	Food[] foods = null;
	int produceIndex=0, consumeIndex=0;
	
	Producer p=null;
	Consumer c = null;
	
	public FoodBuffer(int size) {
		foods = new Food[size];
	}

	@Override
	public void draw(Graphics g) {
		for(Food f : foods) {
			if(f != null)
				f.draw(g);
		}
	}
	
	private boolean isEmpty() {
		if(produceIndex==consumeIndex)
			return true;
		else
			return false;
	}
	
	private boolean isFull() {
		if((produceIndex+1)%foods.length==consumeIndex)
			return true;
		else
			return false;
	}
	
	
	public synchronized void prouce(Food f) {
		if(isFull()) {
			try {
				p.state = "I am waiting for free table";
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		produceIndex = (produceIndex+1) % foods.length;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int startX = (int)(screenSize.getWidth()/2) - 250;
		int startY = (int)(screenSize.getHeight()/2) - 25;
		f.x = startX+ produceIndex*50 + 5;
		f.y = startY + 5;
		foods[produceIndex] = f;
		notify();
	}
	
	public synchronized Food consume() {
		if(isEmpty()) {
			try {
				c.state = "I am waiting for food";
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		consumeIndex = (consumeIndex+1) % foods.length;
		Food f = foods[consumeIndex];
		foods[consumeIndex] = null;
		notify();
		return f;
	}

}
