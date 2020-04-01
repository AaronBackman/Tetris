package grafiikat;

import gamelogic.Peli;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TetrisGrafiikat extends Application {


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage ikkuna) {
        Peli peli = new Peli();
        if (peli.onkoPeliKaynnissa()) {
            peli.seuraavaFrame();

            piirra(ikkuna, peli);
            //TODO timeri TimeLine luokalla(?)
        }

        if (peli.onkoPeliKaynnissa() == false) {
            System.out.println("havisit pelin");
        }
    }

    public void piirra(Stage ikkuna, Peli peli) {

        GridPane napit = new GridPane();
        Button vasenNappi = new Button();
        vasenNappi.setText("<");
        vasenNappi.setOnAction(new VasenNappi(this, ikkuna, peli));

        Button oikeaNappi = new Button();
        oikeaNappi.setText(">");
        oikeaNappi.setOnAction(new OikeaNappi(this, ikkuna, peli));

        Button valiLyonti = new Button();
        valiLyonti.setText("  ");

        Button alasNappi = new Button();
        alasNappi.setText("V");
        alasNappi.setOnAction(new AlasNappi(this, ikkuna, peli));

        Button vastaPaivaanNappi = new Button();
        vastaPaivaanNappi.setText("\\");
        vastaPaivaanNappi.setOnAction(new VastaPaivaanNappi(this, ikkuna, peli));

        Button myotaPaivaanNappi = new Button();
        myotaPaivaanNappi.setText("/");
        myotaPaivaanNappi.setOnAction(new MyotaPaivaanNappi(this, ikkuna, peli));

        napit.add(vastaPaivaanNappi, 0, 0);
        napit.add(myotaPaivaanNappi, 2, 0);
        napit.add(vasenNappi, 0, 1);
        napit.add(alasNappi, 1, 1);
        napit.add(oikeaNappi, 2, 1);
        napit.add(valiLyonti, 1, 2);

        napit.setPrefHeight(50);
        napit.setPrefWidth(100);

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

                String vari = peli.annaRuudukko().annaRuudut()[sarake + 4][rivi + 2].annaVariMerkkijonona();


                ruutu.setStyle("-fx-background-color: " + vari + ";");
                ruudukko.add(ruutu, sarake, rivi);
            }
        }
        for (int i = 0; i < korkeus; i++) {
            ruudukko.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            ruudukko.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        ruudukko.prefHeight(250);
        ruudukko.prefWidth(500);

        napit.setAlignment(Pos.BOTTOM_RIGHT);

        HBox root = new HBox();
        root.setPadding(new Insets(10, 10, 10, 10));

        root.getChildren().addAll(ruudukko, napit);
        root.setHgrow(ruudukko, Priority.ALWAYS);

        ikkuna.setScene(new Scene(root, 600, 600));
        ikkuna.show();
    }
}
