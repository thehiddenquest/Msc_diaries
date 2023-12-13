package rwsn;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * 
 * @author Sunirmal Khatua
 *
 */
public class Sensor extends Thread implements DisplayObject {
	private boolean live = true;
	private boolean chargingRequestSend=false;
	private Image img;
	int x,y,id;
	private SensorTypes type;
	private BaseStation bs;
	private double energyDepletionRate;
	double remainingEnergy;
	private boolean charging = false;
	
	public Sensor(int x, int y, int id, SensorTypes type, BaseStation bs) {
		this.x=x;
		this.y=y;
		this.id = id;
		this.type = type;
		this.bs = bs;
		remainingEnergy = Parameters.InitialEnergy;
		energyDepletionRate = Math.random()+0.01;
		img = new ImageIcon(getClass().getResource("/images/sensor.jpg")).getImage();
		start();
	}
	
	public void sendData() {
		if(type==SensorTypes.TEMPERATURE) {
			int data = (int)(Math.random()*100);
			Message<Integer> msg = new Message<Integer>(id, MessageTypes.DATA, data);
			bs.receiveMessage(msg);
		}else {
			double data = Math.random()*100;
			Message<Double> msg = new Message<Double>(id, MessageTypes.DATA, data);
			bs.receiveMessage(msg);
		}
			
	}
	
	/*
	 * This method implements the data sending round in the loop.
	 * In each round, it sends data which reduces energy and if the
	 * remaining energy goes bellow threshold it sends charging request to Base Station.
	 */
	@Override
	public void run() {
		while(true) {
			try {
				if(live) {
					sendData();
					remainingEnergy -= energyDepletionRate;
					if(remainingEnergy<0)
						remainingEnergy=0;
					if(remainingEnergy<Parameters.ThresholdEnergy && !chargingRequestSend) {
						Message<Double> msg = new Message<Double>(id, MessageTypes.RECHARGE, remainingEnergy);
						bs.receiveMessage(msg);
						chargingRequestSend=true;
						synchronized (this) {
							wait();
						}
						
						//startCharging();
	
					}else if(remainingEnergy==0) {
						live=false;
					}
					if(remainingEnergy == Parameters.InitialEnergy)
					{
						chargingRequestSend = false;
					}
				}
			
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public int getID() {
		return id;
	}
	public int[] get_cord()
	{
		int cord[]= {x,y};
		return cord;
	}
	public void charging(double chargeVolt)
	{
		remainingEnergy = chargeVolt;
	}
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img,x-10,y+10,30,30,null);
		if(!live) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLACK);
		}
		g.drawString("["+id+","+String.format("%.2f",remainingEnergy)+","+type+"]", x, y+5);
		g.setColor(Color.BLACK);
	}

}
