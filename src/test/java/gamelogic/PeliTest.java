package gamelogic;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PeliTest {

    //testaa ettei tule Exceptionia kelvollisilla syotteilla
    @Test
    public void testaaSatunnaisiaSyotteita() {
        Peli peli = new Peli();

        Random r = new Random();
        int n = 0;
        while(n < 1000000) {
            if(peli.onkoPeliKaynnissa() == false) {
                peli = new Peli();
            }

            //ei nolla jotta voi tehda siirtoja heti ennen ensimmaista putoamista
            if(n % 50 == 10) {
                peli.seuraavaFrame();
            }

            int syote = r.nextInt(6);

            switch(syote) {
                case 0:
                    peli.otaInputti("vasen");
                    break;
                case 1:
                    peli.otaInputti("oikea");
                    break;
                case 2:
                    peli.otaInputti("alas");
                    break;
                case 3:
                    peli.otaInputti("vastaPaivaan");
                    break;
                case 4:
                    peli.otaInputti("myotaPaivaan");
                    break;
                case 5:
                    peli.otaInputti("pudotaAlasAsti");
                    break;
            }
            n++;
        }
    }
}