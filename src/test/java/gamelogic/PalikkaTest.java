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

    @Test
    public void poistaTaydetRuudutTesti() {
        RuutuTehdas rt = new RuutuTehdas();
        Ruutu I = rt.teeReunaRuutu();
        Ruutu O = rt.teeTyhjaRuutu();
        //uusi ruudukko, jossa on 2 taytta rivia, tarkoituksena poistaa taydet rivit ja laskea pisteet
        ruudukko.asetaRuudut(new Ruutu[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });

        int pisteTulos = ruudukko.poistaTaydetRuudut();
        int odotetutPisteet = 100; //kaksi poistettua rivia -> 100 pistetta

        assertFalse(ruudukko.annaRuudut()[6][20].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[6][21].onkoTaynna());

        assertFalse(ruudukko.annaRuudut()[12][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[12][19].onkoTaynna());

        assertFalse(ruudukko.annaRuudut()[11][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[11][19].onkoTaynna());

        assertTrue(ruudukko.annaRuudut()[11][20].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[12][20].onkoTaynna());

        //reunaruutuja ei saa poistaa
        assertTrue(ruudukko.annaRuudut()[2][20].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[2][21].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[6][22].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[6][23].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[6][24].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[6][25].onkoTaynna());

        assertEquals(pisteTulos, odotetutPisteet);
    }
}