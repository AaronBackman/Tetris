package gamelogic;

public class OBlock extends Block {
    /**
     * initializes block shape and beginning location
     * @param grid which is associated with this block
     */
    protected OBlock(Grid grid) {
        this.grid = grid;
        SquareFactory squareFactory = new SquareFactory();
        Square I = squareFactory.createFallingSquare(Color.YELLOW);
        Square O = squareFactory.createEmptySquare();
        this.blockShape = new Square[][] {{I,I}, {I,I}};
        this.location = new int[2];
        location[0] = 8;
        location[1] = 0;
    }
}
