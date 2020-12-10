package gamelogic;

/**
 * different methods that create frequently used squares
 */
public class SquareFactory {
    /**
     * @param color square color
     * @return falling sqyare that is full and has a color
     */
    public Square createFallingSquare(Color color) {
        Square square = new Square();
        square.setColor(color);
        square.setFalling(true);
        square.setFull(true);
        return square;
    }

    /**
     * @return empty square that is not full, is black and not falling
     */
    public Square createEmptySquare() {
        Square square = new Square();
        square.setColor(Color.BLACK);
        square.setFalling(false);
        square.setFull(false);
        return square;
    }

    /**
     * @return black and full square, is used as the game grid borders
     */
    public Square createBorderSquare() {
        Square square = new Square();
        square.setColor(Color.BLACK);
        square.setFalling(false);
        square.setFull(true);
        return square;
    }

    /**
     * @return ghost block square, not full and not falling
     * only thing different from empty square is the color
     */
    public Square createFallingLocationSquare() {
        Square square = new Square();
        square.setColor(Color.GRAY);
        square.setFalling(false);
        square.setFull(false);
        return square;
    }
}
