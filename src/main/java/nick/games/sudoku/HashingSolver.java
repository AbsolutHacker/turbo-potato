package nick.games.sudoku;

import io.vavr.collection.Stream;

public class HashingSolver {
  private final Board board;

  public HashingSolver(Board board) {
    this.board = board;
  }

  public Board solve() {
    for (Cell cell : board.allCells()) {
      cell.solution().forEach(number -> {
        cell.getColumn().filterNot(Cell::solved).forEach(other -> other.exclude(number));
        cell.getRow().filterNot(Cell::solved).forEach(other -> other.exclude(number));
        cell.getQuadrant().filterNot(Cell::solved).forEach(other -> other.exclude(number));
      });
    }
    return board;
  }
}
