package gamelogic;

import java.util.Timer;

public class Peli {
    Ruudukko pelinTila;
    Palikka putoavaPalikka;

    public Peli() {
        this.pelinTila = new Ruudukko();
    }

    public void aloitaPeli() {
        Timer timer = new Timer();
        while(true) {
            this.putoavaPalikka = new palikkaTehdas().teeSatunnainenPalikka(pelinTila);
        }
    }
}
