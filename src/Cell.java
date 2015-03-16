/**
 * A class to represent a cell
 * 
 * @author sahil
 */
public class Cell {
	public int evaluate(Sheet sheet) {
		if (this.valueState == STATE.INPROGRESS) {
			this.value.setValue("Cycle detected");
			this.valueState = STATE.ERROR;
			return 1; //CYCLE
		}
		if (this.valueState == STATE.ERROR) {
			return 1;
		}
		this.valueState = STATE.INPROGRESS;
		int returnCode = this.value.evaluate(sheet);
		if (returnCode == 0) this.valueState = STATE.EVALUATED;
		return returnCode;
	}
	
	/**
	 * Returns the value in this cell. If it has not been evaluated, it will be evaluated now
	 * 
	 * @param sheet
	 * @return
	 * 	String representation of the value of the cell
	 * @throws CycleException
	 */
	public String getValue(Sheet sheet) throws CycleException {
		if (this.valueState != STATE.EVALUATED) {
			//If the value for this cell has not yet been evaluated do it now
			evaluate(sheet);
		} else if (this.valueState == STATE.ERROR || this.valueState == STATE.INPROGRESS) {
			throw new CycleException();
		}
		return this.value.getValue().toString();
	}

	public STATE getValueState() {
		return valueState;
	}

	/**
	 * Represents the current state of the cell
	 */
	public enum STATE {
		EVALUATED, INPROGRESS, NONE, ERROR
	}

	public Cell(CellValue value) {
		this.value = value;
		this.valueState = STATE.NONE;
	}
	
	public String toString() {
		return this.value.toString();
	}
	
	private CellValue value;
	private STATE valueState;
}