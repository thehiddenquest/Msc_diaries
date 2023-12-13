package rwsn;

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
public class BaseStation extends Thread implements DisplayObject {

	private Image img;
	private int x,y;
	//private Sensor objs;
	//private Charger objc;
	//private int[] cords;
	//private int[] cordc;
	private List<Message> messages = new ArrayList<Message>();
	Map<Integer, Charger> chargers = new HashMap<Integer, Charger>();
	Map<Integer, Sensor> sensors = new HashMap<Integer, Sensor>();
	
	public BaseStation(int x, int y) {
		this.x=x;
		this.y=y;
		img = new ImageIcon(getClass().getResource("/images/bs.png")).getImage();
		start();
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized (this) {		
				Iterator<Message> it = messages.iterator();
				while(it.hasNext()) {
				    Message m = it.next();
					Sensor s = sensors.get(m.id);
					Charger c = getBestCharger(m);
					Message<Sensor> msgR = new Message<Sensor>(m.id,MessageTypes.REQUEST,s);
					c.receiveMessage(msgR);
					it.remove();
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void receiveMessage(Message msg) {
		if(msg.type==MessageTypes.RECHARGE) {
			messages.add(msg);
		}
	}
	
	public Charger getBestCharger(Message m)
	{
		Sensor s = sensors.get(m.id);
		//int sizec = chargers.size();
		Charger bestC = null;
		double minDist = Double.MAX_VALUE;
		for(Charger c : chargers.values()) {
			double dist = Math.sqrt(Math.pow(c.x-s.x,2)+Math.pow(c.y-s.y,2));
			if(dist<minDist) {
				minDist = dist;
				bestC = c;
			}
		}
		return bestC;
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
		String msg = "[";
		Iterator<Message> it = messages.iterator();
		while (it.hasNext()) {
			Message m = it.next();
			msg=msg+m.id+",";
		}
		if(msg.endsWith(",")) {
			msg = msg.substring(0, msg.length()-1);
		}
		msg+="]";
		g.drawString(msg, x, y);
	}

}
