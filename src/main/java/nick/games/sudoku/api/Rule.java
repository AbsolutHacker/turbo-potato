package nick.games.sudoku.api;

import io.vavr.control.Option;

public interface Rule {
  Option<Constraint> asConstraint();
}
