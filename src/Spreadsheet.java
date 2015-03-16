import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main class. Processing begins from here
 * First read all the input from the prompt, then begin evaluation
 * 
 * In the first pass, if the value is a number or if it is a Numeric post fix expression,
 * then evaluate it and save the result as the value of the cell
 * 
 * In the second pass, if there are any references to cell, resolve them
 * and then evaluate the post fix expression
 * 
 * Currently only supports + - / *
 * 
 * @throws 
 * 	CycleException if there are any cycles
 * 	RuntimeException if the expression given is invalid
 * 
 * @author sahil
 */
public class Spreadsheet {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<String> lines = new ArrayList<String>();
		while (sc.hasNext()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		String[] linesArray = new String[lines.size()];
		evaluate(lines.toArray(linesArray));
	}

	/**
	 * Evaluate the input that was received
	 * @param lines
	 */
	public static void evaluate(String[] lines) {
		String[] sheetSpec = lines[0].split(" ");
		Sheet sheet = new Sheet(Integer.parseInt(sheetSpec[1]),
				Integer.parseInt(sheetSpec[0]));

		for (int lineNumber = 1; lineNumber < lines.length; lineNumber++) {
			int row = (lineNumber - 1) / sheet.getCols();
			int col = (lineNumber - 1) % sheet.getCols();
			sheet.put(row, col,	lines[lineNumber]);
		}
		//Go through all the cells again and evaluate cells
		sheet.evaluate();
		
		System.out.println(sheet.getCols() + " " + sheet.getRows());
		for (int row = 0; row < sheet.getRows(); row++) {
			for (int col = 0; col < sheet.getCols(); col++) {
				System.out.println(String.format("%.5f", Double.valueOf(sheet.valueAt(row, col))));
			}
		}
	}
}
