package gamelogic;

public class Ruutu {
    private boolean putoaako;
    private boolean onkoTaynna;
    private Vari vari;

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

    public String annaVariMerkkijonona() {
        if(vari == Vari.HARMAA) {
            return "darkgrey";
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
        else {
            return "black";
        }
    }
}
