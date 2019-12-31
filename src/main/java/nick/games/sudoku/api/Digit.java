package nick.games.sudoku.api;

public enum Digit {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    public final int intValue;

    Digit(int intValue) {
        this.intValue = intValue;
    }

    public static Digit fromInt(int value) {
        return value == 0 ? null :
        values()[value - 1];
    }
}
