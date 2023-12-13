package canteen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * 
 * @author Sunirmal Khatua
 *
 */
public class Producer extends Thread implements DisplayObject {

	private Image img;
	private int x, y;
	private static int i = 0;

	FoodBuffer foods = null;

	public Producer(int x, int y, FoodBuffer fb) {
		this.x = x;
		this.y = y;
		foods = fb;
		img = new ImageIcon(getClass().getResource("/images/chef.png")).getImage();
		start();
	}

	public void run() {
		while (true) {
			try {
				int delay = (int)(Math.random()*10000);
				Thread.sleep(delay);
				foods.prouce(new Food(FoodType.MANGO), 2);
				produce(i);
				System.out.println(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void produce(int i)
	{
		if( i < foods.getBufferSize()) {
			int randomIndex = (int) (Math.random() * FoodType.values().length);
			foods.prouce(new Food(FoodType.values()[randomIndex]), i);
			i++;
			
		}
		else if(i == foods.getBufferSize())
		{
			i = 0;
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, 70, 70, null);
	}

}
