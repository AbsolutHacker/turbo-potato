package nick.games.sudoku.api;

public enum Number {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    public final int intValue;

    Number(int intValue) {
        this.intValue = intValue;
    }

    public static Number fromInt(int value) {
        return value == 0 ? null :
        values()[value - 1];
    }
}
