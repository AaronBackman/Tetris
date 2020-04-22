package gamelogic;

/**
 * erinaisia metodeita joilla voi tehda yleisimmin kaytettyja ruutuja
 */
public class RuutuTehdas {
    /**
     * @param vari ruudun vari
     * @return putoava ruutu, joka on taynna ja jolla on vari
     */
    public Ruutu teePutoavaRuutu(Vari vari) {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(vari);
        ruutu.asetaPutoaminen(true);
        ruutu.asetaTaynna(true);
        return ruutu;
    }

    /**
     * @return tyhja ruutu, joka on tyhja ja musta eika putoa
     */
    public Ruutu teeTyhjaRuutu() {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(Vari.MUSTA);
        ruutu.asetaPutoaminen(false);
        ruutu.asetaTaynna(false);
        return ruutu;
    }

    /**
     * @return musta taynna oleva ruutu, tarkoitus toimia ruudukon reunoina
     */
    public Ruutu teeReunaRuutu() {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(Vari.MUSTA);
        ruutu.asetaPutoaminen(false);
        ruutu.asetaTaynna(true);
        return ruutu;
    }

    /**
     * @return haamupalikan ruutu, ei taynna eika putoamassa
     * ainoa eroava ominaisuustyhjasta ruudusta on vari
     */
    public Ruutu teePutoamisKohtaRuutu() {
        Ruutu ruutu = new Ruutu();
        ruutu.asetaVari(Vari.HARMAA);
        ruutu.asetaPutoaminen(false);
        ruutu.asetaTaynna(false);
        return ruutu;
    }
}
