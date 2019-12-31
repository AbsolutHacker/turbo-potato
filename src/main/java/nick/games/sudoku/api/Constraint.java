package nick.games.sudoku.api;

public interface Constraint {
  boolean fulfilled();
  void apply();
}
