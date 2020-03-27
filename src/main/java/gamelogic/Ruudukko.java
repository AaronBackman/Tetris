package gamelogic;

public class Ruudukko {
    //ruudut muotoa bbbbb (binaariluku) jossa ensimmainen bitti merkitsee tyhjyytta,
    //toinen putoamista ja loput vareja

    //10x22 kaksi ylinta rivia palikoiden luomiseen, +4 ruutua vasemmalle, oikealle ja alas = 18x26
    int[][] ruudut;

    public Ruudukko() {
        //vasemmalla, oikealla ja alhaalla on 4 ylimaaraista ruutua joita kaytetaan reunan ylityksen tarkistamiseen
        this.ruudut = new int[18][26];
        for(int i=0; i<ruudut[0].length; i++) {
            for(int j=0; j<ruudut.length; j++) {
                if((i < ruudut[0].length - 4) && (j >= 4) && (j <ruudut.length - 4)) {
                    ruudut[j][i] = 0;
                }
                else {
                    ruudut[j][i] = 16;
                }
            }
        }
    }

    public void paivitaRuudukko(int[] sijainti, int[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=-1; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {

                //tarkistaa onko palikkaAlueen paalle jaanyt osa palikkaa ja poistaa ne osat
                if(i == -1) {
                    if ((ruudut[x + j][y + i] & (1 << 3)) == 8) {
                        ruudut[x + j][y + i] = 0;
                    }
                continue;
                }

                //tarkistaa ettei kohderuudussa ole ei-putoavaa palikkaa
                else if(!((ruudut[x + j][y + i] & (0b11 << 3)) == 0b10000)) {
                    ruudut[x + j][y + i] = palikkaAlue[j][i];
                }
            }
        }
    }
    public void asetaPalikkaPudonneeksi(int[] sijainti, int[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                if((palikkaAlue[j][i] & (1 << 3)) == 8) {
                    ruudut[x + j][y + i] |= 0b01000;
                    palikkaAlue[j][i] |= 0b01000;
                }
            }
        }
    }

    public boolean voikoPaivittaa(int[] sijainti, int[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                //jos molemmat ruudut ovat taynna
                if(((ruudut[x + j][y + i] & palikkaAlue[j][i]) & (1 << 4)) == 16 &&
                        (ruudut[x + j][y + i] & (1 << 3)) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void tulostaRuudukko() {
        //huom. reunaruudut
        for(int i=2; i<ruudut[0].length - 4; i++) {
            for(int j=4; j<ruudut.length - 4; j++) {
                if((ruudut[j][i] & (1 << 4)) == 16) {
                    System.out.print("H");
                }
                else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
        System.out.println("__________");
    }
}
