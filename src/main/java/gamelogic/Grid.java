package gamelogic;

public class Grid {

    private Square[][] squares;

    /**
     * depth of grid border from left, right and bottom, not seen by the player
     * included so that when block is moved it wont be outside index bounds
     */
    public final static int BORDER_WIDTH = 4;

    /**
     * depth of an area on top of grid, not seen by the player
     * new blocks are put into this area
     * game is lost if the block doesnt have space to exit this area (it stays outside the grid)
     */
    public final static int TOP_AREA = 2;

    /**
     * height of the area seen by the player
     */
    public final static int HEIGHT = 20;

    /**
     * width of the area seen by the player
     */
    public final static int WIDTH = 10;

    /**
     * initializes the squares in the grid, area seen by the player and the top area are empty
     * left, right and bottom borders are filled
     */
    public Grid() {
        //left, right and bottom have 4 extra squares that are used to check if the block tries to exits the grid
        this.squares = new Square[WIDTH + 2 * BORDER_WIDTH][HEIGHT + TOP_AREA + BORDER_WIDTH];
        SquareFactory squareFactory = new SquareFactory();
        for(int i = 0; i< squares[0].length; i++) {
            for(int j = 0; j< squares.length; j++) {
                if((i < squares[0].length - BORDER_WIDTH) && (j >= BORDER_WIDTH) && (j < squares.length - BORDER_WIDTH)) {
                    squares[j][i] = squareFactory.createEmptySquare();
                }
                else {
                    squares[j][i] = squareFactory.createBorderSquare();
                }
            }
        }
    }

