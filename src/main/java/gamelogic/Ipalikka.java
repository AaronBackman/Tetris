package gamelogic;

public class Ipalikka extends Palikka {
    protected Ipalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.TURKOOSI);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.kaantoAlue = new Ruutu[][] {{O,I,O,O}, {O,I,O,O}, {O,I,O,O}, {O,I,O,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
