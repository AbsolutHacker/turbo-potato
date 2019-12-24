package nick.games.sudoku;

import io.vavr.collection.Stream;
import nick.games.sudoku.api.GameVariant;
import nick.games.sudoku.api.Number;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board<Variant extends GameVariant> {

  public final int SIZE;
  public final Variant variant;

  private final Cell[][] cells;

  private final Set<Cell>[] rows;
  private final Set<Cell>[] columns;
  private final Set<Cell>[] quadrants;

  public Board(Variant variant, Number[][] numbers) {

    this.variant = variant;
    this.SIZE = variant.entryValues().size();

    this.cells = new Cell[SIZE][SIZE];

    this.rows = new Set[SIZE];
    this.columns = new Set[SIZE];
    this.quadrants = new Set[SIZE];

    for (int i = 0; i < SIZE; i++) {
      this.rows[i] = new HashSet<>(SIZE);
      this.columns[i] = new HashSet<>(SIZE);
      this.quadrants[i] = new HashSet<>(SIZE);
    }

    for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
      if (numbers[rowIndex].length != SIZE) {
        throw new IllegalArgumentException("input array size does not match variant constraint!");
      }
      for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {

        final int quadrantIndex = ((rowIndex / 3) * 3) + columnIndex / 3;

        final Cell cell = new CellImpl(numbers[rowIndex][columnIndex], rows[rowIndex], columns[columnIndex], quadrants[quadrantIndex]);

        this.cells[rowIndex][columnIndex] = cell;
        this.rows[rowIndex].add(cell);
        this.columns[columnIndex].add(cell);
        this.quadrants[quadrantIndex].add(cell);
      }
    }

  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("\n");
    for (Cell[] row: cells    ) {
      sb.append(Arrays.toString(row));
      sb.append("\n");
    }
    return sb.toString();
  }

  public Stream<Stream<Cell>> rows() {
    return Stream.of(rows).map(Stream::ofAll);
  }

  public Stream<Stream<Cell>> columns() {
    return Stream.of(columns).map(Stream::ofAll);
  }

  public Stream<Stream<Cell>> quadrants() {
    return Stream.of(quadrants).map(Stream::ofAll);
  }

  public Stream<Stream<Stream<Cell>>> groups() {
    return Stream.of(rows(), columns(), quadrants());
  }

  public Stream<Cell> cells() {
    return Stream.of(cells).flatMap(Stream::of);
  }

  public boolean solved() {
    return cells().map(Cell::solved).reduce(Boolean::logicalAnd);
  }

}
