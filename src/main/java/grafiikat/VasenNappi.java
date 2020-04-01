package grafiikat;

import gamelogic.Peli;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class VasenNappi extends NappiTapahtuma {

    public VasenNappi(TetrisGrafiikat grafiikat, Stage ikkuna, Peli peli) {
        super(grafiikat, ikkuna, peli);
    }

    @Override
    public void handle(ActionEvent event) {
        peli.otaInputti("vasen");
        grafiikat.piirra(ikkuna, peli);
    }
}
