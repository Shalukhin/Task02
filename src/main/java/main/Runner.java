package main;

import java.util.ArrayList;

import service.MatrixChangerThread;
import service.MatrixHolder;

public class Runner {
	
	public static void main(String[] args) {
		MatrixHolder matrix = MatrixHolder.MATRIX;
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		for (int i = 0; i < 5; i++) {
			Thread thread = new MatrixChangerThread(i+1); 
			threadList.add(thread);
			thread.start();
		}
		
		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		
		printMatr(matrix);
		
	}
	
	public static void printMatr(MatrixHolder matrix) {
		for (int i = 0; i < matrix.getLength(); i++) {
			for (int j = 0; j < matrix.getLength(); j++) {
				System.out.print(matrix.getValueInNoda(i, j) + " ");
			}
			System.out.println();
		}
	}
}
