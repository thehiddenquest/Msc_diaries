package GameSoftware;

import javax.swing.JFrame;

public class gui{
    private Main game;

    public gui(Main game) {
        this.game = game;
        createWindow();
    }

    private void createWindow() {
        JFrame window = new JFrame();
        window.add(game);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public void GameMenu()
    {
    	Games_window gmenu = new Games_window();
    	gmenu.render();
    }
}
