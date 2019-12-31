package nick.games.sudoku;

import nick.games.sudoku.api.RegularSudoku;
import nick.games.sudoku.api.Digit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static nick.games.sudoku.Solvers.*;

@RunWith(Parameterized.class)
public class ParameterizedSolverTest extends SolverTestUtilities {

  @Parameterized.Parameters
  public static List<Object[]> data() {
    return TestData.easyGames.map(array -> new Object[] {array}).toJavaList();
  }

  @Parameterized.Parameter
  public Digit[][] puzzle;

  @Test
  public void test_hashingSolverOnly() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), puzzle);
    timeBenchmark(testBoard, HashingSolver);
  }

  @Test
  public void test_bothSolvers() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), puzzle);
    timeBenchmark(testBoard, HashingSolver, CompletingSolver);
  }

  @Test
  public void test_hashThriceThenComplete() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), puzzle);
    timeBenchmark(testBoard, HashingSolver, HashingSolver, HashingSolver, CompletingSolver);
  }

}