package gamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test moving the block with unit tests
 */
class BlockTest {
    Grid grid;
    Block block;

    /**
     * basically the constructor
     */
    @BeforeEach
    public void init() {
        this.grid = new Grid();

        BlockFactory pl = new BlockFactory();
        this.block = pl.makeRandomBlock(grid);
    }

    /**
     * checks the cases where updating is possible
     */
    @Test
    public void updateGridTest() {
        Square[][] turningArea = block.getShape();

        int[] location = new int[]{10,10};
        grid.updateGrid(location, turningArea);
        for(int i=0; i<turningArea.length; i++) {
            for(int j=0; j<turningArea.length; j++) {
                assertEquals(turningArea[j][i], grid.getSquares()[j+location[0]][i+location[1]]);
            }
        }

        location = new int[]{7,3};
        grid.getSquares()[turningArea.length + location[0]][turningArea.length + location[1]].setFull(true);
        grid.updateGrid(location, turningArea);
        for(int i=0; i<turningArea.length; i++) {
            for(int j=0; j<turningArea.length; j++) {
                if(turningArea[j][i].isFull()) {
                    assertEquals(turningArea[j][i], grid.getSquares()[j+location[0]][i+location[1]]);
                }
            }
        }
    }

    /**
     * checks if emptying full rows and calculating score works
     */
    @Test
    public void removeFullRowsTest() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });

        int score = grid.removeFullSquares();
        int expectedScore = 100; //2 removed rows -> 100 score

        assertFalse(grid.getSquares()[6][20].isFull());
        assertFalse(grid.getSquares()[6][21].isFull());

        assertFalse(grid.getSquares()[12][18].isFull());
        assertFalse(grid.getSquares()[12][19].isFull());

        assertFalse(grid.getSquares()[11][18].isFull());
        assertFalse(grid.getSquares()[11][19].isFull());

        assertTrue(grid.getSquares()[11][20].isFull());
        assertTrue(grid.getSquares()[12][20].isFull());

        //border squares must not be removed
        assertTrue(grid.getSquares()[2][20].isFull());
        assertTrue(grid.getSquares()[2][21].isFull());
        assertTrue(grid.getSquares()[6][22].isFull());
        assertTrue(grid.getSquares()[6][23].isFull());
        assertTrue(grid.getSquares()[6][24].isFull());
        assertTrue(grid.getSquares()[6][25].isFull());

        assertEquals(score, expectedScore);
    }

    /**
     * test if checking updatability works
     */
    @Test
    public void canUpdateTest() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });
        block = new IBlock(grid);
        assertFalse(grid.canUpdate(new int[]{10,17}, block.getShape()));//place already has blocks
        assertFalse(grid.canUpdate(new int[]{14,18}, block.getShape()));//on border area
        assertTrue(grid.canUpdate(new int[]{10,16}, block.getShape()));//place is empty
    }

    /**
     * tests putting the ghost block to the grid
     */
    @Test
    public void haamuPalikkaTesti() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });
        block = new Spalikka(grid);
        block.putIntoGrid();//also sets the ghost block

        assertFalse(grid.getSquares()[7][18].isFull());
        assertFalse(grid.getSquares()[7][18].isFalling());
        assertSame(grid.getSquares()[7][18].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[8][18].isFull());
        assertFalse(grid.getSquares()[8][18].isFalling());
        assertSame(grid.getSquares()[8][18].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[8][17].isFull());
        assertFalse(grid.getSquares()[8][17].isFalling());
        assertSame(grid.getSquares()[8][17].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[7][17].isFull());
        assertFalse(grid.getSquares()[7][17].isFalling());
        assertSame(grid.getSquares()[7][17].getColor(), Color.BLACK);

        assertTrue(grid.getSquares()[7][19].isFull());
        assertFalse(grid.getSquares()[7][19].isFalling());
        assertNotSame(grid.getSquares()[7][19].getColor(), Color.GRAY);
    }

    /**
     * tests if ghost block moves as the falling block moves
     */
    @Test
    public void ghostBlockMoveTest() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });
        block = new Spalikka(grid);
        block.putIntoGrid();//also sets the ghost block
        block.moveRight();

        //checks that old ghost block was removed
        assertFalse(grid.getSquares()[7][18].isFull());
        assertFalse(grid.getSquares()[7][18].isFalling());
        assertSame(grid.getSquares()[7][18].getColor(), Color.BLACK);

        assertFalse(grid.getSquares()[8][18].isFull());
        assertFalse(grid.getSquares()[8][18].isFalling());
        assertSame(grid.getSquares()[8][18].getColor(), Color.BLACK);

        assertFalse(grid.getSquares()[8][17].isFull());
        assertFalse(grid.getSquares()[8][17].isFalling());
        assertSame(grid.getSquares()[8][17].getColor(), Color.BLACK);

        //checks that the new ghost block was put correctly
        assertFalse(grid.getSquares()[8][18].isFull());
        assertFalse(grid.getSquares()[8][18].isFalling());
        assertSame(grid.getSquares()[8][19].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[9][18].isFull());
        assertFalse(grid.getSquares()[9][18].isFalling());
        assertSame(grid.getSquares()[9][19].getColor(), Color.GRAY);
    }

    @Test
    public void turnCounterClockWiseTest() {
        block = new TBlock(grid);
        block.setLocation(new int[]{7, 2});

        block.putIntoGrid();

        block.turnCounterClockwiseInGrid();

        assertTrue(grid.getSquares()[7][3].isFull());
        assertTrue(grid.getSquares()[8][4].isFull());

        assertFalse(grid.getSquares()[7][4].isFull());
        assertFalse(grid.getSquares()[6][2].isFull());
        assertFalse(grid.getSquares()[7][1].isFull());
    }

    @Test
    public void turnClockWiseTest() {
        block = new TBlock(grid);
        block.setLocation(new int[]{7, 2});

        block.putIntoGrid();

        block.turnClockwiseInGrid();

        assertTrue(grid.getSquares()[9][3].isFull());
        assertTrue(grid.getSquares()[8][4].isFull());

        assertFalse(grid.getSquares()[9][4].isFull());
        assertFalse(grid.getSquares()[10][2].isFull());
        assertFalse(grid.getSquares()[9][1].isFull());
    }

    @Test
    public void dropBlockByOneTest() {
        block = new OBlock(grid);
        block.dropByOneSquare();

        //old place
        assertFalse(grid.getSquares()[8][0].isFull());
        assertFalse(grid.getSquares()[8][0].isFalling());
        assertSame(grid.getSquares()[8][0].getColor(), Color.BLACK);

        //old place
        assertFalse(grid.getSquares()[9][0].isFull());
        assertFalse(grid.getSquares()[9][0].isFalling());
        assertSame(grid.getSquares()[9][0].getColor(), Color.BLACK);

        //new place
        assertFalse(grid.getSquares()[8][3].isFull());
        assertFalse(grid.getSquares()[8][3].isFalling());
        assertSame(grid.getSquares()[8][3].getColor(), Color.BLACK);

        //block should still be here
        assertTrue(grid.getSquares()[8][1].isFull());
        assertTrue(grid.getSquares()[8][1].isFalling());
        assertNotSame(grid.getSquares()[8][1].getColor(), Color.BLACK);

        //block should now be here
        assertTrue(grid.getSquares()[9][2].isFull());
        assertTrue(grid.getSquares()[9][2].isFalling());
        assertNotSame(grid.getSquares()[9][2].getColor(), Color.BLACK);
    }
}