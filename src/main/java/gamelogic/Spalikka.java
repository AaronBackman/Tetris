package gamelogic;

public class Spalikka extends Palikka {
    protected Spalikka(Ruudukko ruudukko) {
        super(0b101, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{O,I,O}, {I,I,O}, {I,O,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
