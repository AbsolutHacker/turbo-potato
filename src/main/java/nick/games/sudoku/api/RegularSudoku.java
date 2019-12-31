package nick.games.sudoku.api;

import io.vavr.collection.Stream;

public class RegularSudoku implements GameVariant<Digit, SquareGridGroup> {
  @Override
  public Stream<Digit> entryValues() {
    return Stream.of(Digit.values());
  }

  @Override
  public Class<SquareGridGroup> groupTypes() {
    return SquareGridGroup.class;
  }
}
