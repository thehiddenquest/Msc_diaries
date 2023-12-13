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
	
	FoodBuffer foods = null;
	public Consumer(int x, int y, FoodBuffer fb) {
		this.x=x;
		this.y=y;
		foods = fb;
		img = new ImageIcon(getClass().getResource("/images/student.png")).getImage();
	}
	public void run()
	{

		while(true)
		{
			consume();

			try
			{
//				if(foods.isempty())
//				{
//					wait();
//				}
		
				int delay = (int)(Math.random()*10000);
				Thread.sleep(delay);
			}	
			catch(Exception E)
			{
				System.out.println(E);
			}
		}
//			foods.notifyAll();
		}
	public void consume()
	{
		Food foodlist[] = foods.foodList();
		for(int j=0; j<foodlist.length;j++)
		{
			if(foodlist[j] != null)
			{
				foodlist[j] = null;
				
				break;
			}
		}
		
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(img,x,y,70,70,null);
	}

}
