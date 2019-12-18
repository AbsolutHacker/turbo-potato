package nick.games.sudoku;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class XmlInputReaderTestToo {

  @Test
  public void test_readFile() {
    File f = new File("easy.opensudoku");
    final Board board = new Board(XmlInputReader.read(f).get(0));
    final HashingSolver solver = new HashingSolver(board);

    System.out.println(board);

    for (int i = 1; !board.solved() && i <= 1000; i++) {
    final long startTime = System.nanoTime();
    System.out.println(">> Begin run #" + i + " at " + startTime);
    solver.solve();
    final long stopTime = System.nanoTime();
    System.out.println(board);
    System.out.println(">> End run #" + i + " at " + stopTime);
    System.out.println(">> Took " + (stopTime - startTime) + "ns\n");
    }

  }

  private void print(Number[][] board) {
    assert board.length == Board.SIZE;
  }
}