package gamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//testaa palikan siirtamista ruudukolla
class PalikkaTest {
    Ruudukko ruudukko;
    Palikka palikka;

    @BeforeEach
    public void init() {
        this.ruudukko = new Ruudukko();

        PalikkaTehdas pl = new PalikkaTehdas();
        this.palikka = pl.teeSatunnainenPalikka(ruudukko);
    }

    //tarkistaa vain tapauksen jossa paivityksen pitaisi onnistua, toinen metodi tarkistaa onko se mahdollista
    @Test
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

    @Test
    public void voikoPaivittaaTesti() {
        RuutuTehdas rt = new RuutuTehdas();
        Ruutu I = rt.teeReunaRuutu();
        Ruutu O = rt.teeTyhjaRuutu();
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
        palikka = new Ipalikka(ruudukko);
        assertFalse(ruudukko.voikoPaivittaa(new int[]{10,17}, palikka.annaKaantoAlue()));//kohdassa on jo palikoita
        assertFalse(ruudukko.voikoPaivittaa(new int[]{14,18}, palikka.annaKaantoAlue()));//reuna-alueella
        assertTrue(ruudukko.voikoPaivittaa(new int[]{10,16}, palikka.annaKaantoAlue()));//kohta on tyhja
    }

    @Test
    public void haamuPalikkaTesti() {
        RuutuTehdas rt = new RuutuTehdas();
        Ruutu I = rt.teeReunaRuutu();
        Ruutu O = rt.teeTyhjaRuutu();
        ruudukko.asetaRuudut(new Ruutu[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
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
        palikka = new Spalikka(ruudukko);
        palikka.asetaRuudukolle();//asettaa myos haamupalikan

        assertFalse(ruudukko.annaRuudut()[7][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[7][18].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[7][18].annaVari(), Vari.HARMAA);

        assertFalse(ruudukko.annaRuudut()[8][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][18].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][18].annaVari(), Vari.HARMAA);

        assertFalse(ruudukko.annaRuudut()[8][17].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][17].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][17].annaVari(), Vari.HARMAA);

        assertFalse(ruudukko.annaRuudut()[7][17].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[7][17].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[7][17].annaVari(), Vari.MUSTA);

        assertTrue(ruudukko.annaRuudut()[7][19].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[7][19].annaPutoaminen());
        assertNotSame(ruudukko.annaRuudut()[7][19].annaVari(), Vari.HARMAA);
    }

    @Test
    public void haamuPalikkaLiikeTesti() {
        RuutuTehdas rt = new RuutuTehdas();
        Ruutu I = rt.teeReunaRuutu();
        Ruutu O = rt.teeTyhjaRuutu();
        ruudukko.asetaRuudut(new Ruutu[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
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
        palikka = new Spalikka(ruudukko);
        palikka.asetaRuudukolle();//asettaa myos haamupalikan
        palikka.liikuOikealle();

        //varmistavat, etta vanhasta kohdasta poistettiin haamupalikan osa
        assertFalse(ruudukko.annaRuudut()[7][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[7][18].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[7][18].annaVari(), Vari.MUSTA);

        assertFalse(ruudukko.annaRuudut()[8][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][18].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][18].annaVari(), Vari.MUSTA);

        assertFalse(ruudukko.annaRuudut()[8][17].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][17].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][17].annaVari(), Vari.MUSTA);

        //varmistaa etta uuteen kohtaan tulee haamupalikka
        assertFalse(ruudukko.annaRuudut()[8][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][18].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][19].annaVari(), Vari.HARMAA);

        assertFalse(ruudukko.annaRuudut()[9][18].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[9][18].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[9][19].annaVari(), Vari.HARMAA);
    }

    @Test
    public void kaannyVastapaivaanTesti() {
        palikka = new Tpalikka(ruudukko);
        palikka.asetaSijainti(new int[]{7, 2});

        palikka.asetaRuudukolle();

        palikka.kaannaVastapaivaanRuudukossa();

        assertTrue(ruudukko.annaRuudut()[7][3].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[8][4].onkoTaynna());

        assertFalse(ruudukko.annaRuudut()[7][4].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[6][2].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[7][1].onkoTaynna());
    }

    @Test
    public void kaannyMyotapaivaanTesti() {
        palikka = new Tpalikka(ruudukko);
        palikka.asetaSijainti(new int[]{7, 2});

        palikka.asetaRuudukolle();

        palikka.kaannaMyotapaivaanRuudukossa();

        assertTrue(ruudukko.annaRuudut()[9][3].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[8][4].onkoTaynna());

        assertFalse(ruudukko.annaRuudut()[9][4].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[10][2].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[9][1].onkoTaynna());
    }

    @Test
    public void pudotaYksiRuutuTesti() {
        palikka = new Opalikka(ruudukko);
        palikka.pudotaYksiRuutu();

        //vanha palikan kohta
        assertFalse(ruudukko.annaRuudut()[8][0].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][0].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][0].annaVari(), Vari.MUSTA);

        //vanha palikan kohta
        assertFalse(ruudukko.annaRuudut()[9][0].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[9][0].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[9][0].annaVari(), Vari.MUSTA);

        //palikan uuden kohdan alla
        assertFalse(ruudukko.annaRuudut()[8][3].onkoTaynna());
        assertFalse(ruudukko.annaRuudut()[8][3].annaPutoaminen());
        assertSame(ruudukko.annaRuudut()[8][3].annaVari(), Vari.MUSTA);

        //palikan pitaisi viela olla tassa kohdassa
        assertTrue(ruudukko.annaRuudut()[8][1].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[8][1].annaPutoaminen());
        assertNotSame(ruudukko.annaRuudut()[8][1].annaVari(), Vari.MUSTA);

        //palikan pitaisi nyt olla tassa kohdassa
        assertTrue(ruudukko.annaRuudut()[9][2].onkoTaynna());
        assertTrue(ruudukko.annaRuudut()[9][2].annaPutoaminen());
        assertNotSame(ruudukko.annaRuudut()[9][2].annaVari(), Vari.MUSTA);
    }
}