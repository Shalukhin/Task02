package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exception.ReaderException;

public class ReaderFromFile {
	
	private static final Logger LOGGER = LogManager.getLogger(WriterToFile.class.getName());	
	
	public String read(String fileName) throws ReaderException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		BufferedReader fileReader;
		String result;
		try {
			fileReader = new BufferedReader(new FileReader(file));
			result = fileReader.readLine();
			fileReader.close();
		} catch (IOException e) {
			LOGGER.error("Error_file");
			throw new ReaderException("Error_file");
		} 		
		return result;
	}

}
