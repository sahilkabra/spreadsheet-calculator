/**
 * This class represents a simple Number is stored in the cell.
 * No further calculation required
 *
 * @author sahil
 *
 */
public class SimpleValue extends CellValue {

  public SimpleValue(String value) {
    this.setValue(value);
  }

  @Override
  public void setValue(String value) {
    this.setValue(Double.parseDouble(value));
  }

  @Override
  public void setValue(Double value) {
    this.value = value;
  }

  @Override
  public Double getValue() {
    return this.value;
  }

  @Override
  public int evaluate(Sheet parent) {
    return 0;
  }

  //private members
  private Double value;
}
