package rwsn;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.*;

public class MainFrame extends JFrame implements ActionListener{
	DisplayCanvas canvas = new DisplayCanvas();
	public static Sensor[] sensors;
	public static Charger[] chargers;
	public static BaseStation basestation;
	public MainFrame() {
		setTitle("Rechargable WSN");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(canvas);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		BaseStation bs = new BaseStation((int)((d.getWidth()-20)/2),(int)(d.getHeight()/2));
		basestation = bs;
		canvas.bs = bs;
		int ns = Integer.parseInt(JOptionPane.showInputDialog("How Many Sensors?"));
		int nc = Integer.parseInt(JOptionPane.showInputDialog("How Many Chargers?"));
		sensors = new Sensor[ns];
		chargers = new Charger[nc];
		for(int i=0;i<nc;i++) {
			int x = (int)(Math.random()*d.getWidth()+0.5);
			int y = (int)(Math.random()*(d.getHeight()-150)+0.5);					
			Charger c = new Charger(x, y);
			chargers[i] = c;
			canvas.chargers.add(c);
		}
		for(int i=0;i<ns;i++) {
			int x = (int)(Math.random()*d.getWidth()+0.5);
			int y = (int)(Math.random()*(d.getHeight()-150)+0.5);					
			Sensor sr = new Sensor(x, y, bs,Sensor.Type.DOUBLE);
			sensors[i] = sr;
			canvas.sensors.add(sr);
		}
		
	}
	
	public static void main(String[] args) {
		MainFrame f = new MainFrame();
		f.setVisible(true);
		int i =0;
		String Message;
		double dis = 9999999999999999999.9;
		int temp = 0 ;
		while(true) {
			Message = sensors[i].send_message();
			boolean b = basestation.recive_message(Message,sensors[i]);
			if(b) {
				for(int j =0;; j++) {
					if (Math.sqrt((sensors[i].getX() - chargers[j].getX())*(sensors[i].getX() - chargers[j].getX()) +
					(sensors[i].getY() - chargers[j].getY())*(sensors[i].getY() - chargers[j].getY()))< dis) {
						temp = j;
					}
					if(chargers[i]!=null)
						break;
				}
				chargers[temp].need_charging(sensors[i]);
			}
			i++;
			f.canvas.repaint();
			if(i == sensors.length)
				i = 0;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
