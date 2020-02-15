package dao;

import entity.MatrixHolder;
import exception.DAOException;

public interface MatrixDAO {
	
	boolean writeMatrixState(MatrixHolder matrix) throws DAOException;
	
	boolean writeSumInfo(String threadName, int sum) throws DAOException;

}
