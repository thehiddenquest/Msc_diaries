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
public class BaseStation implements DisplayObject {

	private Image img;
	private int x,y;
	private Sensor objs;
	private Charger objc;
	private int[] cords;
	private int[] cordc;
	private List<Message> messages = new ArrayList<Message>();
	Map<Integer, Charger> chargers = new HashMap<Integer, Charger>();
	Map<Integer, Sensor> sensors = new HashMap<Integer, Sensor>();
	
	public BaseStation(int x, int y) {
		this.x=x;
		this.y=y;
		img = new ImageIcon(getClass().getResource("/images/bs.png")).getImage();
	}
	
	public synchronized void receiveMessage(Message msg) {
		if(msg.type==MessageTypes.RECHARGE) {
			messages.add(msg);
			objs = sensors.get(msg.id);
//			System.out.println("Sensor id requesting for charge: "+ msg.id);
			cords = objs.get_cord();
			String combinedCoords = String.valueOf(cords[0]) + String.valueOf(cords[1]);
			int closest = best_charger(cords);
			Message<String> msgR = new Message<>(msg.id,MessageTypes.REQUEST,combinedCoords, objs);
			objc = chargers.get(closest);
			objc.receiveMessage(msgR);
//			System.out.println("Coordinate of sensor "+ msg.id +" is " + cords[0] + "," + cords[1] + 
//					" nearest charger is " + closest );
		}
	}
	public int best_charger(int[] coord)
	{
		int sizec = chargers.size();
		double min = 999.0;
		int min_index = -1;
		cordc = new int[sizec*2];
		for(int i = 0; i < sizec; i++)
		{
			objc = chargers.get(i);
			cordc = objc.get_cordc();
//			System.out.println("Coord of " + cordc[0]+ " " + cordc[0]);
			double o = Math.sqrt(Math.pow(cordc[0] - coord[0], 2) + Math.pow(cordc[1] - coord[1], 2));
			if(o < min)
			{
				min = o;
				min_index = i;
			}
		}
		return min_index;
	}
//	private int findMinIndex(double[] array) {
//	    int minIndex = 0;
//	    double minValue = array[0];
//
//	    for (int i = 1; i < array.length; i++) {
//	        if (array[i] < minValue) {
//	            minValue = array[i];
//	            minIndex = i;
//	        }
//	    }
//
//	    return minIndex;
//	}
	
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
