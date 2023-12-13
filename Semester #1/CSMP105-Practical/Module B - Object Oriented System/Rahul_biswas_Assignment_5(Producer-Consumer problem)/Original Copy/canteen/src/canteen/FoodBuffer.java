package canteen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

public class FoodBuffer implements DisplayObject {

	Food[] foods = null;

	public FoodBuffer(int size) {
		foods = new Food[size];
	}

	public int getBufferSize() {
		return foods.length;
	}

	@Override
	public void draw(Graphics g) {
		for (Food f : foods) {
			if (f != null)
				f.draw(g);
		}
	}

	public synchronized void prouce(Food f, int index) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int startX = (int) (screenSize.getWidth() / 2) - 250;
		int startY = (int) (screenSize.getHeight() / 2) - 25;
		f.x = startX + index * 50 + 5;
		f.y = startY + 5;
		foods[index] = f;
	}

	public synchronized void consume(int index) {
		if (foods[index] != null) {
			foods[index] = null;
		}
	}
}
