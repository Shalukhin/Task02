package service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import entity.MatrixHolder;

public class MatrixChangerThread extends Thread {
	
	private MatrixHolder matrix = MatrixHolder.MATRIX;

	public MatrixChangerThread(int name) {
		
		super(String.valueOf(name));
	}

	@Override
	public void run() {
		
		int nameInt = Integer.valueOf(this.getName());
		int positionNodaOnMainDiagonal;
		int[] positionSecondNoda;
		do {
			positionNodaOnMainDiagonal = getRandomPosition(matrix);
		} while (!matrix.setValueInNoda(positionNodaOnMainDiagonal, positionNodaOnMainDiagonal, nameInt));
		
		do {
			positionSecondNoda = getRandomNoda(positionNodaOnMainDiagonal);
		} while(!matrix.setValueInNoda(positionSecondNoda[0], positionSecondNoda[1], nameInt));		
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<Integer> future = es.submit(new MatrixSummatorCallable(positionNodaOnMainDiagonal));
		
		
	}
	
	private int[] getRandomNoda(int positionOnMainDiagonal) {
		int[] noda = new int[2];
		int ramdomPosition = (int) (Math.random() * matrix.getLength());
		if (new Random().nextBoolean()) {
			noda[0] = positionOnMainDiagonal;
			noda[1] = ramdomPosition;
		} else {
			noda[0] = ramdomPosition;
			noda[1] = positionOnMainDiagonal;
		}
		return noda;
	}
	
	private int getRandomPosition(MatrixHolder matrix) {
		return (int) (Math.random() * matrix.getLength());
	}
	
	

}
