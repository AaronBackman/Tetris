package gamelogic;

public class Opalikka extends Palikka {
    protected Opalikka(Ruudukko ruudukko) {
        super(0b100, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{I,I}, {I,I}};
        this.sijainti = new int[2];
        sijainti[0] = 8;
        sijainti[1] = 1;
    }

    public void kaanna() {
        //ei tee mitaan, koska on nelio
    }
}
