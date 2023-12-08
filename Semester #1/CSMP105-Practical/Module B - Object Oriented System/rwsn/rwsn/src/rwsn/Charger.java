package rwsn;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Charger implements DisplayObject {
	Image img;
	int x,y;
	double remainingEnergy;
	public Charger(int x, int y) {
		this.x=x;
		this.y=y;
		remainingEnergy = Parameters.InitialEnergy;
		img = new ImageIcon(getClass().getResource("/images/charger.png")).getImage();
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,50,70,null);
		g.drawString(String.valueOf(remainingEnergy), x, y+5);
	}

}
