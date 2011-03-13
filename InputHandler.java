//package cli;

public interface InputHandler {
	public void success(String value, Object arg);

	public void failure(String value, Object arg);

	public boolean validate(String value, Object arg);
}
