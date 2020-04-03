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
        int pisteet = 0;

        for(int i=0; i<KORKEUS; i++) {
            int taydetRuudutRivilla = 0;

            for(int j=0; j<LEVEYS; j++) {
                if(ruudut[j + REUNA_ALUE][i + SIJOITUS_ALUE].onkoTaynna()) {
                    taydetRuudutRivilla += 1;
                }
            }
            if(taydetRuudutRivilla == LEVEYS) {
                pudotaRiville(i);
                pisteet += 1;
            }
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
            for(int j=0; j<palikkaAlue[0].length; j++) {
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

    public Ruutu[][] annaRuudut() {
        return ruudut;
    }
}
