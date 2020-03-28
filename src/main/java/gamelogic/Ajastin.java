package gamelogic;

import java.util.TimerTask;

public class Ajastin extends TimerTask {
    Peli peli;

    public Ajastin(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void run() {
        peli.seuraavaFrame();
        peli.otaInputti();
        peli.annaRuudukko().tulostaRuudukko();

        if(peli.onkoPeliKaynnissa() == false) {
            this.cancel();
        }
    }
}
