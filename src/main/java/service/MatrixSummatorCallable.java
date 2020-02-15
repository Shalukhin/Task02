package service;

import java.util.concurrent.Callable;

import entity.MatrixHolder;

public class MatrixSummatorCallable implements Callable<Integer> {						//класс для вычисления сумму элементов в строке и столбце матрицы

	private MatrixHolder matrix = MatrixHolder.MATRIX;
	private int positionNodeOnMainDiagonal;

	public MatrixSummatorCallable(int positionNodaOnMainDiagonal) {
		super();
		this.positionNodeOnMainDiagonal = positionNodaOnMainDiagonal;
	}

	@Override
	public Integer call() throws Exception {
		int resultSum = 0;
		for (int i = 0; i < matrix.getLength(); i++) {
			resultSum = resultSum + matrix.getNode(i, positionNodeOnMainDiagonal).getValue();
			resultSum = resultSum + matrix.getNode(positionNodeOnMainDiagonal, i).getValue();			
		}
		return Integer.valueOf(resultSum);
	}
}
