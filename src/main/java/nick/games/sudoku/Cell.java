package nick.games.sudoku;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import nick.games.sudoku.api.Number;

import java.util.Set;

public interface Cell {
  Option<Number> solution();

  boolean solved();

  Option<Number> exclude(Set<Number> nonCandidates);

  Option<Number> exclude(Number nonCandidate);

  void solve(Number solution);

  Stream<Number> candidates();

  Stream<Stream<Cell>> groups();

  Stream<Cell> row();

  Stream<Cell> column();

  Stream<Cell> quadrant();
}
