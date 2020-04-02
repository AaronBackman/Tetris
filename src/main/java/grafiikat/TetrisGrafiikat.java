package grafiikat;

import gamelogic.Peli;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisGrafiikat extends Application {


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage ikkuna) {
        ikkuna.setTitle("Tetris Limited Edition");

        piirraPaaValikko(ikkuna);
    }

    private void piirraPaaValikko(Stage ikkuna) {
        Label otsikko = new Label("TETRIS");
        otsikko.setPrefSize(150, 50);
        otsikko.setStyle("-fx-font: 40 arial;");

        HBox ylaRivi = new HBox();
        ylaRivi.getChildren().add(otsikko);
        ylaRivi.setAlignment(Pos.CENTER);
        BorderPane.setMargin(ylaRivi, new Insets(30, 30, 30, 30));

        Button siirryPeliin = new Button("Aloita Peli");
        siirryPeliin.setOnAction(e -> {
            peliIkkuna(ikkuna);
        });
        VBox valikko = new VBox();
        valikko.setAlignment(Pos.CENTER);

        valikko.getChildren().add(siirryPeliin);

        BorderPane root = new BorderPane();
        root.setCenter(valikko);
        root.setTop(ylaRivi);


        ikkuna.setScene(new Scene(root, 600, 600));
        ikkuna.show();
    }

    private void peliIkkuna(Stage ikkuna) {
        Peli peli = new Peli();
        Timeline ajastin = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (peli.onkoPeliKaynnissa()) {
                    peli.seuraavaFrame();

                    piirraPeli(ikkuna, peli);
                }

                if (peli.onkoPeliKaynnissa() == false) {
                    try {
                        stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        ajastin.setCycleCount(Timeline.INDEFINITE);
        ajastin.play();
    }

    private void piirraPeli(Stage ikkuna, Peli peli) {

        GridPane napit = new GridPane();

        Button vasenNappi = new Button();
        vasenNappi.setText("<");
        vasenNappi.setOnAction(e -> {
            peli.otaInputti("vasen");
            this.piirraPeli(ikkuna, peli);
        });

        Button oikeaNappi = new Button();
        oikeaNappi.setText(">");
        oikeaNappi.setOnAction(e -> {
            peli.otaInputti("oikea");
            this.piirraPeli(ikkuna, peli);
        });

        Button valiLyonti = new Button();
        valiLyonti.setText("  ");

        Button alasNappi = new Button();
        alasNappi.setText("V");
        alasNappi.setOnAction(e -> {
            peli.otaInputti("alas");
            this.piirraPeli(ikkuna, peli);
        });

        Button vastaPaivaanNappi = new Button();
        vastaPaivaanNappi.setText("\\");
        vastaPaivaanNappi.setOnAction(e -> {
            peli.otaInputti("vastaPaivaan");
            this.piirraPeli(ikkuna, peli);
        });

        Button myotaPaivaanNappi = new Button();
        myotaPaivaanNappi.setText("/");
        myotaPaivaanNappi.setOnAction(e -> {
            peli.otaInputti("myotaPaivaan");
            this.piirraPeli(ikkuna, peli);
        });

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
                ruutu.setMaxSize(30, 30);

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
        ruudukko.setAlignment(Pos.CENTER);

        napit.setAlignment(Pos.BOTTOM_RIGHT);

        String pisteet = String.valueOf(peli.annaPisteet());
        TextField pisteTaulu = new TextField(pisteet);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        root.setRight(napit);
        root.setCenter(ruudukko);
        root.setTop(pisteTaulu);

        ikkuna.setScene(new Scene(root, 600, 600));
        ikkuna.show();
    }
}
