package nick.games.sudoku;

import nick.games.sudoku.api.RegularSudoku;
import nick.games.sudoku.api.Solver;
import org.junit.Test;

import static nick.games.sudoku.SolverTestUtilities.timeBenchmark;

public class CompletingSolverTest {
  public static final Solver unitUnderTest = Solvers.CompletingSolver;

  @Test
  public void test_completingSolver() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), TestData.easyGames.get(4));
    timeBenchmark(testBoard, Solvers.HashingSolver, unitUnderTest);
  }
}
