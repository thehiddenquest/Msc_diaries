package rwsn;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Hashtable;

import javax.swing.ImageIcon;

public class Charger implements DisplayObject {
	Image img;
	int x,y;
	public static Hashtable<Integer, Double> hashtable = new Hashtable<>();
	double remainingEnergy;
	public Charger(int x, int y) {
		this.x=x;
		this.y=y;
		remainingEnergy = Parameters.InitialEnergy;
		img = new ImageIcon(getClass().getResource("/images/charger.png")).getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,50,70,null);
		g.drawString(String.valueOf(remainingEnergy), x, y+5);
	}

	public void need_charging(Sensor s) {
		hashtable.put(s.id,s.remainingEnergy);
	}
	
}
