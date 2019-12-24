package nick.games.sudoku.constraints;

import io.vavr.collection.Array;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import nick.games.sudoku.Cell;
import nick.games.sudoku.api.Constraint;
import nick.games.sudoku.api.Number;

public final class Constraints {
  public static final Constraint UNIQUENESS = Traversable::isDistinct;
  public static final Constraint COMPLETENESS = seq -> seq.map(Cell::solution).containsAll(Array.of(Number.values()).map(Option::of));
  private Constraints() {}
}
