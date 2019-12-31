package nick.games.sudoku;

import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import nick.games.sudoku.api.CellGroup;
import nick.games.sudoku.api.RegularSudoku;
import nick.games.sudoku.api.Solver;
import nick.games.sudoku.api.Digit;


public enum Solvers implements Solver<RegularSudoku> {
  HashingSolver {
    @Override
    public Board<RegularSudoku> solve(Board<RegularSudoku> board) {
      board.cells().forEach(cell ->
          cell.solution().forEach(number -> {
            cell.groups().forEach(group -> group.filterNot(CellGroup::solved).forEach(other -> other.exclude(number)));
          }));
      return board;
    }
  },
  CompletingSolver {
    @Override
    public Board<RegularSudoku> solve(Board<RegularSudoku> board) {
      board.groups().forEach(groupType ->
          groupType.forEach(
              group ->
                  Stream.of(Digit.values()).filterNot(number ->
                      group.flatMap(CellGroup::solution).contains(number))
                      .forEach(number ->
                          group.map(cell -> new Tuple2<>(cell, cell.candidates()))
                              .filter(cellWithCandidates -> cellWithCandidates._2.contains(number))
                              .transform(candidatesForNumber ->
                              {
                                if (candidatesForNumber.size() == 1) {
                                  return candidatesForNumber.map(Tuple2::_1);
                                } else {
                                  return Stream.<CellGroup>empty();
                                }
                              }
                          ).forEach(cell -> cell.solve(number))
                      )
          )
      );
      return board;
    }
  },
}
