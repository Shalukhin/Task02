package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exception.InvalidValueException;
import util.ProjectConstant;

public enum MatrixHolder {
	MATRIX;
	
	private static final Logger LOGGER = LogManager.getLogger(MatrixHolder.class.getName());
	
	private Node[][] matrix;
	{
		int size = ProjectConstant.DEFAULT_MATRIX_SIZE;
		try {
			setMatrixSize(size);
		} catch (InvalidValueException e) {
			initMatrix(5);
		}
	}
	
	public void setMatrixSize(int size) throws InvalidValueException {
		if (size < 1) {
			LOGGER.error("Error_invalid_value_matrix_size");
			throw new InvalidValueException("Error_invalid_value_matrix_size");			
		}
		initMatrix(size);		
	}
	
	private void initMatrix(int size) {
		matrix = new Node[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = new Node();
			}
		}
	}
	
	public Node getNode(int line, int column) {
		return matrix[line][column];
	}
	
	public int getLength() {
		return matrix.length;
	}

}
