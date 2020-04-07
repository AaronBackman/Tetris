package gamelogic;

public class Palikka {
    protected Ruudukko ruudukko;
    protected  Ruutu[][] kaantoAlue;  //alue jossa palikkaa kaannetaan
    protected int[] sijainti; //kaantoAlueen indeksin[0][0] sijainti koko laudalla alkaen vasemmasta ylakulmasta,x,y
    protected int[] putoamisKohdanSijainti = new int[2]; //sama kuin ylla
    protected boolean onkoPutoamassa = true;

    public void pudotaYksiRuutu() {
        sijainti[1] += 1;
        if(ruudukko.voikoPaivittaa(sijainti, kaantoAlue)) {
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
            paivitaPutoamisKohta();
        }
        else {
            sijainti[1] -= 1;
            ruudukko.asetaPalikkaPudonneeksi(sijainti, kaantoAlue);
            onkoPutoamassa = false;
        }
    }

    //asettaa (uuden) palikan ruudukolle
    public void asetaRuudukolle() {
        ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
        paivitaPutoamisKohta();
    }

    public void pudotaNiinPaljonKuinMahdollista() {
        //pudottaa kunnes putoaa alas asti
        while(onkoPutoamassa) {
            pudotaYksiRuutu();
        }
    }

    public void liikuVasemmalle() {
        sijainti[0] -= 1;
        if(ruudukko.voikoPaivittaa(sijainti, kaantoAlue)) {
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
            paivitaPutoamisKohta();
        }
        else {
            sijainti[0] +=1;
        }
    }

    public void liikuOikealle() {
        sijainti[0] += 1;
        if(ruudukko.voikoPaivittaa(sijainti, kaantoAlue)) {
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
            paivitaPutoamisKohta();
        }
        else {
            sijainti[0] -=1;
        }
    }

    public void kaannaVastapaivaan() {
        Ruutu[][] kaannetytRuudut = new Ruutu[kaantoAlue.length][kaantoAlue.length];
        for (int i = 0; i < kaantoAlue.length; i++) {
            for (int j = 0; j < kaantoAlue.length; j++) {
                kaannetytRuudut[j][i] = kaantoAlue[kaantoAlue.length - 1 - i][j];
            }
        }
        if(ruudukko.voikoPaivittaa(sijainti, kaannetytRuudut)) {
            kaantoAlue = kaannetytRuudut;
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
            paivitaPutoamisKohta();
        }
    }

    public void kaannaMyotapaivaan() {
        Ruutu[][] kaannetytRuudut = new Ruutu[kaantoAlue.length][kaantoAlue.length];
        for (int i = 0; i < kaantoAlue.length; i++) {
            for (int j = 0; j < kaantoAlue.length; j++) {
                kaannetytRuudut[kaantoAlue.length - 1 - i][j] = kaantoAlue[j][i];
            }
        }
        if(ruudukko.voikoPaivittaa(sijainti, kaannetytRuudut)) {
            kaantoAlue = kaannetytRuudut;
            ruudukko.paivitaRuudukko(sijainti, kaantoAlue);
            paivitaPutoamisKohta();
        }
    }

    //asettaa kohtaan johon palikka lopulta putoaisi harmaita tyhjia ruutuja
    private void paivitaPutoamisKohta() {
        ruudukko.poistaPutoamisKohtaPalikka(putoamisKohdanSijainti, kaantoAlue);

        putoamisKohdanSijainti[0] = sijainti[0];
        putoamisKohdanSijainti[1] = sijainti[1];

        //laskee sijainnin (x,y) johon putoava palikka putoaisi, jos se pudotettaisiin alas
        while(ruudukko.voikoPaivittaa(putoamisKohdanSijainti, kaantoAlue)) {
            //vahentaa y koordinaattia yhdella
            putoamisKohdanSijainti[1] += 1;
        }
        //nostaa yhdella ylospain, koska tassa vaiheessa while ehto ei enaa toteutunut
        putoamisKohdanSijainti[1] -= 1;

        if(putoamisKohdanSijainti[1] - sijainti[1] >= kaantoAlue.length) {
            ruudukko.asetaPutoamisKohtaPalikka(putoamisKohdanSijainti, kaantoAlue);
        }

        //TODO bugi, ei toimi
    }

    public Ruudukko annaRuudukko() {
        return ruudukko;
    }

    public void asetaRuudukko(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
    }

    public int[] annaSijainti() {
        return sijainti;
    }

    public Ruutu[][] annaKaantoAlue() {
        return kaantoAlue;
    }

    public int[] annaPutoamisKohdanSijainti() {
        return putoamisKohdanSijainti;
    }
}
