package gamelogic;

public class Ruudukko {
    //ruudut muotoa bbbbb (binaariluku) jossa ensimmainen bitti merkitsee tyhjyytta,
    //toinen putoamista ja loput vareja

    //10x22 kaksi ylinta rivia palikoiden luomiseen
    int[][] ruudut;

    public Ruudukko() {
        //vasemmalla, oikealla ja alhaalla on ylimaaraisia ruutuja joita kaytetaan reunan ylityksen tarkistamiseen
        this.ruudut = new int[12][23];
        for(int i=0; i<ruudut.length; i++) {
            for(int j=0; j<ruudut[0].length; j++) {
                if((i < ruudut.length - 1) && (j > 0) && (j <ruudut.length - 1)) {
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
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                ruudut[y + j][x + i] = palikkaAlue[j][i];
            }
        }
    }
    public void asetaPalikkaPudonneeksi(int[] sijainti, int[][] palikkaAlue) {
        int x = sijainti[0];
        int y = sijainti[1];
        for(int i=0; i<palikkaAlue.length; i++) {
            for(int j=0; j<palikkaAlue.length; j++) {
                if((palikkaAlue[y][x] & (1 << 3)) == 8) {
                    ruudut[y + j][x + i] |= 0b01000;
                    palikkaAlue[y][x] |= 0b01000;
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
                if(((ruudut[y + j][x + i] & palikkaAlue[y][x]) & (1 << 4)) == 16) {
                    return false;
                }
            }
        }
        return true;
    }

    public void tulostaRuudukko() {
        //huom. reunaruudut
        for(int i=0; i<ruudut.length - 1; i++) {
            for(int j=1; j<ruudut[0].length - 1; j++) {
                if((ruudut[j][i] & (1 << 4)) == 16) {
                    System.out.print("H");
                }
                else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }
}
