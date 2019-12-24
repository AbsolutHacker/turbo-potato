package nick.games.sudoku.api;

import io.vavr.collection.Seq;
import nick.games.sudoku.Cell;

public interface Group {
  Seq<Cell> members();
  Seq<Constraint> constraints();
}
