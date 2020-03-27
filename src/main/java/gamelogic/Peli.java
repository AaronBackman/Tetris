package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

public class Peli {
    Ruudukko pelinTila;
    Palikka putoavaPalikka;

    public Peli() {
        this.pelinTila = new Ruudukko();
    }

    public void aloitaPeli() throws InterruptedException {
        long aikavali = 1;
        this.putoavaPalikka = new palikkaTehdas().teeSatunnainenPalikka(pelinTila);
        int count = 0;
        while(count < 10) {
            Thread.sleep(1000);
            putoavaPalikka.pudotaYksiRuutu();
            pelinTila.tulostaRuudukko();
            if(! putoavaPalikka.onkoPutoamassa) {
                putoavaPalikka = new palikkaTehdas().teeSatunnainenPalikka(pelinTila);
                pelinTila.tulostaRuudukko();
            }
            count++;
        }
    }
}