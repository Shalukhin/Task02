package service;

import java.util.concurrent.Callable;

import entity.MatrixHolder;

public class MatrixSummatorCallable implements Callable<Integer> {

	private MatrixHolder matrix = MatrixHolder.MATRIX;
	private int positionNodaOnMainDiagonal;

	public MatrixSummatorCallable(int positionNodaOnMainDiagonal) {
		super();
		this.positionNodaOnMainDiagonal = positionNodaOnMainDiagonal;
	}

	@Override
	public Integer call() throws Exception {
		int resultSum = 0;
		for (int i = 0; i < matrix.getLength(); i++) {
			resultSum = resultSum + matrix.getValueInNoda(i, positionNodaOnMainDiagonal);
			resultSum = resultSum + matrix.getValueInNoda(positionNodaOnMainDiagonal, i);			
		}
		return Integer.valueOf(resultSum);
	}
}
