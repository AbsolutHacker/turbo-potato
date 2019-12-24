package nick.games.sudoku;

import nick.games.sudoku.api.RegularSudoku;
import org.junit.Test;

import static nick.games.sudoku.SolverTestUtilities.timeBenchmark;
import static nick.games.sudoku.Solvers.*;

public class SolverTest {

  @Test
  public void test_hashAndComplete() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), TestData.easyGames.get(4));
    timeBenchmark(testBoard, HashingSolver, CompletingSolver);
  }

  @Test
  public void test_hashThriceAndComplete() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), TestData.easyGames.get(4));
    timeBenchmark(testBoard, HashingSolver, HashingSolver, HashingSolver, CompletingSolver);
  }

  @Test
  public void test_completeThenHash() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), TestData.easyGames.get(4));
    timeBenchmark(testBoard, CompletingSolver, HashingSolver);
  }
}
