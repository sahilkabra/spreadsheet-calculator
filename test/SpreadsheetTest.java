import org.junit.Test;

public class SpreadsheetTest {
  @Test
  public void testSpreadsheet() throws Exception {
    String[] sampleTest = { "3 2", "A2", "4 5 *", "A1", "A1 B2 / 2 +", "3",
        "39 B1 B2 * /" };
    Spreadsheet.evaluate(sampleTest);
  }

  @Test(expected = CycleException.class)
  public void testCycle() throws Exception {
    String[] sampleTest = { "3 2", "A3", "4 5 *", "A1", "A1 B2 / 2 +", "3",
        "39 B1 B2 * /" };
    Spreadsheet.evaluate(sampleTest);
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidExpression() throws Exception {
    String[] sampleTest = { "3 2", "A3", "4 5 *", "A1", "A1 B2 / +", "3",
        "39 B1 B2 * /" };
    Spreadsheet.evaluate(sampleTest);
  }

  @Test(expected = RuntimeException.class)
  public void testNullCellValueInvalidExpression() throws Exception {
    String[] sampleTest = { "3 2",
        "A2", "4 5 *", "A1",
        "A1 B2 / 2 +", "", "39 B1 B2 * /"
        };
    Spreadsheet.evaluate(sampleTest);
  }

  @Test
  public void testSpreadsheetNullCellValue() throws Exception {
    String[] sampleTest = { "3 2",
        "A2", "4 5 *", null,
        "A1 9 / 2 +", "A3", "39 B1 9 * /"
        };
    Spreadsheet.evaluate(sampleTest);
  }
}
