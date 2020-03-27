package gamelogic;

public class Opalikka extends Palikka {
    protected Opalikka(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        Ruutu I = ruutuTehdas.teePutoavaRuutu(Vari.KELTAINEN);
        Ruutu O = ruutuTehdas.teeTyhjaRuutu();
        this.kaantoAlue = new Ruutu[][] {{I,I}, {I,I}};
        this.sijainti = new int[2];
        sijainti[0] = 8;
        sijainti[1] = 1;
    }

    public void kaanna() {
        //ei tee mitaan, koska on nelio
    }
}
