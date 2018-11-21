package com.rix.tossit.validation;

import com.rix.tossit.entity.GameHistory;
import com.rix.tossit.exception.InvalidMoveException;
import static com.rix.tossit.utils.TossitConstants.*;

import org.springframework.stereotype.Service;

@Service
public class BasicGameValidator implements ValidatorSvc {
	/*
	 * Players can't move twice in a row
	 * Player can move piece forward - until another piece is reached
	 * Player can move piece sideways - ""
	 * Player can move piece diagonal - ""
	 * Player can toss ball forward, diagonal, sideways to another of their pieces as long as no other piece is between the 
	 * 		tosser and recipient 
	 * 
	 * 
	 */
	
	/*
		Sample Board - Starting Position
		E = Empty Space
		W = White Piece
		X = White Ball
		G = Green Piece
		Y = Green Ball
		
		
		  A B C D E F G H 
		1 W E E Y W E E W
		2 E W E E E E W E
		3 E E W E E W E E
		4 E E E W W E E E
		5 E E E G G E E E
		6 E E G E E G E E
		7 E G E E E E G E
		8 G E E G X E E G
	 */
	
	@Override
	public void validateMove(GameHistory prev, GameHistory attempted) throws InvalidMoveException {
		char [][] prevBoard = createBoard(prev.getPieceLocation());
		char [][] attemptedBoard = createBoard(attempted.getPieceLocation());
		
		Validator v = new Validator(prevBoard, attemptedBoard);
		
		determineMoveValid(v, prevBoard);
		determineCorrectUserMoved(prev, attempted);
		
		//if made it without exceptions, valid move
	}
	
	private void determineCorrectUserMoved(GameHistory prev, GameHistory attempted) throws InvalidMoveException {
		if (prev.getLastMove().equals(attempted.getLastMove()))
			throw new InvalidMoveException("Wrong player moved, take turns! ;)");
			
	}
	
	private void determineMoveValid(Validator v, char[][] prevBoard) throws InvalidMoveException {
		int highRow = Math.max(v.getToRow(),v.getFromRow());
		int lowRow = Math.min(v.getFromRow(), v.getToRow()) + 1;
		int highCol = Math.max(v.getToCol(),v.getFromCol());
		int lowCol = Math.min(v.getFromCol(), v.getToCol()) + 1;
		
		
		
		if (v.isBallOnlyMove()) {
			//subtract 1 from high row/col b/c we dont want to verify the empty space for the final location
			highRow--;
			highCol--;
		}
		else if(v.isBallPawnMove() || v.isPawnOnlyMove()) {
			//nothing 
		}
		else
			throw new InvalidMoveException("Invalid Move. A piece can only move forward/backward, left/right or diagonal and may not move through or onto an existing piece. ");
		
		//Currently checks only pawn move
		//need to check ball move
		
		if (v.isColumnMove()) {
			for (int y = lowRow; y < highRow; y++) {
				//Row changes in a column move, column is same
				System.out.println("prevBoard: " + prevBoard[highCol][y]);
				System.out.println("y: " + y + "   highCol:" + highCol);
				if(prevBoard[highCol][y] != 'E')
					throw new InvalidMoveException("Another piece is in the way of you moving to this location.");
			}
		}
		else if (v.isRowMove()) {
			for (int x = lowCol; x < highCol; x++) {
				//Row changes in a column move, column is same
				System.out.println("prevBoard: " + prevBoard[x][highRow]);
				System.out.println("x: " + x + "   highRow:" + highRow);
				if(prevBoard[x][highRow] != 'E') {
					
					throw new InvalidMoveException("Another piece is in the way of you moving to this location.");
				}
					
			}
		}
		else if (v.isDiagonalMove()) {
			int x1 = lowCol;
			int x2 = highCol;
			int y1, y2;
			boolean isUp = true;
			
			if (x1 == v.getToCol() + 1) {
				y1 = v.getToRow() + 1;
				y2 = v.getFromRow();
				if (lowRow == v.getToRow() + 1)
					isUp = false;
			}
			else {
				y1 = v.getFromRow() + 1;
				y2 = v.getToRow();
				if (lowRow == v.getFromRow() + 1)
					isUp = false;
			}
				
			for (int x = x1, y = y1; x < x2 && y < y2; x++) {
				System.out.println("["+x+"]["+y+"] = " + prevBoard[x][y]);
				if (prevBoard[x][y] != 'E')
					throw new InvalidMoveException("Another piece is in the way of you moving to this location.");
				
				if (isUp) 
					y--;
				else
					y++;
				System.out.println("     y = " + y);
			}
		}
		else {
			throw new InvalidMoveException("Invalid Move. A piece can only move forward/backward, left/right or diagonal");
		}
	}
	
//			  0 1 2 3 4 5 6 7 
//			0 W E E Y W E E W
//			1 E W E E E E W E
//			2 E E W E E W E E
//			3 E E E W W E E E
//			4 E E E G G E E E
//			5 E E G E E G E E
//			6 E G E E E E G E
//			7 G E E G X E E G
	
	//"WEEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG";
	//"EWEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG";
	
	private char[][] createBoard(String loc) {
		String boardString = "";
		char [][] board = new char[8][8];
		for(int r = 0, i = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++, i++) {
				board[c][r] = loc.charAt(i);
				boardString += board[c][r] + " ";
			}
			
			boardString += "\n";
		}
		
		System.out.println("\n" + boardString + "\n");
		
