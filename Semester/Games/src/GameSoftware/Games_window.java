package GameSoftware;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Games_window {
	public Rectangle playButton = new Rectangle(Main.WIDTH /2 + 120 , 150 , 100 , 50 );
	public Rectangle helpButton = new Rectangle(Main.WIDTH /2 + 120 , 250 , 100 , 50 );
	public Rectangle quitButton = new Rectangle(Main.WIDTH /2 + 120 , 350 , 100 , 50 );
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		Font fnt0 = new Font("arial",Font.BOLD,50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Java Games", Main.WIDTH / 2, 100);
		g2d.draw(playButton);
		g2d.draw(helpButton);
		g2d.draw(quitButton);
	}

}
