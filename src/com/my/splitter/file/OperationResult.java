package com.my.splitter.file;

/**
 * 
 * Container for operation result information
 * 
 * @author mike
 */
public class OperationResult {

	private boolean success;

	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public OperationResult() {
	}

	public OperationResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public OperationResult(boolean success) {
		this(success, "There is no information");
	}

}
