package nick.games.sudoku.api;

import io.vavr.collection.Stream;

public class RegularSudoku implements GameVariant<Number, SquareGridGroup> {
  @Override
  public Stream<Number> entryValues() {
    return Stream.of(Number.values());
  }

  @Override
  public Class<SquareGridGroup> groupTypes() {
    return SquareGridGroup.class;
  }
}
