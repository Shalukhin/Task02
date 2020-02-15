package dao.impl;

import java.util.Formatter;
import java.util.concurrent.Semaphore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.MatrixDAO;
import entity.MatrixHolder;
import exception.DAOException;
import exception.WriterException;
import util.ProjectConstant;
import util.WriterToFile;

public class FileMatrixDAO implements MatrixDAO {
	
	private static FileMatrixDAO instance;
	
	private static final Logger LOGGER = LogManager.getLogger(FileMatrixDAO.class.getName());
	private Semaphore semaphore = new Semaphore(1);
	private WriterToFile writer = new WriterToFile(ProjectConstant.NAME_RESULT_FILE);
	
	private FileMatrixDAO() {};
	public static FileMatrixDAO getInstance() {
		if (instance == null) {
			instance = new FileMatrixDAO();
		}
		return instance;
	}

	@Override
	public boolean writeMatrixState(MatrixHolder matrix) throws DAOException {
		String matrixTextView = makeTextWiewFromMatrix(matrix);
		return write(matrixTextView, "Error_writing_matrix_to_file");			
	}
	
	@Override
	public boolean writeSumInfo(String threadName, int sum) throws DAOException {
		String sumTextView = String.format("Thread \"%s\": sum = %3d", threadName, sum);
		return write(sumTextView, "Error_writing_sum_to_file");		
	}
	
	private String makeTextWiewFromMatrix(MatrixHolder matrix) {
		String result;
		Formatter formatter = new Formatter();
		for (int i = 0; i < matrix.getLength(); i++) {
			for (int j = 0; j < matrix.getLength(); j++) {
				formatter.format("%2s ", String.valueOf(matrix.getNode(i, j).getValue()));
			}
			formatter.format("\n");
		}
		result = formatter.toString();
		formatter.close();
		return result;
	}	
	
	private boolean write(String text, String message) throws DAOException {
		try {
			semaphore.acquire();												//запись в файл контролируется симафором
			return writer.writeToEndFile(text);
		} catch (WriterException e) {
			LOGGER.error(message);
			throw new DAOException(message, e);
		} catch (InterruptedException e) {
			LOGGER.error("Error_in_semaphore");
			throw new DAOException("Error_in_semaphore", e);
		} finally {
			semaphore.release();
		}		
	}	
}
