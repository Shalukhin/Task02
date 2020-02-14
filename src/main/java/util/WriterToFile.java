package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import exception.WriterException;

public class WriterToFile {
	
	private static final Logger LOGGER = LogManager.getLogger(WriterToFile.class.getName());	
	private String fileName;	

	public WriterToFile(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean writeToEndFile(String text) throws WriterException {
		
		File file;
		FileWriter fileWriter;
		long fileLengthBeforeWrite;
		
		try {
			file = new File(fileName);
			fileLengthBeforeWrite = file.length();
			fileWriter = new FileWriter(file, true);
			fileWriter.append(text);
			fileWriter.append("\n");
			fileWriter.close();
		} catch (IOException e) {
			LOGGER.error("Error_file");
			throw new WriterException("Error_file");
		}
		
		return file.length() != fileLengthBeforeWrite;		
	}
}
