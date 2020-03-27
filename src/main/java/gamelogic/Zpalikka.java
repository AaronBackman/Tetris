package gamelogic;

public class Zpalikka extends Palikka {
    protected Zpalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.PUNAINEN);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.kaantoAlue = new Ruutu[][] {{I,O,O}, {I,I,O}, {O,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
