package gamelogic;

public class Ruudukko {

    private Ruutu[][] ruudut;

    /**
     * ruudukkoa alhaalta, vasemmalta ja oikealta rajaavan alueen syvyys, ei tulisi nakya pelaajalle
     * mahdollistaa ettei tarvitse tarkistaa onko IndexOutOfBounds, kun palikkaa liikutellaan
     */
    public final static int REUNA_ALUE = 4;

    /**
     * ruudukon ylaosassa olevan kayttajalle nakymattoman alueen syvyys
     * alueelle sijoitetaan uudet palikat
     * peli on havitty jos palikka putoaa tanne alueelle (ruudukon ulkopuolella)
     */
    public final static int SIJOITUS_ALUE = 2;

    /**
     * pelaajalle nakyvan alueen korkeus
     */
    public final static int KORKEUS = 20;

    /**
     * pelaajalle nakyvan alueen leveys
     */
    public final static int LEVEYS = 10;

    /**
     * alustaa ruudukon ruudut, pelaajalle nakyva alue ja sijoitus alue ovat tyhjia,
     * reuna alueet on taytetty
     */
    public Ruudukko() {
        //vasemmalla, oikealla ja alhaalla on 4 ylimaaraista ruutua joita kaytetaan reunan ylityksen tarkistamiseen
        this.ruudut = new Ruutu[LEVEYS + 2 * REUNA_ALUE][KORKEUS + SIJOITUS_ALUE + REUNA_ALUE];
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        for(int i=0; i<ruudut[0].length; i++) {
            for(int j=0; j<ruudut.length; j++) {
                if((i < ruudut[0].length - REUNA_ALUE) && (j >= REUNA_ALUE) && (j <ruudut.length - REUNA_ALUE)) {
                    ruudut[j][i] = ruutuTehdas.teeTyhjaRuutu();
                }
                else {
                    ruudut[j][i] = ruutuTehdas.teeReunaRuutu();
                }
            }
        }
    }

