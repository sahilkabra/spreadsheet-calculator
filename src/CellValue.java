
/**
 * A class representing the value of a cell.
 *
 * The value of a cell maybe a NUMBER or an expression.
 *
 * @author sahil
 */
public abstract class CellValue {

  public static CellValue get(String value) {
    if (value.replaceAll("[\\s]", "").matches("\\d+")) {
      //all numeric, return simple value
      return new SimpleValue(value);
    }
    return CompositeValue.get(value);
  }

  @Override
  public String toString() {
    Double value = this.getValue();
    if (value == null) return "";
    return value.toString();
  }

  //abstract methods
  public abstract Double getValue();
  public abstract void setValue(String value);
  public abstract void setValue(Double value);
  public abstract int evaluate(Sheet parent);
}
