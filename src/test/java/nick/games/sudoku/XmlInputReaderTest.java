package nick.games.sudoku;

import io.vavr.collection.Iterator;
import nick.games.sudoku.api.Digit;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class XmlInputReaderTest {

  @Test
  public void test_readFile() {
    File f = new File("src/test/resources/easy.opensudoku");
    final Iterator<Digit[][]> iterator = TestData.easyGames.iterator();
    XmlInputReader.read(f).forEach(numbers -> assertTrue(Arrays.deepEquals(numbers, iterator.next())));
  }

  private static String print(Digit[][] board) {
    final StringBuilder stringBuilder = new StringBuilder("new Number[][] {\n");
    for (Digit[] row : board) {
      stringBuilder.append(String.format("{%s, %s, %s, %s, %s, %s, %s, %s, %s},\n", (Object[]) row));
    }
    stringBuilder.append("},");
    return stringBuilder.toString();
  }
}