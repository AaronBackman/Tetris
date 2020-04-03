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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TetrisGrafiikat extends Application {
    private static boolean tauko = false;
    private static Peli peli = new Peli();

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

        //aloittaa uuden pelin
        Button siirryPeliin = new Button("Aloita Peli");
        siirryPeliin.setOnAction(e -> {
            peli = new Peli();
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
        //millisekunteina
        final double kierroksenKesto = 1000;
        Timeline ajastin = new Timeline(new KeyFrame(Duration.millis(kierroksenKesto), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tauko == false) {
                    if (peli.onkoPeliKaynnissa()) {
                        peli.seuraavaFrame();

                        piirraPeli(ikkuna);
                    }

                    if (peli.onkoPeliKaynnissa() == false) {
                        try {
                            stop();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    taukoValikko(ikkuna);
                }
            }
        }));
        ajastin.setCycleCount(Timeline.INDEFINITE);
        ajastin.play();
    }

    private void piirraPeli(Stage ikkuna) {

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

        String pisteet = String.valueOf(peli.annaPisteet());
        Label pisteTaulu = new Label("PISTEET: " + pisteet);

        Button taukoNappi = new Button("PAUSSI");
        taukoNappi.setFocusTraversable(false);
        BorderPane.setAlignment(taukoNappi, Pos.BOTTOM_RIGHT);
        taukoNappi.setOnAction(e -> {
            tauko = true;
            taukoValikko(ikkuna);
        });

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        root.setCenter(ruudukko);
        root.setTop(pisteTaulu);
        root.setRight(taukoNappi);

        ikkuna.setScene(new Scene(root, 600, 600));
        ikkuna.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent tapahtuma) {
                switch(tapahtuma.getCode()) {
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
                    default:
                        break;

                }

                piirraPeli(ikkuna);
            }
        });

        ikkuna.show();
    }

    private void taukoValikko(Stage ikkuna) {
        Button jatkaNappula = new Button("Jatka Pelia");
        jatkaNappula.setOnAction(e -> {
            tauko = false;
        });

        BorderPane root = new BorderPane();
        root.setCenter(jatkaNappula);

        ikkuna.setScene(new Scene(root, 600, 600));
        ikkuna.show();
    }
}
