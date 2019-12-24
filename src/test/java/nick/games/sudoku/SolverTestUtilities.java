package nick.games.sudoku;

import io.vavr.Tuple2;
import nick.games.sudoku.api.GameVariant;
import nick.games.sudoku.api.Solver;

import static org.junit.Assert.fail;

public class SolverTestUtilities {
  private static final long MAX_TIME_NANOSECONDS = 3_000_000_000L;
  private static final int MAX_ITERATIONS = 10;

  public static <EntryType extends Enum<EntryType>, GroupType extends Enum<GroupType>, T extends GameVariant<EntryType, GroupType>> Tuple2<Integer, Long> timeBenchmark(Board<T> board, Solver... solvers) {
    System.out.println("Initial board:" + board.toString());
    long timeToSolve = 0L;
    int runsToSolve = 0;
    for (int iteration = 1; iteration <= MAX_ITERATIONS; iteration++)
    while (!board.solved() && timeToSolve <= iteration*MAX_TIME_NANOSECONDS) {
      timeToSolve += SolverTestUtilities.run(solvers[(iteration - 1) % solvers.length], board);
      runsToSolve++;
    }
    if (!board.solved()) {
      fail("Unit failed to solve puzzle in " + MAX_TIME_NANOSECONDS + "ns per iteration");
    }
    System.out.println("Solved board:" + board.toString());
    System.out.printf("Took % 2d runs, % ,10d ns\n\n", runsToSolve, timeToSolve);
    return new Tuple2<>(runsToSolve, timeToSolve);
  }

  public static <EntryType extends Enum<EntryType>, GroupType extends Enum<GroupType>, T extends GameVariant<EntryType, GroupType>> long run(Solver solver, Board<T> board) {
    final long startTime = System.nanoTime();
    solver.solve(board);
    final long stopTime = System.nanoTime();
    return (stopTime - startTime);
  }
}
