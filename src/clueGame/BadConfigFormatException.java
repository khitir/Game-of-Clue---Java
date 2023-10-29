package clueGame;

/*
 * Authors: John Taylor and Zakaria Khitirishvili
 * BadConfigFormatException
 * 		A catch all exception for any problems with the Clue setup files
 */

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		System.out.println("Initialization from setup files failed");
	}

	public BadConfigFormatException(String message) {
		System.out.println("Error: " + message);
	}
}
