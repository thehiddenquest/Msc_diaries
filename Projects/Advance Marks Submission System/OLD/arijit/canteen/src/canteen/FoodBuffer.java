package canteen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

public class FoodBuffer  implements DisplayObject {
	
	Food[] foods = null;
	static int i = 0;
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
	
	public int getBufferSize() {
		return foods.length; 
	}
	
	public Food[] foodList() {
		return foods;
	}
	
	public void prouce(Food f, int index) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int startX = (int)(screenSize.getWidth()/2) - 250;
		int startY = (int)(screenSize.getHeight()/2) - 25;
		f.x = startX+ index*50 + 5;
		f.y = startY + 5;
		foods[index] = f;
	}
	
	public Boolean isEmpty() {
		if(foods == null)
			return true;
		for(Food element : foods) {
			if(element == null)
				return true;
		}
		return  foods == null || foods.length == 0;
	}
	
}
