package grafiikat;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * on/off kytkin jolla voi valita nayttaako peli haamupalikan vai ei
 */
public class HaamuKytkinNappi extends StackPane {
    private final Button nappi = new Button("Piilota Haamupalikka");
    private final String tyyliOff = "-fx-font-size:20;-fx-background-color: RED;";
    private final String tyyliOn = "-fx-font-size:20;-fx-background-color: GREEN;";

    /**
     * kytkimen tila: true = ON, false = OFF, oletuksena haamupalikka naytetaan
     */
    private boolean tila = true;

    /**
     * alustaa tietyt perusominaisuudet
     */
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

    /**
     * asettaa kytkimelle tietyt perusominaisuudet (esim koko) ja napin toiminnallisuuden
     */
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

    /**
     * @return kytkimen tila: naytetaanko haamupalikka, true = ON, false = OFF
     */
    public boolean annaTila() {
        return tila;
    }
}
