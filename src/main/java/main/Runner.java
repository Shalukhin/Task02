package main;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import entity.MatrixHolder;
import service.MatrixChangerThread;
import service.MatrixRefresherAccessToNodes;
import service.MatrixSaverThread;
import util.ProjectConstant;

public class Runner {
	
	private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());
	
	
	public static void main(String[] args) {
		
		int[] matrixInit = {ProjectConstant.DEFAULT_MATRIX_SIZE, ProjectConstant.DEFAULT_COUNT_THREAD_GROUP};

		MatrixHolder matrix = MatrixHolder.MATRIX;
		matrix.setMatrixSize(matrixInit[0]);
		
		Semaphore semaphoreAfterSaveMatrix = new Semaphore(1);
		Semaphore semaphoreAfterRefreshMatrix = new Semaphore(1);
		CyclicBarrier barrierAfterChange = new CyclicBarrier(5, new MatrixSaverThread(semaphoreAfterSaveMatrix));
		CyclicBarrier barrierAfterSummation = new CyclicBarrier(5,  new MatrixRefresherAccessToNodes(semaphoreAfterRefreshMatrix));	
		
		for (int m = 0; m < matrixInit[1]; m++) {
			try {
				semaphoreAfterSaveMatrix.acquire();				
				semaphoreAfterRefreshMatrix.acquire();
			} catch (InterruptedException e) {
				LOGGER.error("Error_with_semaphore");
			}
			for (int i = 0; i < matrixInit[0]; i++) {
				Thread thread = new MatrixChangerThread(i + 1 + 5 * m, barrierAfterChange, barrierAfterSummation); 				
				thread.start();
			}			
		}
		
		System.out.printf("Application ended. Result in \"%s\" file.", ProjectConstant.NAME_RESULT_FILE);
	}
	
	public static void printMatr(MatrixHolder matrix) {
		for (int i = 0; i < matrix.getLength(); i++) {
			for (int j = 0; j < matrix.getLength(); j++) {
				System.out.print(matrix.getNode(i, j).getValue() + " ");
			}
			System.out.println();
		}
	}
}
