package grafiikat;

import gamelogic.Peli;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class MyotaPaivaanNappi extends NappiTapahtuma {

    public MyotaPaivaanNappi(TetrisGrafiikat grafiikat, Stage ikkuna, Peli peli) {
        super(grafiikat, ikkuna, peli);
    }

    @Override
    public void handle(ActionEvent event) {
        peli.otaInputti("myotaPaivaan");
        grafiikat.piirra(ikkuna, peli);
    }
}