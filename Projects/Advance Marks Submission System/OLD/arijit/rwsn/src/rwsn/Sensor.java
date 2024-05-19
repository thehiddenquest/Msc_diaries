package rwsn;

import java.awt.Graphics2D;
import java.util.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Sensor implements DisplayObject {
	public enum Type{
		INTEGER,
		DOUBLE
	};
	Image img;
	int x,y,id;
	BaseStation bs;
	double energyDepletionRate;
	double remainingEnergy;
	Type type;
	public Sensor(int x, int y, BaseStation bs,Type type) {
		this.x=x;
		this.y=y;
		this.bs = bs;
		id = x+y;
		this.type = type;
		remainingEnergy = Parameters.InitialEnergy;
		energyDepletionRate = Math.random()+0.01;
		img = new ImageIcon(getClass().getResource("/images/sensor.jpg")).getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String send_message() {
		String temp;
		if(remainingEnergy < Parameters.ThresholdEnergy) {
			Message <String> msg = new Message <String> (null);
			temp = msg.getValue();
			System.out.println("id = "+id+" Type = "+type+" value = "+msg.getValue());
		}
		else {
			remainingEnergy = remainingEnergy - energyDepletionRate;
			Message <Double> msg = new Message <Double> (remainingEnergy);
			temp = msg.getValue().toString();
			System.out.println("id = "+id+" Type = "+type+" value = "+msg.getValue());
			
		}
		return temp;
	}
	
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,50,70,null);
		g.drawString(String.valueOf(remainingEnergy), x, y+5);
		g.drawString(String.valueOf(energyDepletionRate), x-10, y+15);
	}

}

