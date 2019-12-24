package nick.games.sudoku.api;

import io.vavr.collection.Seq;
import nick.games.sudoku.Cell;

public interface Constraint {
  boolean fulfilled(Seq<Cell> group);
//  void apply(Seq<Cell> group);
}
