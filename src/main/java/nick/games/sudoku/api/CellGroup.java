package nick.games.sudoku.api;

import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

/**
 * A group of cells. May contain only a single cell.
 */
public interface CellGroup {
  /**
   * All sub-groups contained in this group.
   * @return
   */
  Stream<? extends CellGroup> members();

  /**
   * A stream of numbers being made illegal for <b>sibling</b> groups by the presence of this group.
   * @return
   */
  Stream<Digit> exclusions();

  default Option<Digit> solution() {
    return exclusions().singleOption();
  }

  boolean solved();

  Option<Digit> exclude(Digit nonCandidate);

  void solve(Digit solution);

  Stream<Digit> candidates();

  /**
   * A stream of groups interacting intersecting on this cell group.
   * @return
   */
  Stream<? extends Seq<CellGroup>> groups();
}
