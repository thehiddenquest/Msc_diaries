package my_game_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game_panel extends JFrame implements ActionListener {

	Player_AI player = new Player_AI();

	JFrame f = new JFrame();
	JPanel bt_panel = new JPanel();
	JPanel text_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] bton = new JButton[9];
	JButton newGame = new JButton();
	JButton exit = new JButton();
	JPanel option_panel = new JPanel();

	int turn_flag = 0;
	int chance_flag = 0;

	public Game_panel() {
		setTitle("Tic Tac Toe With AI");
		setSize(300, 300);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMaximizedBounds(null);
		init_component();
	}

	public void init_component() {

		bt_panel.setLayout(new GridLayout(3, 3));
		bt_panel.setBackground(Color.CYAN);

		newGame.setText("New Game");
		newGame.setBackground(Color.BLUE);
		newGame.addActionListener(this);

		exit.setText("Exit");
		exit.setBackground(Color.RED);
		exit.addActionListener(this);

		text_panel.setBackground(Color.BLACK);

		textfield.setForeground(Color.RED);
		textfield.setBackground(Color.BLACK);
		textfield.setFont(new Font("Serif", Font.BOLD, 30));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Let's Go!");
		textfield.setOpaque(true);

		for (int i = 0; i < 9; i++) {
			bton[i] = new JButton();
			bt_panel.add(bton[i]);
			bton[i].setFont(new Font("Serif", Font.BOLD, 40));
			bton[i].setFocusable(false);
			bton[i].addActionListener(this);
			bton[i].setBackground(Color.CYAN);
		}
		text_panel.add(textfield);

		option_panel.setBackground(Color.BLACK);
		option_panel.add(newGame);
		option_panel.add(exit);

		add(text_panel, BorderLayout.NORTH);
		add(bt_panel);
		add(option_panel, BorderLayout.SOUTH);

	}

	public void oWins(int x1, int x2, int x3) {
		bton[x1].setBackground(Color.GREEN);
		bton[x2].setBackground(Color.GREEN);
		bton[x3].setBackground(Color.GREEN);

		for (int i = 0; i < 9; i++) {
			bton[i].setEnabled(false);
		}
		textfield.setText("You Lose");
	}

	public void xWins(int x1, int x2, int x3) {
		bton[x1].setBackground(Color.GREEN);
		bton[x2].setBackground(Color.GREEN);
		bton[x3].setBackground(Color.GREEN);

		for (int i = 0; i < 9; i++) {
			bton[i].setEnabled(false);
		}
		textfield.setText("congrats!");
	}

	public void matchCheck() {
		if ((bton[0].getText() == "X") && (bton[1].getText() == "X") && (bton[2].getText() == "X")) {
			xWins(0, 1, 2);
		} else if ((bton[0].getText() == "X") && (bton[4].getText() == "X") && (bton[8].getText() == "X")) {
			xWins(0, 4, 8);
		} else if ((bton[0].getText() == "X") && (bton[3].getText() == "X") && (bton[6].getText() == "X")) {
			xWins(0, 3, 6);
		} else if ((bton[1].getText() == "X") && (bton[4].getText() == "X") && (bton[7].getText() == "X")) {
			xWins(1, 4, 7);
		} else if ((bton[2].getText() == "X") && (bton[4].getText() == "X") && (bton[6].getText() == "X")) {
			xWins(2, 4, 6);
		} else if ((bton[2].getText() == "X") && (bton[5].getText() == "X") && (bton[8].getText() == "X")) {
			xWins(2, 5, 8);
		} else if ((bton[3].getText() == "X") && (bton[4].getText() == "X") && (bton[5].getText() == "X")) {
			xWins(3, 4, 5);
		} else if ((bton[6].getText() == "X") && (bton[7].getText() == "X") && (bton[8].getText() == "X")) {
			xWins(6, 7, 8);
		}

		else if ((bton[0].getText() == "O") && (bton[1].getText() == "O") && (bton[2].getText() == "O")) {
			oWins(0, 1, 2);
		} else if ((bton[0].getText() == "O") && (bton[3].getText() == "O") && (bton[6].getText() == "O")) {
			oWins(0, 3, 6);
		} else if ((bton[0].getText() == "O") && (bton[4].getText() == "O") && (bton[8].getText() == "O")) {
			oWins(0, 4, 8);
		} else if ((bton[1].getText() == "O") && (bton[4].getText() == "O") && (bton[7].getText() == "O")) {
			oWins(1, 4, 7);
		} else if ((bton[2].getText() == "O") && (bton[4].getText() == "O") && (bton[6].getText() == "O")) {
			oWins(2, 4, 6);
		} else if ((bton[2].getText() == "O") && (bton[5].getText() == "O") && (bton[8].getText() == "O")) {
			oWins(2, 5, 8);
		} else if ((bton[3].getText() == "O") && (bton[4].getText() == "O") && (bton[5].getText() == "O")) {
			oWins(3, 4, 5);
		} else if ((bton[6].getText() == "O") && (bton[7].getText() == "O") && (bton[8].getText() == "O")) {
			oWins(6, 7, 8);
		} else if (chance_flag == 9) {
			textfield.setText("Game Draw!!");
		}
	}

	public void AI_play() {
		int index = player.find_best_move(bton);
		bton[index].setForeground(Color.GREEN);
		bton[index].setText("O");
		turn_flag = 0;
		chance_flag++;
		matchCheck();

	}

	public static void main(String[] args) {
		Game_panel gp = new Game_panel();
		gp.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			chance_flag = 0;
			turn_flag = 0;
			textfield.setText("Let's Go!");
			for (int i = 0; i < 9; i++) {
				bton[i].setText("");
				bton[i].setFocusable(false);
				bton[i].setBackground(Color.CYAN);
				bton[i].setEnabled(true);
			}

		} else if (e.getSource() == exit) {
			this.dispose();
		} else if (turn_flag == 0) {
			for (int i = 0; i < 9; i++) {
				if (e.getSource() == bton[i]) {
					{
						if (bton[i].getText() == "") {
							bton[i].setForeground(Color.BLUE);
							bton[i].setText("X");
							turn_flag = 1;
							chance_flag++;
							validate();
							matchCheck();
							try {
								AI_play();
							} catch (Exception e1) {
								// TODO: handle exception
							}
						}

					}
				}
			}
		}
	}

}
