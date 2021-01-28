package ufu.facom.lia.board.map;

public enum EFeatures {
	
	PIECE_ADVANTAGE("pieceAdvantage", 4, 1),
	PIECE_DISADVANTAGE("pieceDisadvantage", 4, 2),
	PIECE_THREAT("pieceThreat", 3, 3),
	PIECE_TAKE("pieceTake", 3, 4),
	ADVANCEMENT("advancement", 3, 5),
	DOUBLE_DIAGONAL("doubleDiagonal", 4, 6),
	BACK_ROW_BRIDGE("backRowBridge", 1, 7),
	CENTRE_CONTROL("centreControl", 3, 8),
	X_CENTRE_CONTROL("xCentreControl", 3, 9),
	TOTAL_MOBILITY("totalMobility", 4, 10),
	EXPOSURE("exposure", 3, 11),
	KING_CENTRE_CONTROL("kingCentreControl", 3, 12),
	DIAGONAL_MOMENT("diagonalMoment", 4, 13),
	APEX("apex", 1, 14),
	CRAMP("cramp", 2, 15),
	DENIAL_OF_OCCUPANCY("denielOfOccupancy", 4, 16),
	DYKE("dyke", 3, 17),
	EXCHANGE("exchange", 4, 18),
	FORK("fork", 3, 19),
	GAP("gap", 4, 20),
	BACK_ROW_CONTROL("backRowControl", 1, 21),
	HOLE("hole", 3, 22),
	UNDENIED_MOBILITY("undeniedMobility", 3, 23),
	NODE("node", 3, 24),
	TRIANGLE_OF_OREO("triangleOfOreo", 1, 25),
	POLE("pole", 3, 26),
	THREAT("threat", 3, 27),
	TAKEN("taken", 3, 28);
	
	private String simpleName;
	private int numBits;
	private int key; //define um número de identificação para a função para ser utilizada no método de invocação da função.
	
	private EFeatures(String simpleName, int numBits, int key) {
		this.simpleName = simpleName;
		this.numBits = numBits;
		this.key = key;
	}
	
	public String getName(){
		return this.simpleName;
	}
	
	public int getNumBits(){
		return this.numBits;
	}
	
	public int getKey(){
		return this.key;
	}

}
