package canteen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
public class Consumer extends Thread implements DisplayObject {

	private Image img;
	private int x,y;
	String state = "";
	
	FoodBuffer foods = null;
	public Consumer(int x, int y, FoodBuffer fb) {
		this.x=x;
		this.y=y;
		foods = fb;
		img = new ImageIcon(getClass().getResource("/images/student.png")).getImage();
	}
	
	@Override
	public void run() {
		while(true) {
			Food f = foods.consume();
			state = "I have consumed "+f.type;
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img,x,y,70,70,null);
		g.drawString(state, x-200, y+30);
	}

}
