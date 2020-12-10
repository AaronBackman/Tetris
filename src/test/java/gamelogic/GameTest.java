package gamelogic;

import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * testaa Peli luokkaa satunnaisilla syotteilla
 */
class GameTest {

    /**
     * testaa ettei kayttaja pysty aiheuttamaan Exceptionia kelvollisilla syotteilla
     */
    @Test
    public void testaaSatunnaisiaSyotteita() {
        Game game = new Game();

        Random r = new Random();
        int n = 0;
        while(n < 1000000) {
            if(game.isGameOn() == false) {
                game = new Game();
            }

            //ei nolla jotta voi tehda siirtoja heti ennen ensimmaista putoamista
            if(n % 50 == 10) {
                game.nextFrame();
            }

            int syote = r.nextInt(6);

            switch(syote) {
                case 0:
                    game.takeInput("vasen");
                    break;
                case 1:
                    game.takeInput("oikea");
                    break;
                case 2:
                    game.takeInput("alas");
                    break;
                case 3:
                    game.takeInput("vastaPaivaan");
                    break;
                case 4:
                    game.takeInput("myotaPaivaan");
                    break;
                case 5:
                    game.takeInput("pudotaAlasAsti");
                    break;
            }
            n++;
        }
    }
}