package service;

public class MatrixChangerThread extends Thread {
	
	private MatrixHolder matrix = MatrixHolder.MATRIX;

	public MatrixChangerThread(int name) {
		
		super(String.valueOf(name));
	}

	@Override
	public void run() {
		
		int nameInt = Integer.valueOf(this.getName());
		int rndPositionOnMainDiagonal;
		int[] rndPositionSecondNoda;
		do {
			rndPositionOnMainDiagonal = (int) (Math.random() * matrix.getLength());
		} while (!matrix.setValueInNoda(rndPositionOnMainDiagonal, rndPositionOnMainDiagonal, nameInt));
		
		do {
			rndPositionSecondNoda = getRndNoda(rndPositionOnMainDiagonal);
		} while(!matrix.setValueInNoda(rndPositionSecondNoda[0], rndPositionSecondNoda[1], nameInt));		
	}
	
	private int[] getRndNoda(int positionOnMainDiagonal) {
		int[] noda = new int[2];
		int rndPosition = (int) (Math.random() * matrix.getLength());
		if (Math.random() > 0.5) {
			noda[0] = positionOnMainDiagonal;
			noda[1] = rndPosition;
		} else {
			noda[0] = rndPosition;
			noda[1] = positionOnMainDiagonal;
		}
		return noda;
	}
	
	

}
