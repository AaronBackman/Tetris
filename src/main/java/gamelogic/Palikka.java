package gamelogic;

public class Palikka {
    protected Ruudukko ruudukko;
    protected  Ruutu[][] kaantoAlue;  //alue jossa palikkaa kaannetaan
    protected int[] sijainti; //kaantoAlueen indeksin[0][0] sijainti koko laudalla alkaen vasemmasta ylakulmasta
    protected boolean onkoPutoamassa = true;

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
        Ruutu[][] kaannetytRuudut = new Ruutu[kaantoAlue.length][kaantoAlue.length];
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
        Ruutu[][] kaannetytRuudut = new Ruutu[kaantoAlue.length][kaantoAlue.length];
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
