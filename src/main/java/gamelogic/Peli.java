package gamelogic;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Peli {
    Ruudukko pelinTila;
    Palikka putoavaPalikka;

    public Peli() {
        this.pelinTila = new Ruudukko();
    }

    public void aloitaPeli() throws InterruptedException {
        long aikavali = 1;
        this.putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
        Scanner sc = new Scanner(System.in);
        while(true) {
            pelinTila.tulostaRuudukko();
            String input = sc.nextLine();
            if(input.equals("a")) {
                putoavaPalikka.liikuVasemmalle();
            }

            else if(input.equals("d")) {
                putoavaPalikka.liikuOikealle();
            }

            else if(input.equals("s")) {
                putoavaPalikka.pudotaYksiRuutu();
            }

            else if(input.equals("q")) {
                putoavaPalikka.kaannaVastapaivaan();
            }

            else if(input.equals("e")) {
                putoavaPalikka.kaannaMyotapaivaan();
            }
            pelinTila.tulostaRuudukko();
            if(! putoavaPalikka.onkoPutoamassa) {

                //tarkistaa onko pudonnut palikka ruudun ulkopuolella (==peli havitty)
                if(putoavaPalikka.sijainti[1] == 0) {
                    //TODO lisaa tarkistus onko laudan ulkopuolella todella palikan osia
                    System.out.println("havisit pelin");
                    break;
                }
                putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
                pelinTila.tulostaRuudukko();
            }
        }
    }
}