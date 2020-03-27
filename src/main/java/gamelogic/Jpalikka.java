package gamelogic;

public class Jpalikka extends Palikka {
    protected Jpalikka(Ruudukko ruudukko) {
        super(0b010, ruudukko);
        int I = taytettyRuutu;
        int O = tyhjaRuutu;
        this.kaantoAlue = new int[][] {{I,I,O}, {O,I,O}, {O,I,O}};
        this.sijainti = new int[2];
        sijainti[0] = 7;
        sijainti[1] = 0;
    }
}
