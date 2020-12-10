package gamelogic;

public class TBlock extends Block {
    /**
     * initializes block shape and beginning location
     * @param grid which is associated with this block
     */
    protected TBlock(Grid grid) {
        this.grid = grid;
        SquareFactory squareFactory = new SquareFactory();
        Square I = squareFactory.createFallingSquare(Color.PURPLE);
        Square O = squareFactory.createEmptySquare();
        this.blockShape = new Square[][] {{O,I,O}, {I,I,O}, {O,I,O}};
        this.location = new int[2];
        location[0] = 7;
        location[1] = 0;
    }
}
