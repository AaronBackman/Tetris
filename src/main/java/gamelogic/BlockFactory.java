package gamelogic;

import java.util.Random;

/**
 * used to create new blocks
 */
public class BlockFactory {

    /**
     * makes random blocks
     * @param grid where the block will be put
     * @return the created block
     */
    public Block makeRandomBlock(Grid grid) {
        Random r = new Random();
        int n = r.nextInt(7);
        if(n == 0) {
            return new IBlock(grid);
        }
        else if(n == 1) {
            return new JBlock(grid);
        }
        else if(n == 2) {
            return new LBlock(grid);
        }
        else if(n == 3) {
            return new OBlock(grid);
        }
        else if(n == 4) {
            return new Spalikka(grid);
        }
        else if(n == 5) {
            return new TBlock(grid);
        }
        else if(n == 6) {
            return new Zpalikka(grid);
        }
        else {
            //should never happen, compiler requires a return statement
            return null;
        }
    }
}
