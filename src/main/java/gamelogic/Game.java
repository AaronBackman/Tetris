package gamelogic;

import java.util.LinkedList;

/**
 * includes the logical side of the tetris game
 */
public class Game {
    /**
     * includes data about the squares (grid) of the game
     */
    private final Grid gameState;

    /**
     * next 3 falling blocks that come after the current block
     */
    private LinkedList<Block> nextBlocks;

    /**
     * current falling block (just one)
     */
    private Block fallingBlock;
    private boolean gameIsOn;
    private int score;

    /**
     * sets the game an empty grid, random falling block and next 3 falling blocks
     */
    public Game() {
        this.gameState = new Grid();
        this.fallingBlock = new BlockFactory().makeRandomBlock(gameState);
        this.nextBlocks = new LinkedList<>();

        //stores next 3 falling blocks
        for(int i=0; i<3; i++) {
            nextBlocks.add(new BlockFactory().makeRandomBlock(gameState));
        }
        this.gameIsOn = true;
    }

    /**
     * takes input as a string and moves the block accordingly
     * with this method user input can be changed to easier form and put to this method
     * @param input input as a string
     */
    public void takeInput(String input) {

        switch (input) {
            case "left":
                fallingBlock.moveLeft();
                break;
            case "right":
                fallingBlock.moveRight();
                break;
            case "down":
                fallingBlock.dropByOneSquare();
                break;
            case "counterClockwise":
                fallingBlock.turnCounterClockwiseInGrid();
                break;
            case "clockwise":
                fallingBlock.turnClockwiseInGrid();
                break;
            case "dropToBottom":
                fallingBlock.dropToBottom();
                break;
        }
    }

    /**
     * moves the game forward by one step (drops the block by one)
     * and makes necessary checks
     */
    public void nextFrame() {
        fallingBlock.dropByOneSquare();
        if(! fallingBlock.isFalling) {

            //checks if the block fell outside the grid (==game over)
            if(getGrid().isBlockOutsideGrid(fallingBlock.getLocation(), fallingBlock.getShape())) {
                this.gameIsOn = false;
            }

            score += gameState.removeFullSquares();

            nextBlocks.add(new BlockFactory().makeRandomBlock(gameState));

            // keeps the same settings if the ghost block is shown or not as the previous block
            boolean naytaHaamuPalikka = fallingBlock.getShowGhostBlock();
            fallingBlock = nextBlocks.poll();
            fallingBlock.setShowGhostBlock(naytaHaamuPalikka);
            fallingBlock.putIntoGrid();
        }
    }


    public Grid getGrid() {
        return gameState;
    }

    public boolean isGameOn() {
        return gameIsOn;
    }

    public int getScore() {
        return score;
    }

    public LinkedList<Block> getNextBlocks() {
        return nextBlocks;
    }

    public Block getFallingBlock() {
        return fallingBlock;
    }

}