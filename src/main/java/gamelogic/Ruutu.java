package gamelogic;

public class Ruutu {
    private boolean putoaako;
    private boolean onkoTaynna;
    private Vari vari;

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

    public boolean annaPutoaminen() {
        return putoaako;
    }

    public void asetaPutoaminen(boolean putoaako) {
        this.putoaako = putoaako;
    }

    public boolean onkoTaynna() {
        return onkoTaynna;
    }

    public void asetaTaynna(boolean onkoTaynna) {
        this.onkoTaynna = onkoTaynna;
    }

    public Vari annaVari() {
        return vari;
    }

    public void asetaVari(Vari vari) {
        this.vari = vari;
    }
}
