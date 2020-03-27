package gamelogic;

public class Lpalikka extends Palikka {
    protected Lpalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.ORANSSI);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.kaantoAlue = new Ruutu[][] {{O,I,O}, {O,I,O}, {I,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
