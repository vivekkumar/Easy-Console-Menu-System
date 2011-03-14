//package cli;

import java.util.ArrayList;
import java.util.List;

public class CliOptionsMenu {
	private List<OptionItem> optionItems = new ArrayList<OptionItem>();
	private int currentOptionIndex;
	
	public CliOptionsMenu(OptionItem []options) {
		for(int i = 0; i < options.length; i++) {
			optionItems.add(options[i]);
		}
		currentOptionIndex = 0;
	}
	
	public CliOptionsMenu() {
		currentOptionIndex = 0;
	}
	
	public void addOption(OptionItem o) {
		optionItems.add(o);
	}
	
	public void addOption(OptionItem o, int index) {
		int len = optionItems.size();
		if(!(index >=0 && index <= (len-1))) {
			optionItems.add(index, o);
		}		
	}
	
	/**
	 * Start taking inputs from the user from first index in the menu
	 */
	public void show() {
		startGettingInputs(0);
	}
	
	/**
	 * Start taking inputs from the user from specified index in the menu
	 * public void show(int itemIndex)
	 * @param itemIndex - An index to the option
	 * @return nothing
	 */
	public void show(int itemIndex) {	
		startGettingInputs(itemIndex);
	}
	
	private void startGettingInputs(int startIndex) {
		int len = optionItems.size();
		if(startIndex >= 0 && startIndex < len) {
			for(int i = startIndex; i < len; i++) {
				boolean state = optionItems.get(i).getInput();
				currentOptionIndex = i;
				if(state){
					if(optionItems.get(i).isConfirmation()) {
						int nextIndex = handleConfirmation(optionItems.get(i));
						if(nextIndex >= 0) {
							show(nextIndex);
							break;
						} else {
							break;
						}
					}
				} else {
					show(currentOptionIndex);
					break;
				}
			}
		}
	}
	
	private int handleConfirmation(OptionItem oi) {
		int index = 0;		
		int yIdx = oi.getYesIndex();
		int nIdx = oi.getNoIndex();
		int len = optionItems.size();
		String value = oi.getValue().toLowerCase();
		
		if(value.equals("y")) {
			index = yIdx;
		} else if(value.equals("n")) {
			index = nIdx;
		} else {
			index = currentOptionIndex;
		}
		
		if(!(index >= 0 && index < len)) return -1;
		else return index;
	}
	
	public int getCurrentOptionIndex() {
		return currentOptionIndex;
	}
	
	public OptionItem getCurrentOptionItem() {
		return getOptionItem(currentOptionIndex);
	}
	
	public OptionItem getOptionItem(int index) {
		try {
			return optionItems.get(currentOptionIndex);
		} catch(Exception e) {
			return null;
		}
	}
	
	public void clearOptions() {
		optionItems.clear();
	}
	
	public boolean hasOptions() {
		return (optionItems.size() > 0 ? true : false);
	}
}
