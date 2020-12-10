package gamelogic;

public class Block {
    /**
     * includes all information about all squares in the grid (game)
     * ie. this is the game
     */
    protected Grid grid;

    /**
     * information about blocks squares colors, blocks shape and rotation
     */
    protected  Square[][] blockShape;

    /**
     * location of the top left square of the block on grid
     * first number is x coordinate, second y coordinate
     */
    protected int[] location;

    protected int[] fallingLocation = new int[2]; //same as above, but for the falling location
    protected boolean isFalling = true;

    /**
     * ghost block is gray, located where the falling block would now fall, and has the same shape
     */
    protected boolean showGhostBlock = true;

    public void dropByOneSquare() {
        location[1] += 1;
        if(grid.canUpdate(location, blockShape)) {
            grid.updateGrid(location, blockShape);
            updateFallingLocation();
        }
        else {
            location[1] -= 1;
            grid.setBlockFallen(location, blockShape);
            isFalling = false;
        }
    }

    public void putIntoGrid() {
        grid.updateGrid(location, blockShape);
        updateFallingLocation();
    }

    public void dropToBottom() {
        //pudottaa kunnes putoaa alas asti
        while(isFalling) {
            dropByOneSquare();
        }
    }

    public void moveLeft() {
        location[0] -= 1;
        if(grid.canUpdate(location, blockShape)) {
            grid.updateGrid(location, blockShape);
            updateFallingLocation();
        }
        else {
            location[0] +=1;
        }
    }

    public void moveRight() {
        location[0] += 1;
        if(grid.canUpdate(location, blockShape)) {
            grid.updateGrid(location, blockShape);
            updateFallingLocation();
        }
        else {
            location[0] -=1;
        }
    }

    public void turnCounterClockwiseInGrid() {
        Square[][] turnedSquares = turnCounterClockwise();

        if(grid.canUpdate(location, turnedSquares)) {
            blockShape = turnedSquares;
            grid.updateGrid(location, blockShape);
            updateFallingLocation();
        }
    }

    private Square[][] turnCounterClockwise() {
        Square[][] turnedSquares = new Square[blockShape.length][blockShape.length];
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape.length; j++) {
                turnedSquares[j][i] = blockShape[blockShape.length - 1 - i][j];
            }
        }
        return turnedSquares;
    }

    public void turnClockwiseInGrid() {
        Square[][] turnedSquares = turnClockwise();

        if(grid.canUpdate(location, turnedSquares)) {
            blockShape = turnedSquares;
            grid.updateGrid(location, blockShape);
            updateFallingLocation();
        }
    }

    private Square[][] turnClockwise() {
        Square[][] turnedSquares = new Square[blockShape.length][blockShape.length];
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape.length; j++) {
                turnedSquares[blockShape.length - 1 - i][j] = blockShape[j][i];
            }
        }
        return turnedSquares;
    }

    /**
     * removes old ghost block, recalculates the falling location and puts a new ghost block
     */
    private void updateFallingLocation() {
        grid.removeGhostBlock(fallingLocation, blockShape);
        if(showGhostBlock == false) {
            //in this case the method only removes the old ghost block (if even exists)
            return;
        }
        fallingLocation[0] = location[0];
        fallingLocation[1] = location[1];

        //calculates the falling location
        while(grid.canUpdate(fallingLocation, blockShape)) {
            fallingLocation[1] += 1;
        }
        fallingLocation[1] -= 1;

        if(fallingLocation[1] - location[1] > 0) {
            grid.setGhostBlock(fallingLocation, blockShape);
        }
    }

    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int[] getLocation() {
        return location;
    }
    public void setLocation(int[] location) {
        this.location = location;
    }

    public Square[][] getShape() {
        return blockShape;
    }
    public void setShape(Square[][] shape) {
        this.blockShape = shape;
    }

    public int[] getFallingLocation() {
        return fallingLocation;
    }

    public void setShowGhostBlock(boolean showGhostBlock) {
        this.showGhostBlock = showGhostBlock;
    }
    public boolean getShowGhostBlock() {
        return showGhostBlock;
    }
}
