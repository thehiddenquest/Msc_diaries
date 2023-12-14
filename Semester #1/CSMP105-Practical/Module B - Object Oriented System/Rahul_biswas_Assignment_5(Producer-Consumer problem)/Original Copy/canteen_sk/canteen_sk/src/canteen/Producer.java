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
	String state="";
	
	FoodBuffer foods = null;
	
	public Producer(int x, int y, FoodBuffer fb) {
		this.x=x;
		this.y=y;
		foods = fb;
		img = new ImageIcon(getClass().getResource("/images/chef.png")).getImage();
	}
	
	@Override
	public void run() {
		while(true) {
			Food f = getRandomFood();
			foods.prouce(f);
			state = "I have produced "+f.type;
			System.out.println(state);
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Food getRandomFood() {
		int type = (int)(Math.random()*4);
		if(type==0)
			return new Food(FoodType.BANANA);
		else if(type==1)
			return new Food(FoodType.MANGO);
		else if(type==2)
			return new Food(FoodType.KIWI);
		else if(type==3)
			return new Food(FoodType.PINEAPPLE);
		else
			return new Food(FoodType.MANGO);
		
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img,x,y,70,70,null);
		g.drawString(state, x+100, y+30);
	}

}
