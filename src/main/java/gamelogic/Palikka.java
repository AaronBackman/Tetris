package gamelogic;

public class Palikka {
    /**
     * sisaltaa kaiken tiedon pelin palikoiden tilasta
     */
    protected Ruudukko ruudukko;

    /**
     * sisaltaa tiedon palikan ruutujen varista, palikan muodosta ja rotaatiosta
     */
    protected  Ruutu[][] palikanMuoto;

    /**
     * palikan vasemman ylakulman sijainti ruudukolla
     * ensimmainen numero on vaaka-koordinaatti, toinen pysty-koordinaatti
     */
    protected int[] sijainti;

    /**
     * palikan vasemman ylakulman sijainti ruudukolla kohdassa johon se tulisi nyt putoamaan
     * ensimmainen numero on vaaka-koordinaatti, toinen pysty-koordinaatti
     */
    protected int[] putoamisKohdanSijainti = new int[2]; //sama kuin ylla
    protected boolean onkoPutoamassa = true;

    /**
     * haamupalikka on harmaa, saman muotoinen palikka siina kohdassa johon palikka tulisi nyt putoamaan
     */
    protected boolean naytaHaamupalikka = true;

    /**
     * pudottaa palikkaa ruudukolla yhden ruudun
     * paivittaa palikkaan liittyvan ruudukon ja muuttaa palikan sijaintia
     */
    public void pudotaYksiRuutu() {
        sijainti[1] += 1;
        if(ruudukko.voikoPaivittaa(sijainti, palikanMuoto)) {
            ruudukko.paivitaRuudukko(sijainti, palikanMuoto);
            paivitaPutoamisKohta();
        }
        else {
            sijainti[1] -= 1;
            ruudukko.asetaPalikkaPudonneeksi(sijainti, palikanMuoto);
            onkoPutoamassa = false;
        }
    }

    /**
     * asettaa palikan ruudukko kenttään palikan muodon ja sijainnin mukaisesti ruutuja
     */
    public void asetaRuudukolle() {
        ruudukko.paivitaRuudukko(sijainti, palikanMuoto);
        paivitaPutoamisKohta();
    }

    /**
     * pudottaa palikkaa kunnes se osuu johonkin ja asettaa sen sitten pudonneeksi
     */
    public void pudotaNiinPaljonKuinMahdollista() {
        //pudottaa kunnes putoaa alas asti
        while(onkoPutoamassa) {
            pudotaYksiRuutu();
        }
    }

    /**
     * liikuttaa palikkaa yhden ruudun vasemmalle ruudukossa
     */
    public void liikuVasemmalle() {
        sijainti[0] -= 1;
        if(ruudukko.voikoPaivittaa(sijainti, palikanMuoto)) {
            ruudukko.paivitaRuudukko(sijainti, palikanMuoto);
            paivitaPutoamisKohta();
        }
        else {
            sijainti[0] +=1;
        }
    }

    /**
     * liikuttaa palikkaa yhden ruudun oikealle ruudukossa
     */
    public void liikuOikealle() {
        sijainti[0] += 1;
        if(ruudukko.voikoPaivittaa(sijainti, palikanMuoto)) {
            ruudukko.paivitaRuudukko(sijainti, palikanMuoto);
            paivitaPutoamisKohta();
        }
        else {
            sijainti[0] -=1;
        }
    }

    /**
     * muuttaa palikan muotoa siten etta se on kaantynut vastapaivaan, jos voi kaantya
     * paivittaa myos palikan uuden muodon ruudukolle
     */
    public void kaannaVastapaivaanRuudukossa() {
        Ruutu[][] kaannetytRuudut = kaannaVastapaivaan();

        if(ruudukko.voikoPaivittaa(sijainti, kaannetytRuudut)) {
            palikanMuoto = kaannetytRuudut;
            ruudukko.paivitaRuudukko(sijainti, palikanMuoto);
            paivitaPutoamisKohta();
        }
    }

    private Ruutu[][] kaannaVastapaivaan() {
        Ruutu[][] kaannetytRuudut = new Ruutu[palikanMuoto.length][palikanMuoto.length];
        for (int i = 0; i < palikanMuoto.length; i++) {
            for (int j = 0; j < palikanMuoto.length; j++) {
                kaannetytRuudut[j][i] = palikanMuoto[palikanMuoto.length - 1 - i][j];
            }
        }
        return kaannetytRuudut;
    }

