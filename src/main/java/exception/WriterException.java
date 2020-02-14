package exception;

public class WriterException extends Exception {
	
	private static final long serialVersionUID = 4146798846403990097L;

	public WriterException() {
		super();
	}

	public WriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriterException(String message) {
		super(message);
	}

	public WriterException(Throwable cause) {
		super(cause);
	}

}
