package nick.games.sudoku;

import io.vavr.collection.Stream;

import io.vavr.control.Option;
import nick.games.sudoku.api.CellGroup;
import nick.games.sudoku.api.Digit;

import java.util.EnumSet;
import java.util.Set;

public class GenerischeDeduktion {

  // a cell is a group of cells

  // given a rule, applying it to a cell group produces a set of impossibility constraints
  // (= "exclusions") affecting a (possibly different) cell group,
  //
  // e.g. applying the rule of uniqueness to a cell group produces for every "solved" number impossibility constraints
  // forbidding the other cells in the group to contain the same number.

  // a rule can be a set of rules
  // rules can be additively combined to produce (deduce) further constraints,
  //
  // e.g. combining rules of uniqueness for multiple numbers may give constraints that do not require definitive solutions
  // for cells, but only permutations thereof,
  // e.g. if a set of N numbers are possible solutions only for a set of exactly N cells, merging those N cells
  // into a group having as single "group solution" the set of those N numbers in unknown permutation,
  // i.e. the group solution represents the union of all possible unknown solutions/permutations of those N numbers,
  // producing impossibilities of the same weight as those from N definitive solutions without requiring those solutions
  // to be known.

  // 0. split merged subgroups
  // 1. apply exclusions from last round
  // 2. generate exclusions for new solutions, if any goto 1
  // 3. try to find splittable subgroups to merge
  // 4. merge and generate exclusions

  /**
   * Sudoku by Divide'n'Conquer
   *
   *  1. Start with a single group containing all givens. = Cell.group(Number[] givenSolutions)
   *  2. Try to split off solved groups, if any. ( actual solved cells + unknown permutations )
   *  3. Generate constraints for the solved groups. Repeat Step 2.
   */

  abstract static class Cell implements CellGroup {

    public abstract Stream<Digit> candidates();

    public Stream<Cell> members() {
      return Stream.of(this);
    }

    public Stream<Digit> exclusions() {
      return solved()
          ? candidates()
          : Stream.empty();
    }

    public boolean solved() {
      return candidates().corresponds(members(), ((number, cell) -> true));
    }

    boolean reducible() {
      return !members().map(this::equals).reduce(Boolean::logicalAnd);
    }

    public static Cell solved(Digit solution) {
      return new SolvedCell(solution);
    }

    public static Cell empty() {
      return new EmptyCell();
    }

    public static Cell group(Digit[] givenSolutions) {
      return new CompoundCell(givenSolutions);
    }

    @Override
    public Option<Digit> exclude(Digit nonCandidate) {
      return null;
    }

    @Override
    public void solve(Digit solution) {

    }

    @Override
    public Stream<CellGroup> groups() {
      return null;
    }

    static final class SolvedCell extends Cell {
      private final Digit solution;

      public SolvedCell(Digit solution) {
        this.solution = solution;
      }

      @Override
      public Stream<Digit> candidates() {
        return Stream.of(solution);
      }
    }

    private static final class EmptyCell extends Cell {
      private Set<Digit> candidates = EnumSet.allOf(Digit.class);

      @Override
      public Stream<Digit> candidates() {
        return Stream.ofAll(candidates);
      }
    }

    private static final class CompoundCell extends Cell {
      private final Cell[] components;

      public CompoundCell(Digit[] givenSolutions) {
        this.components = new Cell[givenSolutions.length];

        for (int i = 0; i < givenSolutions.length; i++) {
          this.components[i] = givenSolutions[i] == null
              ? empty()
              : new SolvedCell(givenSolutions[i]);
        }
      }

      @Override
      public Stream<Digit> candidates() {
        return Stream.of(components).flatMap(Cell::candidates).distinct();
      }

      @Override
      public Stream<Cell> members() {
        return Stream.of(components);
      }
    }

  }
}
