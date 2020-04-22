package gamelogic;

/**
 * sisaltaa tietoa yksittaiseen ruutuun liittyvista ominaisuuksista:
 * onko putoamassa, onko taynna ja vari
 */
public class Ruutu {
    private boolean putoaako;
    private boolean onkoTaynna;
    private Vari vari;

    /**
     * muuntaa ruudun varin enum muodosta stringiksi, jonka javafx tunnistaa
     * @return vari String muodossa
     */
    public String annaVariMerkkijonona() {
        if(vari == Vari.MUSTA) {
            return "black";
        }
        else if(vari == Vari.TURKOOSI) {
            return "aqua";
        }
        else if(vari == Vari.SININEN) {
            return "blue";
        }
        else if(vari == Vari.ORANSSI) {
            return "orangered";
        }
        else if(vari == Vari.KELTAINEN) {
            return "yellow";
        }
        else if(vari == Vari.VIHREA) {
            return "lime";
        }
        else if(vari == Vari.VIOLETTI) {
            return "magenta";
        }
        else if(vari == Vari.PUNAINEN) {
            return "red";
        }
        else if(vari == Vari.HARMAA) {
            return "grey";
        }
        else {
            return "black";
        }
    }

    /**
     * vertailee ovatko kaksi ruutua samanlaiset
     * @param toinenRuutu ruutu johon verrataan
     * @return true jos samanlaiset, muuten false
     */
    public boolean equals(Ruutu toinenRuutu) {
        //tarkistaa ovatko ruudut molemmat taynna
        if(this.onkoTaynna() != toinenRuutu.onkoTaynna()) {
            return false;
        }

        //tarkistaa ovatko putoamassa
        if(this.annaPutoaminen() != toinenRuutu.annaPutoaminen()) {
            return false;
        }

        //ovatko saman varisia
        if(this.annaVari() != toinenRuutu.annaVari()) {
            return false;
        }
        //muussa tapauksessa kaikki ominaisuudet ovat samoja -> equals
        return true;
    }

    /**
     * @return true jos ruutu on putoamassa
     */
    public boolean annaPutoaminen() {
        return putoaako;
    }

    /**
     * @param putoaako asetettava boolean arvo
     */
    public void asetaPutoaminen(boolean putoaako) {
        this.putoaako = putoaako;
    }

    /**
     * @return true jos ruutu on taynna
     */
    public boolean onkoTaynna() {
        return onkoTaynna;
    }

    /**
     * @param onkoTaynna asetettava boolean arvo
     */
    public void asetaTaynna(boolean onkoTaynna) {
        this.onkoTaynna = onkoTaynna;
    }

    /**
     * @return ruudun varin enum tyyppisena
     */
    public Vari annaVari() {
        return vari;
    }

    /**
     * @param vari asetettava enum arvo
     */
    public void asetaVari(Vari vari) {
        this.vari = vari;
    }
}
