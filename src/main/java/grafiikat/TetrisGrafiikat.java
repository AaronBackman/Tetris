package grafiikat;

import gamelogic.*;
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

import java.util.LinkedList;

/**
 * mallintaa tetris pelin graafisen puolen javafx:lla, sisaltaa myos aanet
 */
public class TetrisGrafiikat extends Application {
    private MediaPlayer soitin;
    private Slider musiikkiSlideri;

    /**
     * valitsee naytetaanko haamupalikka pelissa
     */
    private final HaamuKytkinNappi haamuKytkinNappi = new HaamuKytkinNappi();

    /**
     * onko peli kaynnissa vai tauolla
     */
    private boolean tauko;
    private Peli peli;
    private Stage paaIkkuna;
    private Timeline ajastin;

    /**
     * ikkunan taustaVari
     */
    private final Color taustaVari = Color.TEAL;

    /**
     * ikkunan leveys
     */
    private int leveys = 800;

    /**
     * ikkunan korkeus
     */
    private int korkeus = 650;

    /**
     * ohjelman kaynnistyttya kutsuu Application luokan launch metodia
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * ohjelman osa joka suoritetaan kaynnistyksen jalkeen suoraan main metodista
     * @param paaIkkuna ohjelman ikkuna
     */
    @Override
    public void start(Stage paaIkkuna) {
        this.paaIkkuna = paaIkkuna;

        this.soitin = teeSoitin();
        soitin.play();

        this.musiikkiSlideri = teeMusiikkiSlideri();

        paaIkkuna.setTitle("Tetris Limited Edition");
        paaValikko();
    }

    /**
     * piirtaa ohjelman paavalikon
     */
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
            //nayttaa ensin ohjeet ja sitten jatkaa sielta peliin
            naytaOhjeet();
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

