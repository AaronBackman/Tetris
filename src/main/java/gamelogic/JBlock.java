package gamelogic;

public class JBlock extends Block {
    /**
     * initializes block shape and beginning location
     * @param grid which is associated with this block
     */
    protected JBlock(Grid grid) {
        this.grid = grid;
        SquareFactory squareFactory = new SquareFactory();
        Square I = squareFactory.createFallingSquare(Color.BLUE);
        Square O = squareFactory.createEmptySquare();
        this.blockShape = new Square[][] {{I,I,O}, {O,I,O}, {O,I,O}};
        this.location = new int[2];
        location[0] = 7;
        location[1] = 0;
    }
}
