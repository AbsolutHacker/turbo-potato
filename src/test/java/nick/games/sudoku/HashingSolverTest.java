package nick.games.sudoku;

import io.vavr.Tuple2;
import nick.games.sudoku.api.GameVariant;
import nick.games.sudoku.api.RegularSudoku;
import nick.games.sudoku.api.Solver;
import nick.games.sudoku.api.Number;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class HashingSolverTest extends SolverTestUtilities {

  private static final Solver unitUnderTest = Solvers.HashingSolver;

  @Parameterized.Parameters
  public static List<Object[]> data() {
    return TestData.easyGames.map(array -> new Object[] {array}).toJavaList();
  }

  @Parameterized.Parameter
  public Number[][] puzzle;

  @Test
  public void test_hashingSolverOnly() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), puzzle);
    timeBenchmark(testBoard, unitUnderTest);
  }

  @Test
  public void test_bothSolvers() {
    final Board<RegularSudoku> testBoard = new Board<>(new RegularSudoku(), puzzle);
    timeBenchmark(testBoard, unitUnderTest, Solvers.CompletingSolver);
  }

}