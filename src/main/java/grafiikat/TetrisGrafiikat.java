package grafiikat;

import gamelogic.Peli;
import gamelogic.Ruudukko;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisGrafiikat extends Application {
    private boolean tauko = false;
    private Peli peli;
    private Stage paaIkkuna;
    private Timeline ajastin;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage paaIkkuna) {
        this.paaIkkuna = paaIkkuna;

        paaIkkuna.setTitle("Tetris Limited Edition");
        piirraPaaValikko();
    }

    private void piirraPaaValikko() {
        //musiikki();

        Label otsikko = new Label("TETRIS");
        otsikko.setPrefSize(150, 50);
        otsikko.setStyle("-fx-font: 40 arial;");

        HBox ylaRivi = new HBox();
        ylaRivi.getChildren().add(otsikko);
        ylaRivi.setAlignment(Pos.CENTER);
        BorderPane.setMargin(ylaRivi, new Insets(30, 30, 30, 30));

        //aloittaa uuden pelin
        Button siirryPeliin = new Button("Aloita Peli");
        siirryPeliin.setOnAction(e -> {
            this.peli = new Peli();
            peliIkkuna();
        });
        siirryPeliin.setPrefSize(150, 60);
        siirryPeliin.setStyle("-fx-font-size:25");

        VBox valikko = new VBox();
        valikko.setAlignment(Pos.CENTER);

        valikko.getChildren().add(siirryPeliin);

        BorderPane root = new BorderPane();
        root.setCenter(valikko);
        root.setTop(ylaRivi);


        paaIkkuna.setScene(new Scene(root, 600, 600));
        paaIkkuna.show();
    }

    private void peliIkkuna() {
        //millisekunteina
        final double kierroksenKesto = 1000;
        this.ajastin = new Timeline(new KeyFrame(Duration.millis(kierroksenKesto), tapahtuma -> {

            if (tauko == false) {
                if (peli.onkoPeliKaynnissa()) {
                    peli.seuraavaFrame();

                    piirraPeli();
                }

                if (peli.onkoPeliKaynnissa() == false) {
                    try {
                        ajastin.stop();
                        peliLoppuValikko();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (tauko) {
                try {
                    ajastin.stop();
                    taukoValikko();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                taukoValikko();
            }
        }));

        ajastin.setCycleCount(Timeline.INDEFINITE);
        ajastin.play();
    }

    private void piirraPeli() {

        GridPane ruudukko = teePeliRuudukko();

        String pisteet = String.valueOf(peli.annaPisteet());
        Label pisteTaulu = new Label("PISTEET: " + pisteet);

        Button taukoNappi = new Button("PAUSSI");
        taukoNappi.setFocusTraversable(false);
        BorderPane.setAlignment(taukoNappi, Pos.BOTTOM_RIGHT);
        taukoNappi.setOnAction(tapahtuma -> {
            tauko = true;
            taukoValikko();
        });

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        root.setCenter(ruudukko);
        root.setTop(pisteTaulu);
        root.setRight(taukoNappi);

        paaIkkuna.setScene(new Scene(root, 600, 600));
        paaIkkuna.getScene().setOnKeyPressed(tapahtuma -> {

            switch (tapahtuma.getCode()) {
                case A:
                    peli.otaInputti("vasen");
                    break;
                case S:
                    peli.otaInputti("alas");
                    break;
                case D:
                    peli.otaInputti("oikea");
                    break;
                case Q:
                    peli.otaInputti("vastaPaivaan");
                    break;
                case E:
                    peli.otaInputti("myotaPaivaan");
                    break;
                case SPACE:
                    peli.otaInputti("pudotaAlasAsti");
                    break;
                case ESCAPE:
                    tauko = true;
                    taukoValikko();
                    break;
                default:
                    break;

            }

            piirraPeli();
        });

        paaIkkuna.show();
    }

    private GridPane teePeliRuudukko() {
        GridPane ruudukko = new GridPane();
        ruudukko.setHgap(5);
        ruudukko.setVgap(5);
        ruudukko.setPadding(new Insets(10, 10, 10, 10));

        for (int rivi = 0; rivi < Ruudukko.KORKEUS; rivi++) {
            for (int sarake = 0; sarake < Ruudukko.LEVEYS; sarake++) {
                StackPane ruutu = new StackPane();
                ruutu.setMaxSize(30, 30);

                String vari = peli.annaRuudukko().annaRuudut()[sarake + Ruudukko.REUNA_ALUE][rivi + Ruudukko.SIJOITUS_ALUE].annaVariMerkkijonona();


                ruutu.setStyle("-fx-background-color: " + vari + ";");
                ruudukko.add(ruutu, sarake, rivi);
            }
        }
        for (int i = 0; i < Ruudukko.KORKEUS; i++) {
            ruudukko.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            ruudukko.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        ruudukko.prefHeight(250);
        ruudukko.prefWidth(500);
        ruudukko.setAlignment(Pos.CENTER);

        return ruudukko;
    }

    private void taukoValikko() {
        Button jatkaNappula = new Button("Jatka Pelia");
        jatkaNappula.setOnAction(tapahtuma -> {
            tauko = false;
            peliIkkuna();
        });

        BorderPane root = new BorderPane();
        root.setCenter(jatkaNappula);

        paaIkkuna.setScene(new Scene(root, 600, 600));
        paaIkkuna.show();
    }

    private void peliLoppuValikko() {
        GridPane ruudukko = teePeliRuudukko();

        Button takaisinMenuunNappi = new Button("Palaa Päävalikkoon");
        takaisinMenuunNappi.setOnAction(tapahtuma -> {
            piirraPaaValikko();
        });

        Button uusiPeliNappi = new Button("Uusi Peli");
        uusiPeliNappi.setOnAction(tapahtuma -> {
            this.peli = new Peli();
            peliIkkuna();
        });

        VBox valikko = new VBox();
        valikko.getChildren().addAll(uusiPeliNappi, takaisinMenuunNappi);
        BorderPane.setAlignment(valikko, Pos.CENTER_RIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(ruudukko);
        root.setRight(valikko);

        paaIkkuna.setScene(new Scene(root, 600, 600));
    }

    /*
    private void musiikki() {

        AudioClip note = new AudioClip(this.getClass().getResource("TetrisMusiikki").toString());
        note.play();
    }
    */
}
