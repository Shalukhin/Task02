package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterToFile {
	
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

	public boolean writeToEndFile(String text) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		
		long fileLengthBeforeWrite = file.length();

		FileWriter fileWriter = new FileWriter(file, true);

		fileWriter.append(text);
		fileWriter.append("\n");
		fileWriter.close();

		if (file.length() != fileLengthBeforeWrite) {
			return true;
		} else {
			return false;
		}
	}

}
