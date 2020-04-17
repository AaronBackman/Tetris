package gamelogic;

import grafiikat.HaamuKytkinNappi;

import java.util.LinkedList;

public class Peli {
    private Ruudukko pelinTila;
    private LinkedList<Palikka> seuraavatPalikat;
    private Palikka putoavaPalikka;
    private boolean peliKaynnissa;
    private int pisteet;

    public Peli() {
        this.pelinTila = new Ruudukko();
        this.putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
        this.seuraavatPalikat = new LinkedList<>();

        //varastoi 3 seuraavaa palikkaa
        for(int i=0; i<3; i++) {
            seuraavatPalikat.add(new PalikkaTehdas().teeSatunnainenPalikka(pelinTila));
        }
        this.peliKaynnissa = true;
    }

    public void otaInputti(String input) {

        switch (input) {
            case "vasen":
                putoavaPalikka.liikuVasemmalle();
                break;
            case "oikea":
                putoavaPalikka.liikuOikealle();
                break;
            case "alas":
                putoavaPalikka.pudotaYksiRuutu();
                break;
            case "vastaPaivaan":
                putoavaPalikka.kaannaVastapaivaan();
                break;
            case "myotaPaivaan":
                putoavaPalikka.kaannaMyotapaivaanRuudukossa();
                break;
            case "pudotaAlasAsti":
                putoavaPalikka.pudotaNiinPaljonKuinMahdollista();
                break;
        }
    }

    public void seuraavaFrame() {
        putoavaPalikka.pudotaYksiRuutu();
        if(! putoavaPalikka.onkoPutoamassa) {

            //tarkistaa onko pudonnut palikka ruudun ulkopuolella (==peli havitty)
            if(annaRuudukko().onkoPalikkaRuudunUlkopuolella(putoavaPalikka.annaSijainti(), putoavaPalikka.annaKaantoAlue())) {
                this.peliKaynnissa = false;
            }

            pisteet += pelinTila.poistaTaydetRuudut();

            seuraavatPalikat.add(new PalikkaTehdas().teeSatunnainenPalikka(pelinTila));

            //asettaa haamupalikan nayttamisen seuraavalle palikalla samaksi kuin edellisella palikalla
            boolean naytaHaamuPalikka = putoavaPalikka.annaNaytaHaamuPalikka();
            putoavaPalikka = seuraavatPalikat.poll();
            putoavaPalikka.asetaNaytaHaamuPalikka(naytaHaamuPalikka);
            putoavaPalikka.asetaRuudukolle();
        }
    }

    public Ruudukko annaRuudukko() {
        return pelinTila;
    }

    public boolean onkoPeliKaynnissa() {
        return peliKaynnissa;
    }

    public int annaPisteet() {
        return pisteet;
    }

    public LinkedList<Palikka> annaSeuraavatPalikat() {
        return seuraavatPalikat;
    }

    public Palikka annaPutoavaPalikka() {
        return putoavaPalikka;
    }

}