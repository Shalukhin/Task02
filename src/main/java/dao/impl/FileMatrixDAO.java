package dao.impl;

import java.util.Formatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.MatrixDAO;
import entity.MatrixHolder;
import exception.DAOException;
import exception.WriterException;
import util.ProjectConstant;
import util.WriterToFile;

public class FileMatrixDAO implements MatrixDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(FileMatrixDAO.class.getName());
	private WriterToFile writter = new WriterToFile(ProjectConstant.NAME_RESULT_FILE);

	@Override
	public boolean writeMatrixState(MatrixHolder matrix) throws DAOException {
		String matrixTextView = makeTextWiewFromMatrix(matrix);
		try {
			return writter.writeToEndFile(matrixTextView);
		} catch (WriterException e) {
			LOGGER.error("Error_writing_to_file");
			throw new DAOException("Error_writing_to_file", e);
		}		
	}
	
	private String makeTextWiewFromMatrix(MatrixHolder matrix) {
		String result;
		Formatter formatter = new Formatter();
		for (int i = 0; i < matrix.getLength(); i++) {
			for (int j = 0; j < matrix.getLength(); j++) {
				formatter.format("%2s ", String.valueOf(matrix.getValueInNoda(i, j)));
			}
			formatter.format("\n");
		}
		result = formatter.toString();
		formatter.close();
		return result;
	}
}
