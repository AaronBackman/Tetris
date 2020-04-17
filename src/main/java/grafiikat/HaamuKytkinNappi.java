package grafiikat;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

//haamunapin on/off kytkin
public class HaamuKytkinNappi extends StackPane {
    private final Button nappi = new Button("Piilota Haamupalikka");
    private final String tyyliOff = "-fx-font-size:20;-fx-background-color: RED;";
    private final String tyyliOn = "-fx-font-size:20;-fx-background-color: GREEN;";

    //ON = true, OFF = false, oletuksena haamupalikka naytetaan
    private boolean tila = true;

    private void init() {
        getChildren().addAll(nappi);
        setAlignment(nappi, Pos.CENTER);
        nappi.setPrefSize(250, 60);
        if(tila) {
            nappi.setStyle(tyyliOn);
        }
        else {
            nappi.setStyle(tyyliOff);
        }
    }

    public HaamuKytkinNappi() {
        init();
        EventHandler<Event> klikkaus = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (tila) {
                    nappi.setStyle(tyyliOff);
                    nappi.setText("Näytä Haamupalikka");
                    tila = false;
                } else {
                    nappi.setStyle(tyyliOn);
                    nappi.setText("Piilota Haamupalikka");
                    tila = true;
                }
            }
        };

        nappi.setFocusTraversable(false);
        setOnMouseClicked(klikkaus);
        nappi.setOnMouseClicked(klikkaus);
    }

    public boolean annaTila() {
        return tila;
    }
}
