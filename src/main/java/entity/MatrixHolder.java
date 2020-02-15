package entity;

import util.ProjectConstant;

public enum MatrixHolder {
	MATRIX;
	
	private Node[][] matrix;
	{
		int size = ProjectConstant.DEFAULT_MATRIX_SIZE;
		setMatrixSize(size);
	}
	
	public void setMatrixSize(int size) {
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
