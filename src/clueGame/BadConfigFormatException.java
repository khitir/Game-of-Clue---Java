package clueGame;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() {
		System.out.println("pick something meaningful");
	}
	
	public BadConfigFormatException(String message) {
		System.out.println("error, can't open file: " + message);
		
	}
}