    /**
     * sijoittaa palikan sijaintia ja muotoa vastaavasti ruudukolle
     * @param sijainti paikka johon palikka laitetaan
     * @param palikanMuoto sisaltaa tiedon palikan muodosta
     * @throws IndexOutOfBoundsException jos palikka sijaitsee reuna alueella tai koko ruudukon ulkopuolella
     */
    public void paivitaRuudukko(int[] sijainti, Ruutu[][] palikanMuoto) {
        int x = sijainti[0];
        int y = sijainti[1];

        /* poikkeus heitetaan myos reuna alueella, koska ruutuja tarkistetaan sijainnista oikealle ja alaspain
        jolloin osa tarkistuksista menisi ruudukon ulkopuolelle */
        if(x >= ruudut.length - REUNA_ALUE || x < 0) {
            throw new IndexOutOfBoundsException();
        } else if(y >= ruudut[0].length - REUNA_ALUE || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        for(int i=-1; i<palikanMuoto.length; i++) {
            for(int j=-1; j<palikanMuoto.length+1; j++) {

                //tarkistaa onko palikkaAlueen ulkopuolelle jaanyt osa palikkaa ja poistaa ne osat
                if(i == -1 | j == -1 | j == palikanMuoto.length) {

                    //palikka on niin ylhaalla kuin mahdollista -> sen paalla ei ole ruutuja
                    if(y == 0 && i == -1) {
                        continue;
                    }
                    if (ruudut[x + j][y + i].annaPutoaminen()) {
                        ruudut[x + j][y + i] = ruutuTehdas.teeTyhjaRuutu();
                    }
                }

                //tarkistaa ettei kohderuudussa ole ei-putoavaa palikkaa
                else if(ruudut[x + j][y + i].onkoTaynna() == false || ruudut[x + j][y + i].annaPutoaminen()) {
                    ruudut[x + j][y + i] = palikanMuoto[j][i];
                }
            }
        }
    }

    /**
     * tarkistaa onko sijaintia ja palikan muotoa vastaavalla alueella seka taynna oleva ruudukon ja palikan ruutu
     * jos ei ole palikka voidaan asettaa ruudukolle
     * @param sijainti asetettvan palikan sijainti, vaaka ja pysty koordinaatti vasemmasta ylakulmasta
     * @param palikanMuoto asetettavan palikan muoto
     * @return true jos palikan voi asettaa ruudukolle
     */
    public boolean voikoPaivittaa(int[] sijainti, Ruutu[][] palikanMuoto) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikanMuoto.length; i++) {
            for(int j=0; j<palikanMuoto.length; j++) {
                //jos molemmat ruudut ovat taynna
                if(ruudut[x + j][y + i].onkoTaynna() && palikanMuoto[j][i].onkoTaynna() &&
                        (!ruudut[x + j][y + i].annaPutoaminen())) {

                    return false;
                }
            }
        }
        return true;
    }

    /**
     * katsoo onko taysia riveja ja poistaa ne jos on
     * pudottaa lopuksi poistettujen rivien paalla olevia palikoita
     * @see Ruudukko#pudotaRiville
     * @see Ruudukko#poistaRivi
     * @return poistettujen rivien maaraa vastaava pistemaara
     */
    public int poistaTaydetRuudut() {
        int poistetutRivit = 0;

        for(int i=0; i<KORKEUS; i++) {
            int taydetRuudutRivilla = 0;

            for(int j=0; j<LEVEYS; j++) {
                if(ruudut[j + REUNA_ALUE][i + SIJOITUS_ALUE].onkoTaynna()) {
                    taydetRuudutRivilla += 1;
                }
            }
            if(taydetRuudutRivilla == LEVEYS) {
                pudotaRiville(i);
                poistetutRivit += 1;
            }
        }

        int pisteet = 0;
        switch (poistetutRivit) {
            case 1:
                pisteet = 40;
                break;
            case 2:
                pisteet = 100;
                break;
            case 3:
                pisteet = 300;
                break;
            case 4:
                pisteet = 1200;
                break;
            default:
                break;
        }
        return  pisteet;
    }

    /**
     * tyhjentaa rivin ja pudottaa sen paalla olevia ruutuja yhden ruudun alaspain
     * @param rivi rivin pystykoordinaatti jonka paalla olevia ruutuja pudotetaan alas
     */
    private void pudotaRiville(int rivi) {
        poistaRivi(rivi);

        RuutuTehdas rt = new RuutuTehdas();

        for(int i = rivi+SIJOITUS_ALUE-1; i>=0; i--) {
            for(int j=0; j<LEVEYS; j++) {
                if(ruudut[j + REUNA_ALUE][i].onkoTaynna()) {
                    //pudottaa palikan yhden ruudun alaspain, joka on tyhja koska rivi poistetaan/pudotetaan ensin
                    ruudut[j + REUNA_ALUE][i + 1] = ruudut[j + REUNA_ALUE][i];

                    //poistaa palikan, jotta siihen voidaan pudottaa palikka myohemmin
                    ruudut[j + REUNA_ALUE][i] = rt.teeTyhjaRuutu();
                }
            }
        }
    }

    /**
     * @param rivi tyhjentaa annetun rivin
     */
    private void poistaRivi(int rivi) {
        for(int i=0; i<LEVEYS; i++) {
            RuutuTehdas rt = new RuutuTehdas();
            ruudut[i + REUNA_ALUE][rivi + SIJOITUS_ALUE] = rt.teeTyhjaRuutu();
        }
    }

    /**
     * asettaa annetulta alueelta olevilta palikoilta putoamisominaisuuden pois
     * @param sijainti palikanMuoto alueen vasemman ylakuman sijainti ruudukolla
     * @param palikanMuoto tieto palikan muodosta
     */
    public void asetaPalikkaPudonneeksi(int[] sijainti, Ruutu[][] palikanMuoto) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikanMuoto.length; i++) {
            for(int j=0; j<palikanMuoto.length; j++) {
                if(palikanMuoto[j][i].annaPutoaminen()) {
                    ruudut[x + j][y + i].asetaPutoaminen(false);
                    palikanMuoto[j][i].asetaPutoaminen(false);
                }
            }
        }
    }

    /**
     * tarkistaa onko taynna olevia osia palikasta ruudukon ulkopuolella (sijoitusalueella)
     * @param sijainti palikan vasemman ylakulman sijainti ruudukolla, vaaka ja pystykoordinaatti
     * @param palikanMuoto sisaltaa tiedon palikan muodosta
     * @return true jos palikka on ruudukon ulkopuolella, muuten false
     */
    public boolean onkoPalikkaRuudukonUlkopuolella(int[] sijainti, Ruutu[][] palikanMuoto) {
        int x = sijainti[0];
        int y = sijainti[1];

        for(int i=0; i<palikanMuoto.length; i++) {
            for(int j=0; j<palikanMuoto.length; j++) {
                if(y + j >= SIJOITUS_ALUE) {
                    return false;
                }
                else if(ruudut[x + j][y + i].onkoTaynna()) {
                    //ruudukon ylapuolella on palikka ylempana olevan ehdon perusteella
                    return true;
                }
            }
        }
        //ei tapahdu koskaan, mutta kaantaja vaatii returnin
        return false;
    }

    /**
     * asettaa ruudukolle ns. haamupalikan joka nayttaa mihin palikka tulisi nyt putoamaan
     * @param putoamisSijainti kohta ruudukolla johon palikka tulisi putoamaan, vaaka ja pystykoordinaatti
     * vasemmasta ylakulmasta
     * @param palikanMuoto sisaltaa tiedon haamupalikan muodosta
     */
    public void asetaHaamuPalikka(int[] putoamisSijainti, Ruutu[][] palikanMuoto) {
        RuutuTehdas rt = new RuutuTehdas();
        int x = putoamisSijainti[0];
        int y = putoamisSijainti[1];
        if(y < 0 || x < 0) {
            return;
        }

        for(int i=0; i<palikanMuoto.length; i++) {
            for(int j=0; j<palikanMuoto.length; j++) {
                if(palikanMuoto[j][i].onkoTaynna() && ruudut[x + j][y + i].annaPutoaminen() == false) {
                    //asettaa palikanMuotoa vastaavan ruudun ruudukossa harmaaksi
                    ruudut[x + j][y + i] = rt.teePutoamisKohtaRuutu();
                }
            }
        }
    }

    /**
     * poistaa ruudukolta (vanhan) haamupalikan
     * @param putoamisSijainti (vanhan) haamupalikan sijainti ruudukolla, vaaka ja pystykoordinaatti
     * vasemmasta ylakulmasta
     * @param palikanMuoto sisaltaa tiedon haamupalikan muodosta
     */
    public void poistaHaamuPalikka(int[] putoamisSijainti, Ruutu[][] palikanMuoto) {
        int x = putoamisSijainti[0];
        int y = putoamisSijainti[1];
        if(x == 0 && y == 0) {
            //tassa tapauksessa palikka on juuri luotu ja putoamiskohtaa ei ole viela laskettu (reuna-alueella)
            return;
        }
        if(y < 0) {
            return;
        }

        //putoamiskohtaruudut ovat vain erivarisia tyhjia ruutuja, joten varin vaihto riittaa poistamiseen
        for(int i=0; i<palikanMuoto.length; i++) {
            for(int j=0; j<palikanMuoto.length; j++) {
                if(ruudut[x + j][y + i].annaVari() == Vari.HARMAA) {
                    ruudut[x + j][y + i].asetaVari(Vari.MUSTA);
                }
            }
        }
    }

    /**
     * @return palauttaa ruudukon ruudut, eli siis kaiken ruudukon sisaltaman tiedon
     */
    public Ruutu[][] annaRuudut() {
        return ruudut;
    }

    /**
     * @param ruudut ruudukon ruuduiksi asetettavat ruudut
     */
    public void asetaRuudut(Ruutu[][] ruudut) {
        this.ruudut = ruudut;
    }
}
