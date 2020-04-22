package gamelogic;

import java.util.LinkedList;

/**
 * sisaltaa koko tetris pelin loogisen puolen
 */
public class Peli {
    /**
     * sisaltaa datan pelin ruutujen tilasta
     */
    private final Ruudukko pelinTila;

    /**
     * sisaltaa nykyisen putoavan palikan jalkeiset seuraavat kolme putoavaa palikkaa
     */
    private LinkedList<Palikka> seuraavatPalikat;

    /**
     * nykyinen putoamassa oleva palikka (vain yksi kerrallaan)
     */
    private Palikka putoavaPalikka;
    private boolean peliKaynnissa;
    private int pisteet;

    /**
     * asettaa pelille tyhjan ruudukon, satunnaisen putoavan palikan ja sita seuraavat 3 palikkaa
     */
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

    /**
     * ottaa syotteen string muodossa ja liikuttaa palikkaa sen mukaisesti
     * taman metodin avulla nappainten painamiset voi muuntaa helppoon muotoon ja syotta tahan metodiin
     * @param input syote string muodossa
     */
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
                putoavaPalikka.kaannaVastapaivaanRuudukossa();
                break;
            case "myotaPaivaan":
                putoavaPalikka.kaannaMyotapaivaanRuudukossa();
                break;
            case "pudotaAlasAsti":
                putoavaPalikka.pudotaNiinPaljonKuinMahdollista();
                break;
        }
    }

    /**
     * mallintaa yhden askeleen palikan pelaajasta riippumattomassa putoamisessa alaspain
     * hoitaa siihen liittyvat tarkistukse: kuten onko peli havitty tai onko palikka pudonnut
     * ja tarvitaan uusi putoava palikka
     */
    public void seuraavaFrame() {
        putoavaPalikka.pudotaYksiRuutu();
        if(! putoavaPalikka.onkoPutoamassa) {

            //tarkistaa onko pudonnut palikka ruudun ulkopuolella (==peli havitty)
            if(annaRuudukko().onkoPalikkaRuudukonUlkopuolella(putoavaPalikka.annaSijainti(), putoavaPalikka.annaPalikanMuoto())) {
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

    /**
     * @return ruudukko joka sisaltaa tiedon pelin ruuduista
     */
    public Ruudukko annaRuudukko() {
        return pelinTila;
    }

    /**
     * @return true jos peli on kaynnissa
     */
    public boolean onkoPeliKaynnissa() {
        return peliKaynnissa;
    }

    /**
     * @return pelaajalle kertynyt pistemaara
     */
    public int annaPisteet() {
        return pisteet;
    }

    /**
     * @return nykyisen putoavan palikan jalkeen putoavat palikat
     */
    public LinkedList<Palikka> annaSeuraavatPalikat() {
        return seuraavatPalikat;
    }

    /**
     * @return nykyinen putoava palikka
     */
    public Palikka annaPutoavaPalikka() {
        return putoavaPalikka;
    }

}