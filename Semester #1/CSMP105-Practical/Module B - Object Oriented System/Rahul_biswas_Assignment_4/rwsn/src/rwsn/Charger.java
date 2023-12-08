package rwsn;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * 
 * @author Sunirmal Khatua
 *
 */
public class Charger implements DisplayObject {
	private Image img;
	private int id, x,y;
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
	}
	public synchronized void receiveMessage(Message msgR) {
		if(msgR.type==MessageTypes.REQUEST) {
			messages.add(msgR);
			charge(msgR.obj);
//			System.out.println("Sensor id requesting for charge: "+ msgR.id);
		}
	}
	public void charge(Sensor object)
	{
		double chargingEnergy = Parameters.InitialEnergy;
		object.charging(chargingEnergy);
	}
	public int getId() {
		return id;
	}
	public int[] get_cordc()
	{
		int cord[]= {x,y};
		return cord;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x,y,30,30,null);
		String msg = "[";
		Iterator<Message> it = messages.iterator();
		while (it.hasNext()) {
			Message msgR = it.next();
			msg=msg+msgR.id+",";
		}
		if(msg.endsWith(",")) {
			msg = msg.substring(0, msg.length()-1);
		}
		msg+="]";
		g.drawString(id+","+msg, x, y+5);
	}

}