		return board;
	}
	
	public class Validator {
		private int toRow = -1;
		private int toCol = -1;
		private int fromRow = -1;
		private int fromCol = -1;
		private boolean isBallOnlyMove = false;
		private boolean isBallPawnMove = false;
		private boolean isPawnOnlyMove = false;
		
		
		public Validator(char [][] prevBoard, char [][] attemptedBoard) {
			//determine which piece has changed
			for(int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (prevBoard[c][r] != attemptedBoard[c][r]) {
						//Makes no difference which is from and which is to
						if (toRow < 0 && toCol < 0) {
							toRow = r;
							toCol = c;
						} else {
							fromRow = r;
							fromCol = c;
						}
					}
				}
			}
			
			char prev1 = prevBoard[toCol][toRow];
			char att1 = attemptedBoard[toCol][toRow];
			char prev2 = prevBoard[fromCol][fromRow];
			char att2 = attemptedBoard[fromCol][fromRow];
			
			System.out.println("firstCheck");
			System.out.println("p1:"+prev1);
			System.out.println("att1:"+att1);
			System.out.println("p2:"+prev2);
			System.out.println("att2:"+att2);
			
			System.out.println("about to check which type of piece move it is");
			
			//PawnOnlyMove
			if ((prev1 == EMPTY_SPACE && (att1 == WHITE_PAWN || att1 == GREEN_PAWN)) ||
				(att1 == EMPTY_SPACE && (prev1 == WHITE_PAWN || prev1 == GREEN_PAWN))) {
				System.out.println("Pawn Only 1/2");
				//Verify second half of move is valid
				if (verifySecondHalf(prev1, att1, prev2, att2)) {
					System.out.println("Pawn Only 2/2");
					isPawnOnlyMove = true;
				}
					
			}
			//BallPawnMove
			else if ((prev1 == EMPTY_SPACE && (att1 == WHITE_BALL || att1 == GREEN_BALL)) ||
				(att1 == EMPTY_SPACE && (prev1 == WHITE_BALL || prev1 == GREEN_BALL))) {
				System.out.println("Ball Pawn  1/2");
				if (verifySecondHalf(prev1, att1, prev2, att2) && 
						Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 1 || Math.abs(toCol - fromCol) == 0 ||
						Math.abs(toCol - fromCol) == 1 && Math.abs(toRow - fromRow) == 1 || Math.abs(toRow - fromRow) == 0)				
					isBallPawnMove = true;
			}
			//BallOnlyMove
			else if (prev1 == WHITE_BALL && att1 == GREEN_PAWN || prev1 == GREEN_BALL && att1 == WHITE_PAWN ||
					att1 == WHITE_BALL && prev1 == GREEN_PAWN || att1 == GREEN_BALL && prev1 == WHITE_PAWN) {
				System.out.println("Ball Only 2/2");
				if (verifySecondHalf(prev1, att1, prev2, att2))
					isBallOnlyMove = true;
			}
			
			System.out.println("");
		}
		
		public boolean isRowMove() {
			return fromRow == toRow && fromCol != toCol;
		}
		
		public boolean isColumnMove() {
			return fromRow != toRow && fromCol == toCol;
		}
		
		public boolean isDiagonalMove() {
			return Math.abs((fromRow - toRow) / (fromCol - toCol)) == 1;
		}
		
		public boolean movedOnce() {
			boolean movedOnce = false;
			if (isRowMove())
				movedOnce = Math.abs(fromCol - toCol) == 1;
			else if (isColumnMove()) 
				movedOnce = Math.abs(fromRow - toRow) == 1;
			else if (isDiagonalMove())
				movedOnce = Math.abs(fromRow - toRow) == 1 && Math.abs(fromCol - toCol) == 1;
			
			
			return movedOnce;
		}
		
		private boolean verifySecondHalf(char prev1, char att1, char prev2, char att2) {
			System.out.println("toCol:"+toCol);
			System.out.println("toRow:"+toRow);
			System.out.println("fromCol:"+fromCol);
			System.out.println("fromRow:"+fromRow);
			
			
			System.out.println("p1:"+prev1);
			System.out.println("att1:"+att1);
			System.out.println("p2:"+prev2);
			System.out.println("att2:"+att2);
			return prev1 == att2 && prev2 == att1;
		}
		
		public int getToRow() {
			return toRow;
		}

		public void setToRow(int toRow) {
			this.toRow = toRow;
		}

		public int getToCol() {
			return toCol;
		}

		public void setToCol(int toCol) {
			this.toCol = toCol;
		}

		public int getFromRow() {
			return fromRow;
		}

		public void setFromRow(int fromRow) {
			this.fromRow = fromRow;
		}

		public int getFromCol() {
			return fromCol;
		}

		public void setFromCol(int fromCol) {
			this.fromCol = fromCol;
		}

		public boolean isBallOnlyMove() {
			return isBallOnlyMove;
		}

		public void setBallOnlyMove(boolean isBallOnlyMove) {
			this.isBallOnlyMove = isBallOnlyMove;
		}

		public boolean isBallPawnMove() {
			return isBallPawnMove;
		}

		public void setBallPawnMove(boolean isBallPawnMove) {
			this.isBallPawnMove = isBallPawnMove;
		}

		public boolean isPawnOnlyMove() {
			return isPawnOnlyMove;
		}

		public void setPawnOnlyMove(boolean isPawnOnlyMove) {
			this.isPawnOnlyMove = isPawnOnlyMove;
		}
	}

}
