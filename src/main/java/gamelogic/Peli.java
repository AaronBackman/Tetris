package gamelogic;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Peli {
    private Ruudukko pelinTila;
    private Palikka putoavaPalikka;
    private boolean peliKaynnissa;

    public Peli() {
        this.pelinTila = new Ruudukko();
        this.putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
        this.peliKaynnissa = true;
    }

    public void otaInputti() {

        Scanner sc = new Scanner(System.in);
        String input = "";

        if(sc.hasNextLine()) {
            input = sc.nextLine();
        }

        if (input.equals("a")) {
            putoavaPalikka.liikuVasemmalle();
        } else if (input.equals("d")) {
            putoavaPalikka.liikuOikealle();
        } else if (input.equals("s")) {
            putoavaPalikka.pudotaYksiRuutu();
        } else if (input.equals("q")) {
            putoavaPalikka.kaannaVastapaivaan();
        } else if (input.equals("e")) {
            putoavaPalikka.kaannaMyotapaivaan();
        }
        sc.close();
    }

    public void seuraavaFrame() {
        putoavaPalikka.pudotaYksiRuutu();
        if(! putoavaPalikka.onkoPutoamassa) {

            //tarkistaa onko pudonnut palikka ruudun ulkopuolella (==peli havitty)
            if(putoavaPalikka.sijainti[1] == 0) {
                //TODO lisaa tarkistus onko laudan ulkopuolella todella palikan osia
                //TODO poista kokonaiset palikkarivit
                System.out.println("havisit pelin");
                peliKaynnissa = false;
            }
            putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
        }
    }

    public Ruudukko annaRuudukko() {
        return pelinTila;
    }

    public boolean onkoPeliKaynnissa() {
        return peliKaynnissa;
    }
}