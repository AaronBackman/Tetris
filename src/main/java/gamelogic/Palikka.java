package gamelogic;

public class Palikka {
    //vari 3 bittisena binaarilukuna
    protected final int vari;
    protected Ruudukko ruudukko;
    protected  int[][] kaantoAlue;  //alue jossa palikkaa kaannetaan
    protected int[] sijainti; //kaantoAlueen indeksin[0][0] sijainti koko laudalla alkaen vasemmasta ylakulmasta
    protected final int taytettyRuutu; //taytetty (ja putoava koska on palikka)
    protected final int tyhjaRuutu;
    protected boolean onkoPutoamassa;

    protected Palikka(int vari, Ruudukko ruudukko) {
        this.vari = vari;
        this.taytettyRuutu = 0b11000 | vari;
        this.tyhjaRuutu = 0b01000 | vari;
        this.ruudukko = ruudukko;
        this.onkoPutoamassa = true;
    }
    public void pudotaYksiRuutu() {
        sijainti[1] += 1;
        if(ruudukko.voikoPaivittaa(sijainti, kaantoAlue)) {
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
        }
        else {
            sijainti[1] -= 1;
            ruudukko.asetaPalikkaPudonneeksi(sijainti, kaantoAlue);
            onkoPutoamassa = false;
        }
    }

    public void liikuVasemmalle() {
        sijainti[0] -= 1;
        if(ruudukko.voikoPaivittaa(sijainti, kaantoAlue)) {
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
        }
        else {
            sijainti[0] +=1;
        }
    }

    public void liikuOikealle() {
        sijainti[0] += 1;
        if(ruudukko.voikoPaivittaa(sijainti, kaantoAlue)) {
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
        }
        else {
            sijainti[0] -=1;
        }
    }

    public void kaannaMyotapaivaan() {
        int[][] kaannetytRuudut = new int[kaantoAlue.length][kaantoAlue.length];
        for (int i = 0; i < kaantoAlue.length; i++) {
            for (int j = 0; j < kaantoAlue.length; j++) {
                kaannetytRuudut[i][j] = kaantoAlue[kaantoAlue.length - 1 - j][i];
            }
        }
        if(ruudukko.voikoPaivittaa(sijainti, kaannetytRuudut)) {
            kaantoAlue = kaannetytRuudut;
        }
    }

    public void kaannaVastapaivaan() {
        int[][] kaannetytRuudut = new int[kaantoAlue.length][kaantoAlue.length];
        for (int i = 0; i < kaantoAlue.length; i++) {
            for (int j = 0; j < kaantoAlue.length; j++) {
                kaannetytRuudut[kaantoAlue.length - 1 - j][i] = kaantoAlue[i][j];
            }
        }
        if(ruudukko.voikoPaivittaa(sijainti, kaannetytRuudut)) {
            kaantoAlue = kaannetytRuudut;
        }
    }

    public Ruudukko annaRuudukko() {
        return ruudukko;
    }

    public void asetaRuudukko(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
    }
}
