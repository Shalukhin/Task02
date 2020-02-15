package service;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.MatrixDAO;
import entity.MatrixHolder;
import exception.DAOException;
import exception.InvalidValueException;
import factory.DAOFactory;

public class MatrixChangerThread extends Thread {
	
	private static final Logger LOGGER = LogManager.getLogger(MatrixChangerThread.class.getName());
	private DAOFactory daoFactory = DAOFactory.getInstance();
	private MatrixDAO matrixDAO = daoFactory.getMatrixDAO();
	private MatrixHolder matrix = MatrixHolder.MATRIX;
	private CyclicBarrier barrierAfterChange;
	private CyclicBarrier barrierAfterSummation;

	public MatrixChangerThread(int name, CyclicBarrier barrierAfterChange, CyclicBarrier barrierAfterSummation) throws InvalidValueException {
		super(String.valueOf(name));
		if (barrierAfterChange == null || barrierAfterSummation == null) {
			LOGGER.error("Error_invalid_value_barriers");
			throw new InvalidValueException("Error_invalid_value_barriers");
		}		
		this.barrierAfterChange = barrierAfterChange;
		this.barrierAfterSummation = barrierAfterSummation;
	}

	@Override
	public void run() {
		
		int nameInt = Integer.valueOf(this.getName());
		int positionNodeOnMainDiagonal;
		int[] positionSecondNode;
		do {																									//ищем не занятую ноду на диагонали и записываем в неё цифру из имени потока
			positionNodeOnMainDiagonal = getRandomPosition(matrix);
		} while (!matrix.getNode(positionNodeOnMainDiagonal, positionNodeOnMainDiagonal).setValue(nameInt));
		
		do {																									//ищем не занятую ноду в строке/столбце и записываем в неё цифру из имени потока
			positionSecondNode = getRandomNode(positionNodeOnMainDiagonal);
		} while(!matrix.getNode(positionSecondNode[0], positionSecondNode[1]).setValue(nameInt));
		
		try {																									//ждём у барьера пока все параллельные потоки завершат запись в ноды
			barrierAfterChange.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			LOGGER.error("Error_in_barrier_after_change");
		}		
		
		ExecutorService es = Executors.newSingleThreadExecutor();												//создаём и запускаем Callable-поток, который посчитает необходимую сумму 
		Future<Integer> future = es.submit(new MatrixSummatorCallable(positionNodeOnMainDiagonal));				//(можно было и просто в текущем потоке посчитать сумму, но так интереснее)
		es.shutdown();
		
		int sum = 0;
		try {
			sum = future.get().intValue();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("Error_in_Future_value");
		}
		
		try {																//записываем полученную сумму в файл
			matrixDAO.writeSumInfo(this.getName(), sum);
		} catch (DAOException e) {
			LOGGER.error("Error_in_write_sum_info");
		}
		
		try {															 //ждём у барьера пока все параллельные потоки завершат запись своих сумм в файл
			barrierAfterSummation.await();								 //(запись в файл контролируется симафором (см класс FileMatrixDAO) для исключения коллизий)
		} catch (InterruptedException | BrokenBarrierException e) {
			LOGGER.error("Error_in_barrier_after_summation");
		}		
		
	}
	
	private int[] getRandomNode(int positionOnMainDiagonal) {
		int[] node = new int[2];
		int ramdomPosition = getRandomPosition(matrix);
		if (new Random().nextBoolean()) {
			node[0] = positionOnMainDiagonal;
			node[1] = ramdomPosition;
		} else {
			node[0] = ramdomPosition;
			node[1] = positionOnMainDiagonal;
		}
		return node;
	}
	
	private int getRandomPosition(MatrixHolder matrix) {
		return (int) (Math.random() * matrix.getLength());
	}
}
