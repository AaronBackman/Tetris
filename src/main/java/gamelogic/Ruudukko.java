package gamelogic;

public class Ruudukko {
    //ruudut muotoa bbbbb (binaariluku) jossa ensimmainen bitti merkitsee tyhjyytta,
    //toinen putoamista ja loput vareja

    //10x22 kaksi ylinta rivia palikoiden luomiseen, +4 ruutua vasemmalle, oikealle ja alas = 18x26
    Ruutu[][] ruudut;

    public Ruudukko() {
        //vasemmalla, oikealla ja alhaalla on 4 ylimaaraista ruutua joita kaytetaan reunan ylityksen tarkistamiseen
        this.ruudut = new Ruutu[18][26];
        RuutuTehdas ruutuTehdas = new RuutuTehdas();
        for(int i=0; i<ruudut[0].length; i++) {
            for(int j=0; j<ruudut.length; j++) {
                if((i < ruudut[0].length - 4) && (j >= 4) && (j <ruudut.length - 4)) {
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
                    if(y == 0) {
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
                if(y + j >= 2) {
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
