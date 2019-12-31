package nick.games.sudoku;

import io.vavr.Tuple2;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import nick.games.sudoku.GenerischeDeduktion.Cell;
import nick.games.sudoku.api.Constraint;
import nick.games.sudoku.api.GameVariant;
import nick.games.sudoku.api.Digit;
import nick.games.sudoku.api.CellGroup;
import nick.games.sudoku.api.Solver;

public class ConstraintSolver<Variant extends GameVariant> implements Solver<Variant> {

  private final Board<Variant> board;
  private final Set<Constraint> pendingConstraints;

  public ConstraintSolver(Board<Variant> board) {
    this.board = board;
    this.pendingConstraints = generateConstraints(board);
  }

  @Override
  public Board<Variant> solve(Board<Variant> board) {
    if (board != this.board) {
      throw new IllegalArgumentException("called with different board than was used to construct solver");
    }


    return this.board;
  }

  private Set<Constraint> generateConstraints(Board<Variant> board) {
    return
        Stream.of(Digit.values())
            .crossProduct(board.groups().flatMap(group -> group))
            .map(ContainsConstraintImpl::new)
            .collect(HashSet.collector());
  }

  private static class ContainsConstraintImpl implements Constraint {
    final Stream<Cell> subjects;
    final Digit number;

    ContainsConstraintImpl(Tuple2<Digit, Stream<Cell>> parameters) {
      this.number = parameters._1;
      this.subjects = parameters._2;
    }

    @Override
    public boolean fulfilled() {
      return subjects.flatMap(CellGroup::solution).existsUnique(number::equals);
    }

    @Override
    public void apply() {
      if (!subjects.flatMap(CellGroup::solution).contains(number)) {
        subjects.map(cell -> new Tuple2<>(cell, cell.candidates()))
            .filter(cellWithCandidates -> cellWithCandidates._2.contains(number))
            .map(Tuple2::_1)
            .transform(candidatesForNumber -> {
              if (candidatesForNumber.size() == 1) {
                return candidatesForNumber;
              } else {
                return Stream.<CellGroup>empty();
              }
            })
            .forEach(cell -> cell.solve(number));
      }
    }
  }
}
