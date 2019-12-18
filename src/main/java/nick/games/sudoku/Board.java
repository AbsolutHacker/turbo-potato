package nick.games.sudoku;

import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.Array;
import io.vavr.collection.Stream;
import io.vavr.control.Option;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Board {

  public static final int SIZE_QUADRANT = 3;
  public static final int NUMBER_QUADRANTS = 3;
  public static final int SIZE = SIZE_QUADRANT * NUMBER_QUADRANTS;

  private final Cell[][] cells = new Cell[SIZE][SIZE];
  private final Set<Cell>[] rows = new Set[SIZE];
  private final Set<Cell>[] columns = new Set[SIZE];
  private final Set<Cell>[] quadrants = new Set[SIZE];

  public Board(Number[][] numbers) {

    for (int i = 0; i < SIZE; i++) {
      this.rows[i] = new HashSet<>(SIZE);
      this.columns[i] = new HashSet<>(SIZE);
      this.quadrants[i] = new HashSet<>(SIZE);
    }

    for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
      for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {

        final int quadrantIndex = ((rowIndex / 3) * 3) + columnIndex / 3;

        final Cell cell = new Cell(numbers[rowIndex][columnIndex], rows[rowIndex], columns[columnIndex], quadrants[quadrantIndex]);

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

  public Cell cell(int row, int column) {
    return cells[row][column];
  }

  public Stream<Cell> row(int rowIndex) {
    return Stream.of(cells[rowIndex]);
  }

  public Stream<Cell> column(int colIndex) {
    return Stream.ofAll(columns[colIndex]);
  }

  public Stream<Cell> quadrant(int quadrantIndex) {
    return Stream.ofAll(quadrants[quadrantIndex]);
  }

  public Stream<Cell> allCells() {
    return Stream.of(cells).flatMap(Stream::of);
  }

  public boolean solved() {
    return allCells().map(Cell::solved).reduce(Boolean::logicalAnd);
  }

}
