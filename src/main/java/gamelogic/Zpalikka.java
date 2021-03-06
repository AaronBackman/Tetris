package gamelogic;

public class Zpalikka extends Block {
    /**
     * initializes block shape and beginning location
     * @param grid which is associated with this block
     */
    protected Zpalikka(Grid grid) {
        this.grid = grid;
        SquareFactory squareFactory = new SquareFactory();
        Square I = squareFactory.createFallingSquare(Color.RED);
        Square O = squareFactory.createEmptySquare();
        this.blockShape = new Square[][] {{I,O,O}, {I,I,O}, {O,I,O}};
        this.location = new int[2];
        location[0] = 7;
        location[1] = 0;
    }
}
