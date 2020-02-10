package service;

public enum MatrixHolder {
	MATRIX(5);
	
	private Noda[][] matrix;
	private MatrixHolder(int n) {
		matrix = new Noda[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
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
