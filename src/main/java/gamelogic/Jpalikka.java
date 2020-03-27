package gamelogic;

public class Jpalikka extends Palikka {
    protected Jpalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.SININEN);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.kaantoAlue = new Ruutu[][] {{I,I,O}, {O,I,O}, {O,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
