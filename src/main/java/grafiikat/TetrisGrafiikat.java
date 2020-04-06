package grafiikat;

import gamelogic.Peli;
import gamelogic.Ruudukko;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class TetrisGrafiikat extends Application {
    private boolean tauko;
    private Peli peli;
    private Stage paaIkkuna;
    private Timeline ajastin;
    private final Color taustaVari = Color.TEAL;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage paaIkkuna) {
        this.paaIkkuna = paaIkkuna;

        soitaMusiikkia();

        paaIkkuna.setTitle("Tetris Limited Edition");
        paaValikko();
    }

    private void paaValikko() {
        Label otsikko = new Label("TETRIS");
        otsikko.setPrefSize(200, 50);
        otsikko.setStyle("-fx-font: 40 arial;");

        HBox ylaRivi = new HBox();
        ylaRivi.getChildren().add(otsikko);
        ylaRivi.setAlignment(Pos.CENTER);
        BorderPane.setMargin(ylaRivi, new Insets(30, 30, 30, 30));

        //aloittaa uuden pelin
        Button siirryPeliinNappi = new Button("Aloita Peli");
        siirryPeliinNappi.setOnAction(e -> {
            this.peli = new Peli();
            peliIkkuna();
        });
        siirryPeliinNappi.setPrefSize(200, 60);
        siirryPeliinNappi.setStyle("-fx-font-size:25");

        Button sammutaOhjelmaNappi = new Button("Sammuta Peli");
        sammutaOhjelmaNappi.setOnAction(tapahtuma -> sammutaOhjelma());
        sammutaOhjelmaNappi.setPrefSize(200, 60);
        sammutaOhjelmaNappi.setStyle("-fx-font-size:25");

        VBox valikko = new VBox();
        valikko.setAlignment(Pos.CENTER);

        valikko.getChildren().addAll(siirryPeliinNappi, sammutaOhjelmaNappi);

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(valikko);
        root.setTop(ylaRivi);


        paaIkkuna.setScene(new Scene(root, 800, 600));
        paaIkkuna.show();
    }

    private void peliIkkuna() {
        tauko = false;
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
        pisteTaulu.setPrefSize(400, 50);
        pisteTaulu.setStyle("-fx-font-size:20");

        Button taukoNappi = new Button("PAUSSI");
        taukoNappi.setPrefSize(120, 50);
        taukoNappi.setStyle("-fx-font-size:20");
        taukoNappi.setFocusTraversable(false);
        BorderPane.setAlignment(taukoNappi, Pos.BOTTOM_RIGHT);
        taukoNappi.setOnAction(tapahtuma -> {
            tauko = true;
            taukoValikko();
        });

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));

        root.setCenter(ruudukko);
        root.setTop(pisteTaulu);
        root.setRight(taukoNappi);

        paaIkkuna.setScene(new Scene(root, 800, 600));
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

        final int ruudunKoko = 20;

        for (int rivi = 0; rivi < Ruudukko.KORKEUS; rivi++) {
            for (int sarake = 0; sarake < Ruudukko.LEVEYS; sarake++) {
                StackPane ruutu = new StackPane();
                ruutu.setPrefSize(ruudunKoko, ruudunKoko);
                ruutu.setBorder(new Border(new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));

                String vari = peli.annaRuudukko().annaRuudut()[sarake + Ruudukko.REUNA_ALUE][rivi + Ruudukko.SIJOITUS_ALUE].annaVariMerkkijonona();


                ruutu.setStyle("-fx-background-color: " + vari + ";");
                ruudukko.add(ruutu, sarake, rivi);
            }
        }
        for (int i = 0; i < Ruudukko.KORKEUS; i++) {
            ruudukko.getColumnConstraints().add(new ColumnConstraints(ruudunKoko, ruudunKoko, ruudunKoko, Priority.ALWAYS, HPos.CENTER, true));
            ruudukko.getRowConstraints().add(new RowConstraints(ruudunKoko, ruudunKoko, ruudunKoko, Priority.ALWAYS, VPos.CENTER, true));
        }
        ruudukko.prefHeight(ruudunKoko * Ruudukko.KORKEUS);
        ruudukko.prefWidth(ruudunKoko * Ruudukko.LEVEYS);
        ruudukko.setAlignment(Pos.CENTER);

        return ruudukko;
    }

    private void taukoValikko() {
        Button jatkaNappula = new Button("Jatka Peliä");
        jatkaNappula.setOnAction(tapahtuma -> {
            tauko = false;
            peliIkkuna();
        });
        jatkaNappula.setPrefSize(200, 60);
        jatkaNappula.setStyle("-fx-font-size:25");

        Button takaisinMenuunNappi = new Button("Päävalikko");
        takaisinMenuunNappi.setOnAction(tapahtuma -> {
            paaValikko();
        });
        takaisinMenuunNappi.setPrefSize(200, 60);
        takaisinMenuunNappi.setStyle("-fx-font-size:25");

        Button uusiPeliNappi = new Button("Uusi Peli");
        uusiPeliNappi.setOnAction(tapahtuma -> {
            this.peli = new Peli();
            peliIkkuna();
        });
        uusiPeliNappi.setPrefSize(200, 60);
        uusiPeliNappi.setStyle("-fx-font-size:25");

        Button sammutaOhjelmaNappi = new Button("Poistu Pelistä");
        sammutaOhjelmaNappi.setOnAction(tapahtuma -> sammutaOhjelma());
        sammutaOhjelmaNappi.setPrefSize(200, 60);
        sammutaOhjelmaNappi.setStyle("-fx-font-size:25");

        VBox valikko = new VBox();
        valikko.setAlignment(Pos.CENTER);
        valikko.getChildren().addAll(jatkaNappula, takaisinMenuunNappi, uusiPeliNappi, sammutaOhjelmaNappi);

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(valikko);

        paaIkkuna.setScene(new Scene(root, 800, 600));
        paaIkkuna.show();
    }

    private void peliLoppuValikko() {
        GridPane ruudukko = teePeliRuudukko();

        String pisteet = String.valueOf(peli.annaPisteet());
        Label pisteTaulu = new Label("PISTEET: " + pisteet);
        pisteTaulu.setPrefSize(400, 50);
        pisteTaulu.setStyle("-fx-font-size:20");

        Button takaisinMenuunNappi = new Button("Päävalikko");
        takaisinMenuunNappi.setOnAction(tapahtuma -> {
            paaValikko();
        });
        takaisinMenuunNappi.setPrefSize(200, 60);
        takaisinMenuunNappi.setStyle("-fx-font-size:25");

        Button uusiPeliNappi = new Button("Uusi Peli");
        uusiPeliNappi.setOnAction(tapahtuma -> {
            this.peli = new Peli();
            peliIkkuna();
        });
        uusiPeliNappi.setPrefSize(200, 60);
        uusiPeliNappi.setStyle("-fx-font-size:25");

        Button sammutaOhjelmaNappi = new Button("Poistu Pelistä");
        sammutaOhjelmaNappi.setOnAction(tapahtuma -> sammutaOhjelma());
        sammutaOhjelmaNappi.setPrefSize(200, 60);
        sammutaOhjelmaNappi.setStyle("-fx-font-size:25");

        VBox valikko = new VBox();
        valikko.getChildren().addAll(uusiPeliNappi, takaisinMenuunNappi, sammutaOhjelmaNappi);
        valikko.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setLeft(ruudukko);
        root.setCenter(valikko);
        root.setTop(pisteTaulu);

        paaIkkuna.setScene(new Scene(root, 800, 600));
    }

    private void sammutaOhjelma() {
        paaIkkuna.close();
    }

    //on tassa jotta garbage collector ei poistaisi sita
    MediaPlayer soitin;
    //soittaa tetris teema musiikkia
    public void soitaMusiikkia() {

        String suhteellinenPolku = "src/main/resources/musiikki/Tetris_theme.mp3";
        Media aani = new Media(new File(suhteellinenPolku).toURI().toString());
        soitin = new MediaPlayer(aani);
        soitin.setOnEndOfMedia(new Runnable() {
            public void run() {
                soitin.seek(Duration.ZERO);
            }
        });
        soitin.play();
    }
}
