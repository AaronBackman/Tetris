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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class TetrisGrafiikat extends Application {
    //asteikolla 0-100
    private MediaPlayer soitin;
    private Slider musiikkiSlideri;
    private boolean tauko;
    private Peli peli;
    private Stage paaIkkuna;
    private Timeline ajastin;
    private final Color taustaVari = Color.TEAL;
    private int leveys = 800;
    private int korkeus = 650;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage paaIkkuna) {
        this.paaIkkuna = paaIkkuna;

        this.soitin = teeSoitin();
        soitin.play();

        this.musiikkiSlideri = teeMusiikkiSlideri();

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
        siirryPeliinNappi.setOnAction(tapahtuma -> {
            this.peli = new Peli();
            peliIkkuna();
        });
        siirryPeliinNappi.setPrefSize(200, 60);
        siirryPeliinNappi.setStyle("-fx-font-size:25");

        Button asetuksetNappi = new Button("Asetukset");
        asetuksetNappi.setOnAction(tapahtuma -> {
            asetuksetIkkuna(false);
        });
        asetuksetNappi.setPrefSize(200, 60);
        asetuksetNappi.setStyle("-fx-font-size:25");

        Button sammutaOhjelmaNappi = new Button("Sammuta Peli");
        sammutaOhjelmaNappi.setOnAction(tapahtuma -> sammutaOhjelma());
        sammutaOhjelmaNappi.setPrefSize(200, 60);
        sammutaOhjelmaNappi.setStyle("-fx-font-size:25");

        VBox valikko = new VBox();
        valikko.setAlignment(Pos.CENTER);

        valikko.getChildren().addAll(siirryPeliinNappi, asetuksetNappi, sammutaOhjelmaNappi);

        BorderPane root = new BorderPane();
        root.setBottom(musiikkiSlideri);
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(valikko);
        root.setTop(ylaRivi);


        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
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
        BorderPane.setAlignment(taukoNappi, Pos.TOP_RIGHT);
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
        root.setBottom(musiikkiSlideri);

        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
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
        Button jatkaNappi = new Button("Jatka Peliä");
        jatkaNappi.setOnAction(tapahtuma -> {
            tauko = false;
            peliIkkuna();
        });
        jatkaNappi.setPrefSize(200, 60);
        jatkaNappi.setStyle("-fx-font-size:25");

        Button asetuksetNappi = new Button("Asetukset");
        asetuksetNappi.setOnAction(tapahtuma -> {
            asetuksetIkkuna(true);
        });
        asetuksetNappi.setPrefSize(200, 60);
        asetuksetNappi.setStyle("-fx-font-size:25");

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
        valikko.getChildren().addAll(jatkaNappi, asetuksetNappi, takaisinMenuunNappi, uusiPeliNappi, sammutaOhjelmaNappi);

        BorderPane root = new BorderPane();
        root.setBottom(musiikkiSlideri);
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(valikko);

        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
        paaIkkuna.show();
    }

    private void asetuksetIkkuna(boolean onkoPelissa) {
        VBox valikko = new VBox();

        TextField ikkunanLeveysNappi = new TextField();
        ikkunanLeveysNappi.setPromptText("Ikkunan Leveys: " + leveys);
        ikkunanLeveysNappi.setFocusTraversable(false);
        ikkunanLeveysNappi.setPrefSize(200, 60);
        ikkunanLeveysNappi.setStyle("-fx-font-size:25");

        TextField ikkunanKorkeusNappi = new TextField();
        ikkunanKorkeusNappi.setPromptText("Ikkunan Korkeus: " + korkeus);
        ikkunanKorkeusNappi.setFocusTraversable(false);
        ikkunanKorkeusNappi.setPrefSize(200, 60);
        ikkunanKorkeusNappi.setStyle("-fx-font-size:25");

        valikko.getChildren().addAll(ikkunanLeveysNappi, ikkunanKorkeusNappi);

        Button muutostenVahvistusNappi = new Button("Vahvista Muutokset");
        muutostenVahvistusNappi.setPrefSize(200, 60);
        muutostenVahvistusNappi.setStyle("-fx-font-size:25");
        muutostenVahvistusNappi.setOnAction(tapahtuma -> {
            this.leveys = Integer.parseInt(ikkunanLeveysNappi.getText());
            this.korkeus = Integer.parseInt(ikkunanKorkeusNappi.getText());

            //piirtaa asetusikkunan uuden kokoisena
            asetuksetIkkuna(onkoPelissa);
        });
        BorderPane.setAlignment(muutostenVahvistusNappi, Pos.BOTTOM_RIGHT);

        if(onkoPelissa == false) {
            Button takaisinPaaValikkoon = new Button("Poistu Asetuksista");
            takaisinPaaValikkoon.setOnAction(tapahtuma -> {
                paaValikko();
            });
            takaisinPaaValikkoon.setPrefSize(200, 60);
            takaisinPaaValikkoon.setStyle("-fx-font-size:25");
            valikko.getChildren().add(takaisinPaaValikkoon);
        }

        //oli tauko valikossa
        else if(peli.onkoPeliKaynnissa()) {
            Button takaisinPeliinNappi = new Button("Poistu Asetuksista");
            takaisinPeliinNappi.setOnAction(tapahtuma -> {
                taukoValikko();
            });
            takaisinPeliinNappi.setPrefSize(200, 60);
            takaisinPeliinNappi.setStyle("-fx-font-size:25");
            valikko.getChildren().add(takaisinPeliinNappi);
        }
        //oli pelissa, mutta peli on havitty
        else {
            Button takaisinPeliinNappi = new Button("Poistu Asetuksista");
            takaisinPeliinNappi.setOnAction(tapahtuma -> {
                peliLoppuValikko();
            });
            takaisinPeliinNappi.setPrefSize(200, 60);
            takaisinPeliinNappi.setStyle("-fx-font-size:25");
            valikko.getChildren().add(takaisinPeliinNappi);
        }

         BorderPane root = new BorderPane();
         root.setCenter(valikko);
         root.setRight(muutostenVahvistusNappi);

        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
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
        root.setBottom(musiikkiSlideri);

        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
    }

    private Slider teeMusiikkiSlideri() {
        Slider slideri = new Slider();
        slideri.setMin(0);
        slideri.setMax(100);
        //slideri asteikolla 0-100, aanenvoimakkuus 0-1 -> kerrotaan 100:lla
        slideri.setValue(soitin.getVolume() * 100);
        slideri.setShowTickLabels(true);
        slideri.setShowTickMarks(true);
        slideri.setMajorTickUnit(50);
        slideri.setMinorTickCount(25);
        slideri.setBlockIncrement(25);
        slideri.valueProperty().addListener(e -> {
            //aanenvoimakkuus asteikolla 0-1, slideri 0-100 -> jaetaan 100:lla
            soitin.setVolume(slideri.getValue() / 100.0);
        });

        BorderPane.setAlignment(slideri, Pos.BOTTOM_RIGHT);
        slideri.setMaxWidth(150);

        return slideri;
    }

    private void sammutaOhjelma() {
        paaIkkuna.close();
    }

    //on tassa jotta garbage collector ei poistaisi sita
    //soittaa tetris teema musiikkia
    public MediaPlayer teeSoitin() {

        String suhteellinenPolku = "src/main/resources/musiikki/Tetris_theme.mp3";
        Media aani = new Media(new File(suhteellinenPolku).toURI().toString());
        soitin = new MediaPlayer(aani);
        soitin.setOnEndOfMedia(new Runnable() {
            public void run() {
                soitin.seek(Duration.ZERO);
            }
        });
        soitin.setVolume(0.5);
        return soitin;
    }
}
