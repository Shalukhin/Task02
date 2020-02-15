package exception;

public class ReaderException extends Exception {
	
	private static final long serialVersionUID = -65597278390208822L;

	public ReaderException() {
		super();
	}

	public ReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReaderException(String message) {
		super(message);
	}

	public ReaderException(Throwable cause) {
		super(cause);
	}

}
