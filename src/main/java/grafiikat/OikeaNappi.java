package grafiikat;

import gamelogic.Peli;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class OikeaNappi extends NappiTapahtuma {

    public OikeaNappi(TetrisGrafiikat grafiikat, Stage ikkuna, Peli peli) {
        super(grafiikat, ikkuna, peli);
    }

    @Override
    public void handle(ActionEvent event) {
        peli.otaInputti("oikea");
        grafiikat.piirra(ikkuna, peli);
    }
}