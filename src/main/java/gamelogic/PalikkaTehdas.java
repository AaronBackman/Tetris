package gamelogic;

import java.util.Random;

public class PalikkaTehdas {

    public Palikka teeSatunnainenPalikka(Ruudukko ruudukko) {
        Random r = new Random();
        int n = r.nextInt(7);
        if(n == 0) {
            return new Ipalikka(ruudukko);
        }
        else if(n == 1) {
            return new Jpalikka(ruudukko);
        }
        else if(n == 2) {
            return new Lpalikka(ruudukko);
        }
        else if(n == 3) {
            return new Opalikka(ruudukko);
        }
        else if(n == 4) {
            return new Spalikka(ruudukko);
        }
        else if(n == 5) {
            return new Tpalikka(ruudukko);
        }
        else if(n == 6) {
            return new Zpalikka(ruudukko);
        }
        else {
            //ei pitaisi tapahtua koskaan, kaantaja vaatii returnin
            return null;
        }
    }
}
