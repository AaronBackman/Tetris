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
}
