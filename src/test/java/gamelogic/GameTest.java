package gamelogic;

import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Tests that game class wont break with random (and fast) user input
 */
class GameTest {

    /**
     * tests that user cant break the program with allowed input
     */
    @Test
    public void testRandomInput() {
        Game game = new Game();

        Random r = new Random();
        int n = 0;
        while(n < 1000000) {
            if(game.isGameOn() == false) {
                game = new Game();
            }

            //not 0 so that moves can be made before the block falls first
            if(n % 50 == 10) {
                game.nextFrame();
            }

            int input = r.nextInt(6);

            switch(input) {
                case 0:
                    game.takeInput("left");
                    break;
                case 1:
                    game.takeInput("right");
                    break;
                case 2:
                    game.takeInput("bottom");
                    break;
                case 3:
                    game.takeInput("counterClockwise");
                    break;
                case 4:
                    game.takeInput("clockwise");
                    break;
                case 5:
                    game.takeInput("dropToBottom");
                    break;
            }
            n++;
        }
    }
}