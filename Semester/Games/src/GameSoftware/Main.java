package GameSoftware;

import java.awt.Canvas;
import java.awt.Dimension;


public class Main extends Canvas {
	public static final int WIDTH = 320; // 320 pixels
	public static final int HEIGHT = WIDTH/12* 9; // 240 pixels
	public static final int SCALE = 2;
	
	static Main game;
	public static void main(String[] args) {
		game = new Main();
		game.setPreferredSize(new Dimension( WIDTH * SCALE , HEIGHT * SCALE )); // 960 * 720 pixels 
		game.setMinimumSize(new Dimension( WIDTH * SCALE , HEIGHT * SCALE )); // 960 * 720 pixels 
		game.setMaximumSize(new Dimension( WIDTH * SCALE , HEIGHT * SCALE )); // 960 * 720 pixels
		new gui(game);

	}

}	
