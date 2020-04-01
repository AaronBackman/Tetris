package grafiikat;

import gamelogic.Peli;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class VastaPaivaanNappi extends NappiTapahtuma {

    public VastaPaivaanNappi(TetrisGrafiikat grafiikat, Stage ikkuna, Peli peli) {
        super(grafiikat, ikkuna, peli);
    }

    @Override
    public void handle(ActionEvent event) {
        peli.otaInputti("vastaPaivaan");
        grafiikat.piirra(ikkuna, peli);
    }
}