    /**
     * nayttaa pelaajalle ennen pelin alkua ohjeet liikkumisesta
     */
    private void naytaOhjeet() {
        VBox liikkumiset = new VBox();

        Label liikkumisnappaimet = new Label(" liikkuminen:");
        liikkumisnappaimet.setPrefSize(300, 50);
        liikkumisnappaimet.setStyle("-fx-font: 30 arial;");

        Label vasemmalle = new Label(" A liikuttaa vasemmalle");
        vasemmalle.setPrefSize(300, 50);
        vasemmalle.setStyle("-fx-font: 20 arial;");

        Label alas = new Label(" S liikuttaa alas");
        alas.setPrefSize(300, 50);
        alas.setStyle("-fx-font: 20 arial;");

        Label oikealle = new Label(" D liikuttaa oikealle");
        oikealle.setPrefSize(300, 50);
        oikealle.setStyle("-fx-font: 20 arial;");

        Label vastapaivaan = new Label(" Q kääntää vastapäivään");
        vastapaivaan.setPrefSize(300, 50);
        vastapaivaan.setStyle("-fx-font: 20 arial;");

        Label myotapaivaan = new Label(" E kääntää myötäpäivään");
        myotapaivaan.setPrefSize(300, 50);
        myotapaivaan.setStyle("-fx-font: 20 arial;");

        Label kokonaanAlas = new Label(" välilyönti tiputtaa kokonaan alas");
        kokonaanAlas.setPrefSize(300, 50);
        kokonaanAlas.setStyle("-fx-font: 20 arial;");


        liikkumiset.getChildren().addAll(liikkumisnappaimet,vasemmalle,alas,oikealle,
                                        vastapaivaan,myotapaivaan,kokonaanAlas);

        Button jatkaPeliin = new Button("Ymmärrän ohjeet");
        jatkaPeliin.setPrefSize(200, 50);
        jatkaPeliin.setStyle("-fx-font: 20 arial;");
        jatkaPeliin.setOnAction(tapahtuma -> {
            this.peli = new Peli();
            suoritaPeli();
        });
        BorderPane.setAlignment(jatkaPeliin, Pos.BOTTOM_RIGHT);

        BorderPane root = new BorderPane();
        root.setLeft(liikkumiset);
        root.setRight(jatkaPeliin);
        root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));

        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
        paaIkkuna.show();
    }

    /**
     * sisaltaa loopin jossa pelin piirtaminen ja logiikkapuoli suoritetaan
     * tietyin aikavalein tai kayttajan syotteesta
     */
    private void suoritaPeli() {
        tauko = false;
        //millisekunteina
        final double kierroksenKesto = 1000;
        this.ajastin = new Timeline(new KeyFrame(Duration.millis(kierroksenKesto), tapahtuma -> {

            if (tauko == false) {
                if (peli.onkoPeliKaynnissa()) {
                    peli.annaPutoavaPalikka().asetaNaytaHaamuPalikka(haamuKytkinNappi.annaTila());
                    peli.seuraavaFrame();

                    peliIkkuna();
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

    /**
     * piirtaa ikkunan johon tulee erilaisia nappeja ja ruudukko joka vastaa pelin loogisen puolen ruudukkoa
     */
    private void peliIkkuna() {

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
        root.setLeft(naytaSeuraavatPalikat());

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

            peliIkkuna();
        });

        paaIkkuna.show();
    }

    /**
     *mallintaa pelin ruudukon graafisesti
     */
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

    /**
     * mallintaa seuraavaksi putoavat palikat graafisesti
     * @return seuraavat palikat pystysuoraan omissa gridpane ruudukoissaan
     */
    private VBox naytaSeuraavatPalikat() {
        LinkedList<Palikka> seuraavatPalikat = peli.annaSeuraavatPalikat();

        VBox palikat = new VBox();

        for(Palikka palikka : seuraavatPalikat) {
            GridPane palikkaRuudukko = new GridPane();

            //palikan pisimman sivun pituus, esim L palikka: pituus = 3
            int palikanPituus = palikka.annaPalikanMuoto().length;
            //suurimman palikan (I palikka) suurimman sivun pituus
            final int maxPalikanPituus = 4;
            int ruudunKoko = 20;

            for (int rivi = 0; rivi < maxPalikanPituus; rivi++) {
                for (int sarake = 0; sarake < maxPalikanPituus; sarake++) {
                    StackPane ruutu = new StackPane();
                    ruutu.setPrefSize(ruudunKoko, ruudunKoko);
                    ruutu.setBorder(new Border(new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));

                    String vari;
                    //jos palikka on pienempi kuin maksimialue, laitetaan tyhja palikka paikalle
                    if(sarake >= palikanPituus || rivi >= palikanPituus) {
                        //tyhjan palikan vari
                        vari = new RuutuTehdas().teeTyhjaRuutu().annaVariMerkkijonona();
                    }
                    else {
                        vari = palikka.annaPalikanMuoto()[sarake][rivi].annaVariMerkkijonona();
                    }

                    ruutu.setStyle("-fx-background-color: " + vari + ";");
                    palikkaRuudukko.add(ruutu, sarake, rivi);
                }
            }
            for (int i = 0; i < maxPalikanPituus; i++) {
                palikkaRuudukko.getColumnConstraints().add(new ColumnConstraints(ruudunKoko, ruudunKoko, ruudunKoko, Priority.ALWAYS, HPos.CENTER, true));
                palikkaRuudukko.getRowConstraints().add(new RowConstraints(ruudunKoko, ruudunKoko, ruudunKoko, Priority.ALWAYS, VPos.CENTER, true));
            }
            //palikan maksimipituus 4 ruutua (I palikka)
            palikkaRuudukko.prefHeight(ruudunKoko * palikanPituus);
            palikkaRuudukko.prefWidth(ruudunKoko * palikanPituus);
            palikkaRuudukko.setAlignment(Pos.CENTER);

            palikat.getChildren().add(palikkaRuudukko);
        }
        return palikat;
    }

    /**
     * tekee taukovalikon, johon paasee pelista
     * sielta voi palata peliin, muuttaa asetuksia tai voi lopettaa pelin
     */
    private void taukoValikko() {
        Button jatkaNappi = new Button("Jatka Peliä");
        jatkaNappi.setOnAction(tapahtuma -> {
            tauko = false;
            suoritaPeli();
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
            suoritaPeli();
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

    /**
     * mallintaa graafisesti asetusvalikon
     * @param onkoPelissa jos on pelissa tulee vaihtoehto palata peliin
     */
    private void asetuksetIkkuna(boolean onkoPelissa) {
        VBox valikko = new VBox();
        valikko.setAlignment(Pos.TOP_CENTER);

        TextField ikkunanLeveysNappi = new TextField();
        ikkunanLeveysNappi.setPromptText("Ikkunan Leveys: " + leveys);
        ikkunanLeveysNappi.setFocusTraversable(false);
        ikkunanLeveysNappi.setMaxSize(250, 60);
        ikkunanLeveysNappi.setStyle("-fx-font-size:20");

        TextField ikkunanKorkeusNappi = new TextField();
        ikkunanKorkeusNappi.setPromptText("Ikkunan Korkeus: " + korkeus);
        ikkunanKorkeusNappi.setFocusTraversable(false);
        ikkunanKorkeusNappi.setMaxSize(250, 60);
        ikkunanKorkeusNappi.setStyle("-fx-font-size:20");

        valikko.getChildren().addAll(ikkunanLeveysNappi, ikkunanKorkeusNappi, haamuKytkinNappi);

        Button muutostenVahvistusNappi = new Button("Vahvista Muutokset");
        muutostenVahvistusNappi.setPrefSize(250, 60);
        muutostenVahvistusNappi.setStyle("-fx-font-size:20");
        muutostenVahvistusNappi.setOnAction(tapahtuma -> {
            if(ikkunanLeveysNappi.getText().equals("") == false){
                this.leveys = Integer.parseInt(ikkunanLeveysNappi.getText());
            }
            if(ikkunanKorkeusNappi.getText().equals("") == false)
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
            takaisinPaaValikkoon.setPrefSize(250, 60);
            takaisinPaaValikkoon.setStyle("-fx-font-size:20");
            valikko.getChildren().add(takaisinPaaValikkoon);
        }

        //oli tauko valikossa
        else if(peli.onkoPeliKaynnissa()) {
            Button takaisinPeliinNappi = new Button("Poistu Asetuksista");
            takaisinPeliinNappi.setOnAction(tapahtuma -> {
                taukoValikko();
            });
            takaisinPeliinNappi.setPrefSize(250, 60);
            takaisinPeliinNappi.setStyle("-fx-font-size:20");
            valikko.getChildren().add(takaisinPeliinNappi);
        }
        //oli pelissa, mutta peli on havitty
        else {
            Button takaisinPeliinNappi = new Button("Poistu Asetuksista");
            takaisinPeliinNappi.setOnAction(tapahtuma -> {
                peliLoppuValikko();
            });
            takaisinPeliinNappi.setPrefSize(250, 60);
            takaisinPeliinNappi.setStyle("-fx-font-size:20");
            valikko.getChildren().add(takaisinPeliinNappi);
        }

         BorderPane root = new BorderPane();
         root.setBackground(new Background(new BackgroundFill(taustaVari, CornerRadii.EMPTY, Insets.EMPTY)));

         root.setCenter(valikko);
         root.setRight(muutostenVahvistusNappi);

        paaIkkuna.setScene(new Scene(root, leveys, korkeus));
        paaIkkuna.show();
    }

    /**
     * valikko joka naytetaan kun peli on havitty
     */
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
            suoritaPeli();
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

    /**
     * sammuttaa koko sovelluksen
     */
    private void sammutaOhjelma() {
        paaIkkuna.close();
    }

    /**
     * tekee saatimen joka muuttaa aanenvoimakkuutta
     * @return aanenvoimakkuus saadin
     */
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

    /**
     * mahdollistaa tetriksen teemamusiikin soittamisen
     * @return olio joka mahdollistaa musiikin kuulumisen
     */
    private MediaPlayer teeSoitin() {

        Media aani = new Media(this.getClass().getResource("/musiikki/Tetris_theme.mp3").toString());
        soitin = new MediaPlayer(aani);
        soitin.setOnEndOfMedia(() -> soitin.seek(Duration.ZERO));
        soitin.setVolume(0.5);
        return soitin;
    }
}
