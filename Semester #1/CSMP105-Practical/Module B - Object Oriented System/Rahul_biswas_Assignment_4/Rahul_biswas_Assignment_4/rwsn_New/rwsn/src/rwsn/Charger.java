package rwsn;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;

/**
 * 
 * @author Sunirmal Khatua
 *
 */
public class Charger extends Thread implements DisplayObject {
	private Image img;
	int id, x,y;
	private double remainingEnergy;
	private int speed;
	private BaseStation bs;
	//private Sensor object;
	private List<Message> messages = new ArrayList<Message>();
	
	public Charger(int id, int x, int y, BaseStation bs) {
		this.id=id;
		this.x=x;
		this.y=y;
		this.bs=bs;
		this.speed = (int)(Math.random()*10)+1;
		remainingEnergy = Parameters.InitialEnergy;
		img = new ImageIcon(getClass().getResource("/images/charger.png")).getImage();
		start();
	}
	
	@Override
	public void run() {
		while(true) {
			Sensor nextS = null;
			synchronized (this) {	
				if(messages.size()>0) {
					nextS = getNextSensor();
				}
			}
			if(nextS!=null) {
				int dx = (nextS.x-x)+20;
				int dy = (nextS.y-y)+ 20;
				int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);
				double Xinc = dx / (float) steps;
				double Yinc = dy / (float) steps;
				double x1=x,y1=y;
				System.out.println(Xinc+","+Yinc);
				for (int i = 0; i <= steps; i++)
				{
				    x1 += Xinc;
				    x = (int)x1;
				    y1 += Yinc;
				    y = (int)y1;
				    try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				nextS.remainingEnergy = Parameters.InitialEnergy;
				synchronized (nextS) {
					nextS.notify();
				}
			}
			
						
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void receiveMessage(Message msgR) {
		if(msgR.type==MessageTypes.REQUEST) {
			messages.add(msgR);
		}
	}
	
	public Sensor getNextSensor() {
		Sensor bestS = null;
		double minDist = Double.MAX_VALUE;
		int minIndex=-1;
		for(int i=0; i<messages.size();i++) {
			Message m = messages.get(i);
			Sensor s = (Sensor)m.data;
			double dist = Math.sqrt(Math.pow(x-s.x,2)+Math.pow(y-s.y,2));
			if(dist<minDist) {
				minDist = dist;
				bestS = s;
				minIndex = i;
			}
		}
		messages.remove(minIndex);
		return bestS;
	}

	public int getID() {
		return id;
	}

	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,30,30,null);
		String msg = "[";
		Iterator<Message> it = messages.iterator();
		while (it.hasNext()) {
			Message msgR = it.next();
			msg=msg+((Sensor)msgR.data).id+",";
		}
		if(msg.endsWith(",")) {
			msg = msg.substring(0, msg.length()-1);
		}
		msg+="]";
		g.drawString(id+","+msg, x, y+5);
	}

}
