package grafiikat;

import gamelogic.Ajastin;
import gamelogic.Peli;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGrafiikat  {


    public static void main(String[] args) {
        Peli peli = new Peli();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Ajastin(peli), 0, 1000);
        //launch(args);
    }

    /*@Override
    public void start(Stage primaryStage) throws Exception {

        if (peli.onkoPeliKaynnissa()) {
            peli.seuraavaFrame();

            GridPane ruudukko = new GridPane();
            ruudukko.setHgap(5);
            ruudukko.setVgap(5);
            ruudukko.setPadding(new Insets(10, 10, 10, 10));

            final int korkeus = 20;
            final int leveys = 10;

            for (int rivi = 0; rivi < korkeus; rivi++) {
                for (int sarake = 0; sarake < leveys; sarake++) {
                    StackPane ruutu = new StackPane();
                    ruutu.setMaxSize(30, 40);

                    String vari = peli.annaRuudukko().annaRuudut()[sarake+4][rivi+2].annaVariMerkkijonona();


                    ruutu.setStyle("-fx-background-color: " + vari + ";");
                    ruudukko.add(ruutu, sarake, rivi);
                }
            }
            for (int i = 0; i < korkeus; i++) {
                ruudukko.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
                ruudukko.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
            }
            primaryStage.setScene(new Scene(ruudukko, 400, 400));
            primaryStage.show();

            peli.otaInputti();
        }
    }*/
}
