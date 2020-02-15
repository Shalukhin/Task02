package main;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import entity.MatrixHolder;
import exception.InvalidValueException;
import service.MatrixChangerThread;
import service.MatrixRefresherAccessToNodes;
import service.MatrixSaverThread;
import util.ParserText;
import util.ProjectConstant;
import util.ReaderFromFile;

public class Runner {
	
	private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());
	
	
	public static void main(String[] args) {
		
		MatrixHolder matrix = MatrixHolder.MATRIX;
		
		int[] matrixInit = {ProjectConstant.DEFAULT_MATRIX_SIZE, ProjectConstant.DEFAULT_COUNT_THREAD_GROUP};
		
		try {
			matrixInit = ParserText.extractIntFromText(new ReaderFromFile().read(ProjectConstant.NAME_INIT_FILE));
			matrix.setMatrixSize(matrixInit[0]);
		} catch (Exception e){
			LOGGER.error("Error_in_init_matrix");
		}		
		
		int countTreadsInOneGroup = matrixInit[0];
		int countGroupOfTreads = matrixInit[1];
		
		Semaphore semaphoreAfterSaveMatrix = new Semaphore(1);
		Semaphore semaphoreAfterRefreshMatrix = new Semaphore(1);
		CyclicBarrier barrierAfterChange = new CyclicBarrier(countTreadsInOneGroup, new MatrixSaverThread(semaphoreAfterSaveMatrix));
		CyclicBarrier barrierAfterSummation = new CyclicBarrier(countTreadsInOneGroup,  new MatrixRefresherAccessToNodes(semaphoreAfterRefreshMatrix));	
		
		for (int m = 0; m < countGroupOfTreads; m++) {
			try {
				semaphoreAfterSaveMatrix.acquire();				
				semaphoreAfterRefreshMatrix.acquire();
			} catch (InterruptedException e) {
				LOGGER.error("Error_with_semaphore");
			}
			for (int i = 0; i < countTreadsInOneGroup; i++) {
				Thread thread;
				try {
					thread = new MatrixChangerThread(i + 1 + countTreadsInOneGroup * m, barrierAfterChange, barrierAfterSummation);
					thread.start();
				} catch (InvalidValueException e) {
					LOGGER.error("Error_in_creating_thread");
					break;
				} 				
				
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
