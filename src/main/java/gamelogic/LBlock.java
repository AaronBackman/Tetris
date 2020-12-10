package gamelogic;

public class LBlock extends Block {
    /**
     * initializes block shape and beginning location
     * @param grid which is associated with this block
     */
    protected LBlock(Grid grid) {
        this.grid = grid;
        SquareFactory squareFactory = new SquareFactory();
        Square I = squareFactory.createFallingSquare(Color.ORANGE);
        Square O = squareFactory.createEmptySquare();
        this.blockShape = new Square[][] {{O,I,O}, {O,I,O}, {I,I,O}};
        this.location = new int[2];
        location[0] = 7;
        location[1] = 0;
    }
}
