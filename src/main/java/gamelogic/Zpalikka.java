package gamelogic;

public class Zpalikka extends Palikka {
    protected Zpalikka(Ruudukko ruudukko) {
        super(0b111, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{I,O,O}, {I,I,O}, {O,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
