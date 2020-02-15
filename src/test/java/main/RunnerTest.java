package main;

import static org.junit.Assert.assertNotEquals;

import java.io.File;

import org.junit.Test;

import util.ProjectConstant;

public class RunnerTest {
	
	@Test
	public void creatingNoEmptyFileWithResultWorkApplicationTest() {		
		File file = new File(ProjectConstant.NAME_RESULT_FILE);
		long expected = file.length();		
		Runner.main(null);			
		assertNotEquals(expected, file.length());
	}
}
