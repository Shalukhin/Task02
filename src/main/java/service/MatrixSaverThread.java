package service;

import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.MatrixDAO;
import entity.MatrixHolder;
import exception.DAOException;
import factory.DAOFactory;

public class MatrixSaverThread implements Runnable {												//  данный класс вызывается при открытии циклического барьера "barrierAfterChange"
																									// и служит для записи образа матрица в файл
	private static final Logger LOGGER = LogManager.getLogger(MatrixSaverThread.class.getName());
	private DAOFactory daoFactory = DAOFactory.getInstance();
	private MatrixDAO matrixDAO = daoFactory.getMatrixDAO();
	private MatrixHolder matrix = MatrixHolder.MATRIX;	
	private Semaphore semaphoreAfterSaveMatrix;
	
	public MatrixSaverThread(Semaphore semaphoreAfterSaveMatrix) {
		super();
		this.semaphoreAfterSaveMatrix = semaphoreAfterSaveMatrix;
	}

	@Override
	public void run() {		
		try {
			matrixDAO.writeMatrixState(matrix);
		} catch (DAOException e) {
			LOGGER.error("Error_in_matrix_saver_thread");			
		} finally {
			semaphoreAfterSaveMatrix.release();
		}		
	}
}
