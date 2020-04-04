package gamelogic;

public class RuutuTehdas {
    public Ruutu teePutoavaRuutu(Vari vari) {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(vari);
        ruutu.asetaPutoaminen(true);
        ruutu.asetaTaynna(true);
        return ruutu;
    }

    public Ruutu teeTyhjaRuutu() {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(Vari.MUSTA);
        ruutu.asetaPutoaminen(false);
        ruutu.asetaTaynna(false);
        return ruutu;
    }
    public Ruutu teeReunaRuutu() {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(Vari.MUSTA);
        ruutu.asetaPutoaminen(false);
        ruutu.asetaTaynna(true);
        return ruutu;
    }
}
