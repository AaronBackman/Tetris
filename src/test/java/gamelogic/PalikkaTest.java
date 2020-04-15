package gamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuudukkoTesti {
    Ruudukko ruudukko;
    Palikka palikka;

    @BeforeEach
    public void init() {
        this.ruudukko = new Ruudukko();

        PalikkaTehdas pl = new PalikkaTehdas();
        this.palikka = pl.teeSatunnainenPalikka(ruudukko);
    }

    @Test
    //tarkistaa vain tapauksen jossa paivityksen pitaisi onnistua, toinen metodi tarkistaa onko se mahdollista
    public void paivitaRuudukkoTesti() {
        Ruutu[][] kaantoAlue = palikka.annaKaantoAlue();

        int[] sijainti = new int[]{10,10};
        ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
        for(int i=0; i<kaantoAlue.length; i++) {
            for(int j=0; j<kaantoAlue.length; j++) {
                //tarkistaa ovatko palikan ruudut ja paivitettava kohta molemmat taynna
                assertEquals(kaantoAlue[j][i], ruudukko.annaRuudut()[j+sijainti[0]][i+sijainti[1]]);
            }
        }

        sijainti = new int[]{7,7};
        ruudukko.annaRuudut()[kaantoAlue.length - 1 + sijainti[0]][kaantoAlue.length - 1 + sijainti[1]].asetaTaynna(true);
        ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
        for(int i=0; i<kaantoAlue.length; i++) {
            for(int j=0; j<kaantoAlue.length; j++) {
                //tarkistaa ovatko palikan ruudut ja paivitettava kohta molemmat taynna
                if(kaantoAlue[j][i].onkoTaynna()) {
                    assertEquals(kaantoAlue[j][i], ruudukko.annaRuudut()[j+sijainti[0]][i+sijainti[1]]);
                }
            }
        }
    }
}