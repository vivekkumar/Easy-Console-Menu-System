import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OptionItem {
	/* File name for logging */
	private static String thisClassfileName;
	private String label;
	private String value;
	private InputHandler inputHandler;
	private Object callbackArg;
	private boolean isConfirmation;
	private int yesIndex, noIndex;

	public OptionItem(String l, InputHandler iHandler, Object arg) {
		thisClassfileName = this.getClass().getName();
		label = l;
		inputHandler = iHandler;
		callbackArg = arg;
		isConfirmation = false;
		yesIndex = 0;
		noIndex = 0;
	}

	public OptionItem(String l, InputHandler iHandler) {
		thisClassfileName = this.getClass().getName();
		label = l;
		inputHandler = iHandler;
		callbackArg = null;
		isConfirmation = false;
		yesIndex = 0;
		noIndex = 0;
	}

	public OptionItem(String l, int yIndex, int nIndex) {
		thisClassfileName = this.getClass().getName();
		label = l;
		isConfirmation = true;
		inputHandler = null;
		callbackArg = null;
		yesIndex = yIndex;
		noIndex = nIndex;
	}

	public OptionItem(String l, int yIndex) {
		thisClassfileName = this.getClass().getName();
		label = l;
		isConfirmation = true;
		inputHandler = null;
		callbackArg = null;
		yesIndex = yIndex;
		noIndex = -1;
	}

	public boolean getInput() {
		boolean rValue = false;
		System.out.print(this.label + ": ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String value = null;
		try {
			value = br.readLine();
			this.value = value;

			if (!isConfirmation) {
				if (inputHandler.validate(value, callbackArg)) {
					inputHandler.success(value, callbackArg);
					rValue = true;
					Util.trace(LogConstants.DEBUG, "getInput() Valid value ("
							+ value + ") for option '" + this.label + "'",
							thisClassfileName, Util.getLineNumber());
				} else {
					inputHandler.failure(value, callbackArg);
					rValue = false;
				}
			} else {
				rValue = true;
			}
		} catch (IOException e) {
			if (!isConfirmation)
				inputHandler.failure(value, callbackArg);
			rValue = false;
		}
		return rValue;
	}

	public String getValue() {
		return value;
	}

	public int getYesIndex() {
		return yesIndex;
	}

	public int getNoIndex() {
		return noIndex;
	}

	public boolean isConfirmation() {
		return isConfirmation;
	}

	public void setLabel(String l) {
		this.label = l;
	}
}
