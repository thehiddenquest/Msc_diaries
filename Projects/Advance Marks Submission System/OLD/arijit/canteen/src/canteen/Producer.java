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
	private int x,y;
	static int i;
	
	FoodBuffer foods = null;
	public Producer(int x, int y, FoodBuffer fb) {
		this.x=x;
		this.y=y;
		foods = fb;
		img = new ImageIcon(getClass().getResource("/images/chef.png")).getImage();
		start();
	}
	
	public void produce() {
		int len = foods.getBufferSize();
		if(i < len){
			int randomIndex = (int) (Math.random()*FoodType.values().length);
			foods.prouce(new Food(FoodType.values()[randomIndex]), i);
			i++;
		}
		else if(i == len) {
			i = 0;
		}
	}
	
	public void run() {
		
			while(true) {
			produce();
			try {
				int delay = (int) (Math.random()*2000);
				Thread.sleep(delay);
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		}
	
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img,x,y,70,70,null);
	}

}
