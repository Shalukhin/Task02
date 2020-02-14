package main;

import java.io.IOException;

import dao.impl.FileMatrixDAO;
import entity.MatrixHolder;
import exception.DAOException;
import util.WriterToFile;

public class Test {
	
	public static void main(String[] args) throws IOException, DAOException {
		
		FileMatrixDAO f = new FileMatrixDAO();
		f.writeMatrixState(MatrixHolder.MATRIX);
	}
	
//	BufferedReader reader = new BufferedReader(new FileReader(f));
//	System.out.println(reader.readLine());
//	return false;
	//ClassLoader classLoader = this.getClass().getClassLoader();
			//File file = new File(classLoader.getResource(fileName).getFile());

}
