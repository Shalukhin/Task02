package service;

import java.util.concurrent.Semaphore;

import entity.MatrixHolder;

public class MatrixRefresherAccessToNodes implements Runnable {					// данный класс вызывается при открытии циклического барьера "barrierAfterSummation" 
																				// и служит для возобновления доступа к нодам матрицы для следующей группы потоков
	private MatrixHolder matrix = MatrixHolder.MATRIX;
	private Semaphore semaphoreAfterRefreshMatrix;	
	
	public MatrixRefresherAccessToNodes(Semaphore semaphoreAfterRefreshMatrix) {
		super();
		this.semaphoreAfterRefreshMatrix = semaphoreAfterRefreshMatrix;
	}

	@Override
	public void run() {
		
		for (int i = 0; i < matrix.getLength(); i++) {
			for (int j = 0; j < matrix.getLength(); j++) {
				matrix.getNode(i, j).setAccess(true);
			}
		}
		semaphoreAfterRefreshMatrix.release();
	}
}
