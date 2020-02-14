package factory;

import dao.MatrixDAO;
import dao.impl.FileMatrixDAO;

public class DAOFactory {
	
	private static DAOFactory instance;
	private MatrixDAO matrixDAO = new FileMatrixDAO();
	
	private DAOFactory() {};	
	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}
	
	public MatrixDAO getMatrixDAO() {
		return matrixDAO;
	}

}
