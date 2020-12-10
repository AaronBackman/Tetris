package gamelogic;

public class Spalikka extends Block {
    /**
     * initializes block shape and beginning location
     * @param grid which is associated with this block
     */
    protected Spalikka(Grid grid) {
        this.grid = grid;
        SquareFactory squareFactory = new SquareFactory();
        Square I = squareFactory.createFallingSquare(Color.GREEN);
        Square O = squareFactory.createEmptySquare();
        this.blockShape = new Square[][] {{O,I,O}, {I,I,O}, {I,O,O}};
        this.location = new int[2];
        location[0] = 7;
        location[1] = 0;
    }
}
