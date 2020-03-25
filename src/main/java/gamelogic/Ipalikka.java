package gamelogic;

public class Ipalikka extends Palikka {
    protected Ipalikka(Ruudukko ruudukko) {
        super(0b001, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{O,I,O,O}, {O,I,O,O}, {O,I,O,O}, {O,I,O,O}};
        this.sijainti = new int[2];
        sijainti[0] = 3;
        sijainti[0] = 0;
    }
}
