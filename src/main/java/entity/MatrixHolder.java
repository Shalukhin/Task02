package entity;

import util.ProjectConstant;

public enum MatrixHolder {
	MATRIX;
	
	private Noda[][] matrix;
	{
		int size = ProjectConstant.DEFAULT_MATRIX_SIZE;
		setMatrixSize(size);
	}
	
	public void setMatrixSize(int size) {
		matrix = new Noda[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = new Noda();
			}
		}
	}
	
	public boolean setValueInNoda(int line, int column, int value) {
		return matrix[line][column].setValue(value);
	}
	
	public int getValueInNoda(int line, int column) {
		return matrix[line][column].getValue();
	}
	
	public int getLength() {
		return matrix.length;
	}

}