    /**
     * muuttaa palikan muotoa siten etta se on kaantynut myotapaivaan, jos voi kaantya
     * paivittaa myos palikan uuden muodon ruudukolle
     */
    public void kaannaMyotapaivaanRuudukossa() {
        Ruutu[][] kaannetytRuudut = kaannaMyotapaivaan();

        if(ruudukko.voikoPaivittaa(sijainti, kaannetytRuudut)) {
            palikanMuoto = kaannetytRuudut;
            ruudukko.paivitaRuudukko(sijainti, palikanMuoto);
            paivitaPutoamisKohta();
        }
    }

    private Ruutu[][] kaannaMyotapaivaan() {
        Ruutu[][] kaannetytRuudut = new Ruutu[palikanMuoto.length][palikanMuoto.length];
        for (int i = 0; i < palikanMuoto.length; i++) {
            for (int j = 0; j < palikanMuoto.length; j++) {
                kaannetytRuudut[palikanMuoto.length - 1 - i][j] = palikanMuoto[j][i];
            }
        }
        return kaannetytRuudut;
    }

    /**
     * poistaa ensin vanhan haamupalikan, laskee uuden putoamis sijainnin ja asettaa sitten sinne haamupalikan
     */
    private void paivitaPutoamisKohta() {
        ruudukko.poistaHaamuPalikka(putoamisKohdanSijainti, palikanMuoto);
        if(naytaHaamupalikka == false) {
            //metodi ei saa tehda mitaan tassa tapauksessa, muuta kuin poistaa vanhan haamupalikan(jos edes olemassa)
            return;
        }
        putoamisKohdanSijainti[0] = sijainti[0];
        putoamisKohdanSijainti[1] = sijainti[1];

        //laskee sijainnin (x,y) johon putoava palikka putoaisi, jos se pudotettaisiin alas
        while(ruudukko.voikoPaivittaa(putoamisKohdanSijainti, palikanMuoto)) {
            //vahentaa y koordinaattia yhdella
            putoamisKohdanSijainti[1] += 1;
        }
        //nostaa yhdella ylospain, koska tassa vaiheessa while ehto ei enaa toteutunut
        putoamisKohdanSijainti[1] -= 1;

        if(putoamisKohdanSijainti[1] - sijainti[1] > 0) {
            ruudukko.asetaHaamuPalikka(putoamisKohdanSijainti, palikanMuoto);
        }
    }

    /**
     * @return ruudukko joka sisaltaa tiedon pelin ruuduista
     */
    public Ruudukko annaRuudukko() {
        return ruudukko;
    }

    /**
     * @param ruudukko joka kuvaa pelin ruutujen tilaa
     */
    public void asetaRuudukko(Ruudukko ruudukko) {
        this.ruudukko = ruudukko;
    }

    /**
     * @return palikan sijainti ruudukolla, ensimmainen vaaka ja toinen pysty-koordinaatti
     */
    public int[] annaSijainti() {
        return sijainti;
    }

    /**
     * @param sijainti ruudukolla joka asetetaan, ensimmainen vaaka, toinen pysty-koordinaatti
     */
    public void asetaSijainti(int[] sijainti) {
        this.sijainti = sijainti;
    }

    /**
     * @return tieto palikan ruuduista palikan muotoisesti
     */
    public Ruutu[][] annaPalikanMuoto() {
        return palikanMuoto;
    }

    /**
     * @param palikanMuoto tieto palikan ruuduista palikan muotoisesti
     */
    public void asetaPalikanMuoto(Ruutu[][] palikanMuoto) {
        this.palikanMuoto = palikanMuoto;
    }

    /**
     * @return palikan putoamiskohdan sijainti, ensimmainen vaaka, toinen pysty-koordinaatti
     */
    public int[] annaPutoamisKohdanSijainti() {
        return putoamisKohdanSijainti;
    }

    /**
     * @param naytaHaamupalikka jos true haamupalikka naytetaan, muuten false
     */
    public void asetaNaytaHaamuPalikka(boolean naytaHaamupalikka) {
        this.naytaHaamupalikka = naytaHaamupalikka;
    }

    /**
     * @return naytaHaamupalikka, jos true haamupalikka naytetaan, muuten false
     */
    public boolean annaNaytaHaamuPalikka() {
        return naytaHaamupalikka;
    }
}
