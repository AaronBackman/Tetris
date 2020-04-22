package gamelogic;

public class Spalikka extends Palikka {
    /**
     * alustaa palikan muodon ja aloitussijainnin
     * @param ruudukko joka liittyy tahan palikkaan
     */
    protected Spalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.VIHREA);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.palikanMuoto = new Ruutu[][] {{O,I,O}, {I,I,O}, {I,O,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
