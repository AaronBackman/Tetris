package grafiikat;

import gamelogic.Peli;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AlasNappi extends NappiTapahtuma {

    public AlasNappi(TetrisGrafiikat grafiikat, Stage ikkuna, Peli peli) {
        super(grafiikat, ikkuna, peli);
    }

    @Override
    public void handle(ActionEvent event) {
        peli.otaInputti("alas");
        grafiikat.piirra(ikkuna, peli);
    }
}