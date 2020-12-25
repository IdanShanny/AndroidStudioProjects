package com.shanny.idan.sudoku;

public class Board {
	private int[][] board = new int[9][9];
	private int[][] solvedBoard = new int[9][9];
	
	private long lastSolveTimeInMiliSeconds = -1; // initial value
	private long lastValidateTimeInMiliSeconds = -1; // initial value
	private long count;
	
	public long getCount() {
		
		return count;
	}
	
	public String getLastSolveTime() {
		return formatMiliseconds(lastSolveTimeInMiliSeconds);
	}

	public String getLastValidateTime() {
		return formatMiliseconds(lastValidateTimeInMiliSeconds);
	}
	
	private String formatMiliseconds(long time) {
		long frag = time%1000;
		String fragStr = (frag<10)? "00" + frag : ((frag<100)? "0" + frag : "" + frag);
		return time/1000 + "." + fragStr + " sec.";	
	}
	
	public int getBoardEntry(int i, int j) {
		return board[i][j];
	}

	public void setBoardEntry(int i, int j, int value) {
		board[i][j] = value;
	}

	private boolean equals(int[][] origBoard, int[][] newBoard) {
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (origBoard[i][j] != newBoard[i][j])
					return false;

		return true;
	}

	private boolean checkLines(){
		for (int k=0; k<9; k++)
			for (int i=0; i<8; i++)
				for (int j=i+1; j<9; j++)
					if (((board[i][k] == board[j][k]) && (board[i][k] != 0)) ||
							((board[k][i]==board[k][j]) && (board[k][i] != 0)))
						return false;
		return true;
	}

	private boolean checkSquares(){
		for (int k=0; k<9; k+=3)
			for (int l=0; l<9; l+=3)
				for (int i=0; i<8; i++)
					for (int j=i+1; j<9; j++)
						if ((board[k+i/3][l+i%3] == board[k+j/3][l+j%3]) &&
								(board[k+i/3][l+i%3] != 0))
							return false;
		return true;
	}

	private int checkZeroes(){
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if ((board[i][j] == 0))
					return i*9+j;
		return -1; // no zeroes
	}

	public boolean solve (){
		return solve (true);
	}

	private boolean solve (boolean straight){
		count++;
		if (!checkLines() || !checkSquares())
			return false;
		int n = checkZeroes();
		if (n==-1) {
			return true;
		}

		int start = 1, advance = 1, limit = 10;

		if (!straight) {
			start = 9;
			advance = -1;
			limit = 0;
		}
		for (int i=start; i != limit; i=i+advance) {
			board[n/9][n%9] = i;
			if (solve(straight)) {
				return true;
			}
		}
		board[n/9][n%9] = 0;
		return false;
	}

	public int solveAndValidate (){
		count = 0;
		lastSolveTimeInMiliSeconds = lastValidateTimeInMiliSeconds = -1;
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++) {
				solvedBoard[i][j] = board[i][j];			
			}
		}
		long initTimeStamp = System.currentTimeMillis();
		if (!solve()) {
			lastSolveTimeInMiliSeconds = System.currentTimeMillis() - initTimeStamp;
			return -1; // no solutions
		}
		lastSolveTimeInMiliSeconds = System.currentTimeMillis() - initTimeStamp;
		int temp;
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++) {
				temp = board[i][j];
				board[i][j] = solvedBoard[i][j];
				solvedBoard[i][j] = temp;				
			}
		}
		solve(false);
		lastValidateTimeInMiliSeconds = System.currentTimeMillis() - initTimeStamp;
		if (!equals(board, solvedBoard)) {
			for (int i=0; i<9; i++)
				for (int j=0; j<9; j++)
					board[i][j] = solvedBoard[i][j];
			return 2; // multiple solutions
		}		
		return 1; // single solution
	}
}
