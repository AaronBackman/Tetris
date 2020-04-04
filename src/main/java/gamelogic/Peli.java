package gamelogic;

public class Peli {
    private Ruudukko pelinTila;
    private Palikka putoavaPalikka;
    private boolean peliKaynnissa;
    private int pisteet;

    public Peli() {
        this.pelinTila = new Ruudukko();
        this.putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
        this.peliKaynnissa = true;
    }

    public void otaInputti(String input) {

        if (input.equals("vasen")) {
            putoavaPalikka.liikuVasemmalle();
        } else if (input.equals("oikea")) {
            putoavaPalikka.liikuOikealle();
        } else if (input.equals("alas")) {
            putoavaPalikka.pudotaYksiRuutu();
        } else if (input.equals("vastaPaivaan")) {
            putoavaPalikka.kaannaVastapaivaan();
        } else if (input.equals("myotaPaivaan")) {
            putoavaPalikka.kaannaMyotapaivaan();
        }
        else if (input.equals("pudotaAlasAsti")) {
            putoavaPalikka.pudotaNiinPaljonKuinMahdollista();
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

            putoavaPalikka = new PalikkaTehdas().teeSatunnainenPalikka(pelinTila);
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
}