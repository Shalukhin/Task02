package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.parser.ParseException;

public class ParserText {
	
	private static final Logger LOGGER = LogManager.getLogger(WriterToFile.class.getName());
	
	public static int[] extractIntFromText(String text) throws ParseException {
		int[] result = new int[2];
		String[] strElements = text.trim().split(" ");
		try {
			
		} catch (Exception e) {
			LOGGER.error("Error_parse");
			throw new ParseException("Error_parse", e);
		}
		for (int i = 0; i < 2; i++) {
			result[i] = Integer.parseInt(strElements[i].trim());
		}
		return result;
	}

}
