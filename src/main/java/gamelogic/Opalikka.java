package gamelogic;

public class Opalikka extends Palikka {
    /**
     * alustaa palikan muodon ja aloitussijainnin
     * @param ruudukko joka liittyy tahan palikkaan
     */
    protected Opalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.KELTAINEN);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.palikanMuoto = new Ruutu[][] {{I,I}, {I,I}};
        this.sijainti = new int[2];
        sijainti[0] = 8;
        sijainti[1] = 0;
    }
}
