package nick.games.sudoku;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import nick.games.sudoku.api.CellGroup;
import nick.games.sudoku.api.Digit;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class CellImpl implements CellGroup {

  private final Set<Digit> candidates;

  private final Seq<? extends Seq<CellGroup>> groups;

  public CellImpl(Digit solution, Set<CellGroup> row, Set<CellGroup> column, Set<CellGroup> quadrant) {
    this(solution, List.of(row, column, quadrant));
  }

  public CellImpl(Digit solution, Seq<? extends Seq<CellGroup>> groups) {
    if (solution == null) {
      this.candidates = EnumSet.allOf(Digit.class);
    } else {
      this.candidates = Collections.singleton(solution);
    }

    this.groups = groups;
  }

  @Override
  public Option<Digit> solution() {
    return Option.when(candidates.size() == 1, candidates.iterator().next());
  }

  @Override
  public boolean solved() {
    return candidates.size() == 1;
  }

  @Override
  public Option<Digit> exclude(Digit nonCandidate) {
    candidates.remove(nonCandidate);
    return solution();
  }

  @Override
  public void solve(Digit solution) {
    candidates.clear();
    candidates.add(solution);
  }

  @Override
  public Stream<Digit> candidates() {
    return Stream.ofAll(candidates);
  }

  @Override
  public Stream<? extends Seq<CellGroup>> groups() {
    return Stream.ofAll(groups);
  }

  @Override
  public Stream<? extends CellGroup> members() {
    return null;
  }

  @Override
  public Stream<Digit> exclusions() {

    return null;
  }

  @Override
  public String toString() {
    return solution().map(Enum::ordinal).map(x -> "" + (x + 1)).getOrElse(" ");
  }
}
