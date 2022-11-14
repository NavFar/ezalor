package ir.farahmand.ezalor.services;

public class ContainerOutput {
	private boolean hasError;
	private boolean hasOutput;
	private String message;
	
	public ContainerOutput() {
		this.hasError = false;
		this.hasOutput = false;
		this.message = new String();
	}
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public boolean hasOutput() {
		return hasOutput;
	}
	public void setHasOutput(boolean hasOutput) {
		this.hasOutput = hasOutput;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
