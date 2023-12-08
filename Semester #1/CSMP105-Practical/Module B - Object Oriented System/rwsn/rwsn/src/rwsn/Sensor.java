package rwsn;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Sensor implements DisplayObject {

	Image img;
	int x,y;
	BaseStation bs;
	double energyDepletionRate;
	double remainingEnergy;
	public Sensor(int x, int y, BaseStation bs) {
		this.x=x;
		this.y=y;
		this.bs = bs;
		remainingEnergy = Parameters.InitialEnergy;
		energyDepletionRate = Math.random()+0.01;
		img = new ImageIcon(getClass().getResource("/images/sensor.jpg")).getImage();
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,50,70,null);
		g.drawString(String.valueOf(remainingEnergy), x, y+5);
	}

}
