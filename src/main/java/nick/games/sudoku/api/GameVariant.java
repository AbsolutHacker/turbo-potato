package nick.games.sudoku.api;

import io.vavr.collection.Seq;
import io.vavr.collection.Stream;

public interface GameVariant<EntryType extends Enum<EntryType>, GroupType extends Enum<GroupType>> {
  Stream<EntryType> entryValues();
  Class<GroupType> groupTypes();
}
