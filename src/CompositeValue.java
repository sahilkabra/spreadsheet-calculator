/**
 * The class representing composite values
 * 
 * If will first check to see if the value contains only of numbers and
 * operators, in that case the expression will be evaluated and return a Simple
 * Value otherwise the expression will be evaluated later.
 * 
 * @author sahil
 */
public class CompositeValue extends CellValue {

	public static CellValue get(String value) {
		if (value.replaceAll("[\\s*+/-]", "").matches("\\d+")) {
			// If only numbers and operators
			return new SimpleValue(Expression.evaluate(value));
		}
		return new CompositeValue(value);
	}

	@Override
	public int evaluate(Sheet sheet) {
		String[] components = expression.split(" ");
		StringBuilder referencesResolved = new StringBuilder();
		for (String component : components) {
			if (component.matches("\\d+")) {
				// If a number, it is already resolved :)
				referencesResolved.append(component);
			} else if (component.matches("[/*+-]")) {
				//if operator, just add it
				referencesResolved.append(component);
			} else {
				//reference to a cell in the sheet
				int row = (component.codePointAt(0) % 91) - 65; // A = 65, Z =
				int col = Integer.valueOf(component.substring(1)) - 1;
				try {
					//get the value at that cell and append
					referencesResolved.append(sheet.valueAt(row, col));
				} catch (CycleException ce) {
					this.setValue("Cyclic expression: " + this.expression);
					return 1;
				}
			}
			referencesResolved.append(" ");
		}
		// All the cell references have been replaced with numeric values now the value may be a number or an expression

		if (referencesResolved.toString().replaceAll("[\\s.]", "")
				.matches("\\d+")) {
			// all numeric, return simple value
			this.value = Double.valueOf(referencesResolved.toString());
		} else {
			// we now have a postfix expression to evaluate
			this.value = Double.valueOf(Expression.evaluate(referencesResolved
					.toString().trim()));
		}
		return 0;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.expression = value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	private Double value;
	private String expression;

	private CompositeValue(String value) {
		this.setValue(value);
	}
}
