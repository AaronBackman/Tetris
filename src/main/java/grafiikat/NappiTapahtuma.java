package grafiikat;

import gamelogic.Peli;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public abstract class NappiTapahtuma implements EventHandler<ActionEvent> {
    protected Peli peli;
    protected TetrisGrafiikat grafiikat;
    protected Stage ikkuna;

    public NappiTapahtuma(TetrisGrafiikat grafiikat, Stage ikkuna, Peli peli) {
        this.peli = peli;
        this.ikkuna = ikkuna;
        this.grafiikat = grafiikat;
    }
}
