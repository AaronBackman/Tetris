package gamelogic;

public class Tpalikka extends Palikka {
    protected Tpalikka(Ruudukko ruudukko) {
        super(0b110, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{O,I,O}, {I,I,O}, {O,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 3;
        sijainti[0] = 0;
    }
}
