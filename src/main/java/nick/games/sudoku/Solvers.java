package nick.games.sudoku;

import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import nick.games.sudoku.api.GameVariant;
import nick.games.sudoku.api.Solver;
import nick.games.sudoku.api.Number;


public enum Solvers implements Solver {
  HashingSolver {
    public <T extends GameVariant> Board<T> solve(Board<T> board) {
      board.cells().forEach(cell ->
          cell.solution().forEach(number -> {
            cell.groups().forEach(group -> group.filterNot(Cell::solved).forEach(other -> other.exclude(number)));
          }));
      return board;
    }
  },
  CompletingSolver {
    public <T extends GameVariant> Board<T> solve(Board<T> board) {
      board.groups().forEach(groupType ->
          groupType.forEach(
              group ->
                  Stream.of(Number.values()).filterNot(number ->
                      group.flatMap(Cell::solution).contains(number))
                      .forEach(number ->
                          group.map(cell -> new Tuple2<>(cell, cell.candidates())).filter(cellWithCandidates -> cellWithCandidates._2.contains(number)).transform(candidatesForNumber ->
                              {
                                if (candidatesForNumber.size() == 1) {
                                  return candidatesForNumber.map(Tuple2::_1);
                                } else {
                                  return Stream.<Cell>empty();
                                }
                              }
                          ).forEach(cell -> cell.solve(number))
                      )
          )
      );
      return board;
    }
  }
}
