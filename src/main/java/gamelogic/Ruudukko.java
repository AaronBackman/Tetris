package gamelogic;

public class Ruudukko {
    //ruudut muotoa bbbbb (binaariluku) jossa ensimmainen bitti merkitsee tyhjyytta,
    //toinen putoamista ja loput vareja

    //10x22 kaksi ylinta rivia palikoiden luomiseen, +4 ruutua vasemmalle, oikealle ja alas = 18x26
    private Ruutu[][] ruudut;
    public final static int REUNA_ALUE = 4;
    public final static int SIJOITUS_ALUE = 2;
    public final static int KORKEUS = 20;
    public final static int LEVEYS = 10;

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

    public void paivitaRuudukko(int[] sijainti, Ruutu[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];

        if(x >= ruudut.length || x < 0) {
            throw new IndexOutOfBoundsException();
        } else if(y >= ruudut[0].length || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        for(int i=-1; i<palikkaAlue.length; i++) {
            for(int j=-1; j<palikkaAlue.length+1; j++) {

                //tarkistaa onko palikkaAlueen ulkopuolelle jaanyt osa palikkaa ja poistaa ne osat
                if(i == -1 | j == -1 | j == palikkaAlue.length) {

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
                    ruudut[x + j][y + i] = palikkaAlue[j][i];
                }
            }
        }
    }

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

    private void poistaRivi(int rivi) {
        for(int i=0; i<LEVEYS; i++) {
            RuutuTehdas rt = new RuutuTehdas();
            ruudut[i + REUNA_ALUE][rivi + SIJOITUS_ALUE] = rt.teeTyhjaRuutu();
        }
    }

    public void asetaPalikkaPudonneeksi(int[] sijainti, Ruutu[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                if(palikkaAlue[j][i].annaPutoaminen()) {
                    ruudut[x + j][y + i].asetaPutoaminen(false);
                    palikkaAlue[j][i].asetaPutoaminen(false);
                }
            }
        }
    }

    public boolean onkoPalikkaRuudunUlkopuolella(int[] sijainti, Ruutu[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];

        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
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

    public boolean voikoPaivittaa(int[] sijainti, Ruutu[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                //jos molemmat ruudut ovat taynna
                if(ruudut[x + j][y + i].onkoTaynna() && palikkaAlue[j][i].onkoTaynna() &&
                    (!ruudut[x + j][y + i].annaPutoaminen())) {

                    return false;
                }
            }
        }
        return true;
    }

    //asettaa palikan, joka nayttaa mihin palikka putoaisi, ruutua kuvaa harmaa tyhja ruutu
    public void asetaPutoamisKohtaPalikka(int[] putoamisSijainti, Ruutu[][] palikkaAlue) {
        RuutuTehdas rt = new RuutuTehdas();
        int x = putoamisSijainti[0];
        int y = putoamisSijainti[1];
        if(y < 0) {
            return;
        }

        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                if(palikkaAlue[j][i].onkoTaynna()) {
                    //asettaa palikkaAluetta vastaavan ruudun ruudukossa harmaaksi
                    ruudut[x + j][y + i] = rt.teePutoamisKohtaRuutu();
                }
            }
        }
    }

    public void poistaPutoamisKohtaPalikka(int[] putoamisSijainti, Ruutu[][] palikkaAlue) {
        int x = putoamisSijainti[0];
        int y = putoamisSijainti[1];
        if(y < 0) {
            return;
        }

        //putoamiskohtaruudut ovat vain erivarisia tyhjia ruutuja, joten varin vaihto riittaa poistamiseen
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                if(ruudut[x + j][y + i].annaVari() == Vari.HARMAA) {
                    ruudut[x + j][y + i].asetaVari(Vari.MUSTA);
                }
            }
        }
    }

    public Ruutu[][] annaRuudut() {
        return ruudut;
    }

    public void asetaRuudut(Ruutu[][] ruudut) {
        this.ruudut = ruudut;
    }
}
