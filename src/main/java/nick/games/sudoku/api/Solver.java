package nick.games.sudoku.api;

import nick.games.sudoku.Board;

public interface Solver {
  <T extends GameVariant> Board<T> solve(Board<T> board);
}
