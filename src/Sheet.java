/**
 * A class to represent the sheet.
 * 
 * @author sahil
 */
public class Sheet {

	/**
	 * Goes through every cell in the sheet and evaluates all the cells once
	 * again. Any composite references still not evaluated will be evaluated now
	 */
	public void evaluate() {
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				int result = this.cells[row][col].evaluate(this);
				if (result != 0) return; //In case of a non zero error code, stop processing
			}
		}
	}

	/**
	 * Return the value at <code>row</code> and <code>col</code>
	 * If the value has not yet been evaluated, tries to process it right now
	 * 
	 * @param row
	 * @param col
	 * @return the value at the cell in String format
	 */
	public String valueAt(int row, int col) {
		Cell cell = this.cells[row][col];
		return cell.getValue(this);
	}

	/**
	 * Add the <code>cellValue</code> at the specified <code>row</code> and <code>col</code>
	 * 
	 * @param row
	 * @param col
	 * @param cellValue
	 * @return
	 */
	public Sheet put(int row, int col, String cellValue) {
		this.cells[row][col] = new Cell(CellValue.get(cellValue));
		return this;
	}

	public Sheet(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.cells = new Cell[rows][cols];
	}

	public String toString() {
		StringBuilder sheet = new StringBuilder("<Sheet>\n");
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				sheet.append("\t");
				sheet.append(cells[row][col].toString());
				sheet.append("\t");
			}
			sheet.append("\n");
		}
		sheet.append("</Sheet>\n");
		return sheet.toString();
	}

	// Private members and getters/setters
	private Cell[][] cells;
	private int rows = 0;
	private int cols = 0;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
}
