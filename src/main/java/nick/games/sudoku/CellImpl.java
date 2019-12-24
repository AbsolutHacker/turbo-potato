package nick.games.sudoku;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import nick.games.sudoku.api.Number;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class CellImpl implements Cell {

  private final Set<Number> candidates;

  private final Set<Cell> row;
  private final Set<Cell> column;
  private final Set<Cell> quadrant;

  public CellImpl(Number solution, Set<Cell> row, Set<Cell> column, Set<Cell> quadrant) {
    if (solution == null) {
      this.candidates = EnumSet.allOf(Number.class);
    } else {
      this.candidates = Collections.singleton(solution);
    }

    this.row = row;
    this.column = column;
    this.quadrant = quadrant;
  }

  @Override
  public Option<Number> solution() {
    return Option.when(candidates.size() == 1, candidates.iterator().next());
  }

  @Override
  public boolean solved() {
    return candidates.size() == 1;
  }

  @Override
  public Option<Number> exclude(Set<Number> nonCandidates) {
    candidates.removeAll(nonCandidates);
    return solution();
  }

  @Override
  public Option<Number> exclude(Number nonCandidate) {
    candidates.remove(nonCandidate);
    return solution();
  }

  @Override
  public void solve(Number solution) {
    candidates.clear();
    candidates.add(solution);
  }

  @Override
  public Stream<Number> candidates() {
    return Stream.ofAll(candidates);
  }

  @Override
  public Stream<Stream<Cell>> groups() {
    return Stream.of(row(), column(), quadrant());
  }

  @Override
  public Stream<Cell> row() {
    return Stream.ofAll(row);
  }

  @Override
  public Stream<Cell> column() {
    return Stream.ofAll(column);
  }

  @Override
  public Stream<Cell> quadrant() {
    return Stream.ofAll(quadrant);
  }

  @Override
  public String toString() {
    return solution().map(Enum::ordinal).map(x -> "" + (x + 1)).getOrElse(" ");
  }
}
