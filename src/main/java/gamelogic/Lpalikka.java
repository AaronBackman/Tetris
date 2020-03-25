package gamelogic;

public class Lpalikka extends Palikka {
    protected Lpalikka(Ruudukko ruudukko) {
        super(0b011, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{O,I,O}, {O,I,O}, {I,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 3;
        sijainti[0] = 0;
    }
}