    /**
     * puts the block into the grid according to its shape and location
     * @param location place where the upper left corner of block is put
     * @param blockShape information about the shape of the block
     * @throws IndexOutOfBoundsException if the block is located in border area or outside the whole grid
     */
    public void updateGrid(int[] location, Square[][] blockShape) {
        int x = location[0];
        int y = location[1];

        /* exception is also thrown in border area, because squares are checked from the location to right and bottom
        so some parts might go outside the entire grid */
        if(x >= squares.length - BORDER_WIDTH || x < 0) {
            throw new IndexOutOfBoundsException();
        } else if(y >= squares[0].length - BORDER_WIDTH || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        SquareFactory squareFactory = new SquareFactory();
        for(int i=-1; i<blockShape.length; i++) {
            for(int j=-1; j<blockShape.length+1; j++) {

                //checks if parts of the block were left outside the area of the block
                if(i == -1 | j == -1 | j == blockShape.length) {

                    //block is as high up as possible -> no squares over it
                    if(y == 0 && i == -1) {
                        continue;
                    }
                    if (squares[x + j][y + i].isFalling()) {
                        squares[x + j][y + i] = squareFactory.createEmptySquare();
                    }
                }

                //checks that destination square is empty or falling ie. part of the block
                else if(squares[x + j][y + i].isFull() == false || squares[x + j][y + i].isFalling()) {
                    squares[x + j][y + i] = blockShape[j][i];
                }
            }
        }
    }

    /**
     * checks if the block has space to go to the location
     * @param location location of the top left part of the block on the grid
     * @param blockShape shape of the block
     * @return true if block can be put to the location
     */
    public boolean canUpdate(int[] location, Square[][] blockShape) {
        int x = location[0];
        int y = location[1];
        for(int i=0; i<blockShape.length; i++) {
            for(int j=0; j<blockShape.length; j++) {
                //if both squares are full
                if(squares[x + j][y + i].isFull() && blockShape[j][i].isFull() &&
                        (!squares[x + j][y + i].isFalling())) {

                    return false;
                }
            }
        }
        return true;
    }

    /**
     * empties full rows and drops all squares above it accordingly
     * @see Grid#dropToRow
     * @see Grid#removeRow
     * @return score according to the amount of (at the same time) removed rows
     */
    public int removeFullSquares() {
        int removedRows = 0;

        for(int i = 0; i< HEIGHT; i++) {
            int fullSquaresInRow = 0;

            for(int j = 0; j< WIDTH; j++) {
                if(squares[j + BORDER_WIDTH][i + TOP_AREA].isFull()) {
                    fullSquaresInRow += 1;
                }
            }
            if(fullSquaresInRow == WIDTH) {
                dropToRow(i);
                removedRows += 1;
            }
        }

        int score = 0;
        switch (removedRows) {
            case 1:
                score = 40;
                break;
            case 2:
                score = 100;
                break;
            case 3:
                score = 300;
                break;
            case 4:
                score = 1200;
                break;
            default:
                break;
        }
        return  score;
    }

    /**
     * empties 1 row and drops all squares above it by 1
     * @param row up coordinate of the row to be removed
     */
    private void dropToRow(int row) {
        removeRow(row);

        SquareFactory rt = new SquareFactory();

        for(int i = row+ TOP_AREA -1; i>=0; i--) {
            for(int j = 0; j< WIDTH; j++) {
                if(squares[j + BORDER_WIDTH][i].isFull()) {
                    //pudottaa palikan yhden ruudun alaspain, joka on tyhja koska rivi poistetaan/pudotetaan ensin
                    squares[j + BORDER_WIDTH][i + 1] = squares[j + BORDER_WIDTH][i];

                    //poistaa palikan, jotta siihen voidaan pudottaa palikka myohemmin
                    squares[j + BORDER_WIDTH][i] = rt.createEmptySquare();
                }
            }
        }
    }

    /**
     * @param row empties the row
     */
    private void removeRow(int row) {
        for(int i = 0; i< WIDTH; i++) {
            SquareFactory rt = new SquareFactory();
            squares[i + BORDER_WIDTH][row + TOP_AREA] = rt.createEmptySquare();
        }
    }

    /**
     * sets so that the squares in the area are not falling anymore
     * @param location top left square of the block
     * @param blockShape block shape
     */
    public void setBlockFallen(int[] location, Square[][] blockShape) {
        int x = location[0];
        int y = location[1];
        for(int i=0; i<blockShape.length; i++) {
            for(int j=0; j<blockShape.length; j++) {
                if(blockShape[j][i].isFalling()) {
                    squares[x + j][y + i].setFalling(false);
                    blockShape[j][i].setFalling(false);
                }
            }
        }
    }

    /**
     * checks if any full part of the block is outside the grid
     * @param location top left square of the block
     * @param blockShape block shape
     * @return true if block is (partially) outside the grid
     */
    public boolean isBlockOutsideGrid(int[] location, Square[][] blockShape) {
        int x = location[0];
        int y = location[1];

        for(int i=0; i<blockShape.length; i++) {
            for(int j=0; j<blockShape.length; j++) {
                if(y + j >= TOP_AREA) {
                    return false;
                }
                else if(squares[x + j][y + i].isFull()) {
                    //top of the grid has the block according to the upper condition
                    return true;
                }
            }
        }
        //will never happen, but is requires by the compiler
        return false;
    }

    /**
     * sets the ghost block to the grid that shows where the current falling location of the block
     * @param fallinLocation place of the falling location on the grid (top left square)
     * @param blockShape ghost block shape
     */
    public void setGhostBlock(int[] fallinLocation, Square[][] blockShape) {
        SquareFactory rt = new SquareFactory();
        int x = fallinLocation[0];
        int y = fallinLocation[1];
        if(y < 0 || x < 0) {
            return;
        }

        for(int i=0; i<blockShape.length; i++) {
            for(int j=0; j<blockShape.length; j++) {
                if(blockShape[j][i].isFull() && squares[x + j][y + i].isFalling() == false) {
                    //paints squares gray on the grid according to the block shape (this will be the ghost block)
                    squares[x + j][y + i] = rt.createFallingLocationSquare();
                }
            }
        }
    }

    /**
     * removes (old) ghost block from the grid
     * @param fallingLocation (old) ghost block location
     * @param blockShape ghost block shape
     */
    public void removeGhostBlock(int[] fallingLocation, Square[][] blockShape) {
        int x = fallingLocation[0];
        int y = fallingLocation[1];
        if(x == 0 && y == 0) {
            //in this case the block was just put to the grid and no falling location has been calculated
            return;
        }
        if(y < 0) {
            return;
        }

        //falling location squares are just empty squares with a differen color (gray)
        // so just changing color back is enough to remove them
        for(int i=0; i<blockShape.length; i++) {
            for(int j=0; j<blockShape.length; j++) {
                if(squares[x + j][y + i].getColor() == Color.GRAY) {
                    squares[x + j][y + i].setColor(Color.BLACK);
                }
            }
        }
    }

    public Square[][] getSquares() {
        return squares;
    }
    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
}
