package nick.games.sudoku;

import io.vavr.collection.Stream;
import io.vavr.control.Option;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class Cell {

    private final Set<Number> candidates;

    private final Set<Cell> row;
    private final Set<Cell> column;
    private final Set<Cell> quadrant;

    public Cell(Number solution, Set<Cell> row, Set<Cell> column, Set<Cell> quadrant) {
      if (solution == null) {
        this.candidates =  EnumSet.allOf(Number.class);
      } else {
        this.candidates = Collections.singleton(solution);
      }

      this.row = row;
      this.column = column;
      this.quadrant = quadrant;
    }

    Option<Number> solution() {
      return Option.when(candidates.size() == 1, candidates.iterator().next());
    }

    boolean solved() {
      return candidates.size() == 1;
    }

  public Option<Number> exclude(Set<Number> nonCandidates) {
    candidates.removeAll(nonCandidates);
    return solution();
  }

  public Option<Number> exclude(Number nonCandidate) {
    candidates.remove(nonCandidate);
    return solution();
  }

    public Stream<Number> getCandidates() {
      return Stream.ofAll(candidates);
    }

    public Stream<Cell> getRow() {
      return Stream.ofAll( row);
    }

    public Stream<Cell> getColumn() {
      return Stream.ofAll( column);
    }

    public Stream<Cell> getQuadrant() {
      return Stream.ofAll( quadrant);
    }

  @Override
  public String toString() {
    return solution().map(Enum::ordinal).map(x -> "" + (x + 1)).getOrElse(" ");
  }
}
