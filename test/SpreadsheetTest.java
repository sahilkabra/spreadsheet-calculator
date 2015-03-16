import org.junit.Test;
import org.junit.Assert;

public class SpreadsheetTest {
        @Test
        public void testSpreadsheet() throws Exception {
                String[] sampleTest = { "3 2", "A2", "4 5 *", "A1",
                                "A1 B2 / 2 +", "3", "39 B1 B2 * /" 
                        };
                Sheet sheet = Spreadsheet.evaluate(sampleTest);
                Assert.assertEquals(2, sheet.getRows());
                Assert.assertEquals(3, sheet.getCols());
                Assert.assertEquals("20.00000", String.format("%.5f", Double.valueOf(sheet.valueAt(0, 0))));
                Assert.assertEquals("20.00000", String.format("%.5f", Double.valueOf(sheet.valueAt(0, 1))));
                Assert.assertEquals("20.00000", String.format("%.5f", Double.valueOf(sheet.valueAt(0, 2))));
                Assert.assertEquals("8.66667", String.format("%.5f", Double.valueOf(sheet.valueAt(1, 0))));
                Assert.assertEquals("3.00000", String.format("%.5f", Double.valueOf(sheet.valueAt(1, 1))));
                Assert.assertEquals("1.50000", String.format("%.5f", Double.valueOf(sheet.valueAt(1, 2))));
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
                Sheet sheet = Spreadsheet.evaluate(sampleTest);
                Assert.assertEquals(2, sheet.getRows());
                Assert.assertEquals(3, sheet.getCols());
                Assert.assertEquals("20.00000", String.format("%.5f", Double.valueOf(sheet.valueAt(0, 0))));
                Assert.assertEquals("20.00000", String.format("%.5f", Double.valueOf(sheet.valueAt(0, 1))));
                Assert.assertTrue("".equals(sheet.valueAt(0,2)));
                Assert.assertEquals("4.22222", String.format("%.5f", Double.valueOf(sheet.valueAt(1, 0))));
                Assert.assertTrue("".equals(sheet.valueAt(0,2)));
                Assert.assertEquals("1.02632", String.format("%.5f", Double.valueOf(sheet.valueAt(1, 2))));
        }
}
