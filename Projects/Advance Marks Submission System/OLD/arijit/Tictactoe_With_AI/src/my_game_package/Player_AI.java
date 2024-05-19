package my_game_package;

import javax.swing.JButton;

public class Player_AI {

	public int find_best_move(JButton[] board) {
		int bestVal = Integer.MIN_VALUE;
		int bestMove = -1;
		try {
			for (int i = 0; i < 9; i++) {
				if (board[i].getText().equals("")) {
					board[i].setText("O");

					int moveVal = minimax(board, 0, false);

					board[i].setText("");

					if (moveVal > bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bestMove;
	}

	private int minimax(JButton[] board, int depth, boolean isMaximizing) {
		int score = evaluate(board);

		if (score == 10 || score == -10) {
			return score;
		}

		if (!isMovesLeft(board)) {
			return 0;
		}

		if (isMaximizing) {
			int best = Integer.MIN_VALUE;

			for (int i = 0; i < 9; i++) {
				if (board[i].getText().equals("")) {
					board[i].setText("O");
					best = Math.max(best, minimax(board, depth + 1, !isMaximizing));
					board[i].setText("");
				}
			}
			return best;
		} else {
			int best = Integer.MAX_VALUE;

			for (int i = 0; i < 9; i++) {
				if (board[i].getText().equals("")) {
					board[i].setText("X");
					best = Math.min(best, minimax(board, depth + 1, !isMaximizing));
					board[i].setText("");
				}
			}
			return best;
		}
	}

	private int evaluate(JButton[] board) {
		for (int i = 0; i < 3; i++) {
			if (board[i * 3].getText().equals("O") && board[i * 3 + 1].getText().equals("O")
					&& board[i * 3 + 2].getText().equals("O")) {
				return 10;
			}
			if (board[i].getText().equals("O") && board[i + 3].getText().equals("O")
					&& board[i + 6].getText().equals("O")) {
				return 10;
			}
			if (board[0].getText().equals("O") && board[4].getText().equals("O") && board[8].getText().equals("O")) {
				return 10;
			}
			if (board[2].getText().equals("O") && board[4].getText().equals("O") && board[6].getText().equals("O")) {
				return 10;
			}

			if (board[i * 3].getText().equals("X") && board[i * 3 + 1].getText().equals("X")
					&& board[i * 3 + 2].getText().equals("X")) {
				return -10;
			}
			if (board[i].getText().equals("X") && board[i + 3].getText().equals("X")
					&& board[i + 6].getText().equals("X")) {
				return -10;
			}
			if (board[0].getText().equals("X") && board[4].getText().equals("X") && board[8].getText().equals("X")) {
				return -10;
			}
			if (board[2].getText().equals("X") && board[4].getText().equals("X") && board[6].getText().equals("X")) {
				return -10;
			}
		}

		return 0;
	}

	private boolean isMovesLeft(JButton[] board) {
		for (int i = 0; i < 9; i++) {
			if (board[i].getText().equals("")) {
				return true;
			}
		}
		return false;
	}
}