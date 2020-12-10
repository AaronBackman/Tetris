package graphics;

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
 * models Tetris game graphically with JavaFx, includes sound
 */
public class TetrisGraphics extends Application {
    private MediaPlayer musicPlayer;
    private Slider musicSlider;

    /**
     * whether ghostblock is shown or not
     */
    private final GhostSwitch ghostSwitch = new GhostSwitch();

    /**
     * is game paused
     */
    private boolean pause;
    private Game game;
    private Stage mainWindow;
    private Timeline timer;

    /**
     * window background color
     */
    private final Color backgroundColor = Color.TEAL;

    /**
     * window width
     */
    private int width = 800;

    /**
     * window height
     */
    private int height = 650;

    /**
     * entry point
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * run after program launched from main method
     * @param mainWindow program window
     */
    @Override
    public void start(Stage mainWindow) {
        this.mainWindow = mainWindow;

        this.musicPlayer = createMusicPlayer();
        musicPlayer.play();

        this.musicSlider = createMusicSlider();

        mainWindow.setTitle("Tetris Limited Edition");
        mainMenu();
    }

    /**
     * draws main menu
     */
    private void mainMenu() {
        Label header = new Label("TETRIS");
        header.setPrefSize(200, 50);
        header.setStyle("-fx-font: 40 arial;");

        HBox upperRow = new HBox();
        upperRow.getChildren().add(header);
        upperRow.setAlignment(Pos.CENTER);
        BorderPane.setMargin(upperRow, new Insets(30, 30, 30, 30));

        //starts a new game
        Button moveToGameButton = new Button("Start Game");
        moveToGameButton.setOnAction(e -> {
            //shows guide and then continues to the game
            showGuide();
        });
        moveToGameButton.setPrefSize(200, 60);
        moveToGameButton.setStyle("-fx-font-size:25");

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> {
            settingsWindow(false);
        });
        settingsButton.setPrefSize(200, 60);
        settingsButton.setStyle("-fx-font-size:25");

        Button exitProgramButton = new Button("Exit Program");
        exitProgramButton.setOnAction(e -> closeProgram());
        exitProgramButton.setPrefSize(200, 60);
        exitProgramButton.setStyle("-fx-font-size:25");

        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(moveToGameButton, settingsButton, exitProgramButton);

        BorderPane root = new BorderPane();
        root.setBottom(musicSlider);
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(menu);
        root.setTop(upperRow);


        mainWindow.setScene(new Scene(root, width, height));
        mainWindow.show();
    }

    /**
     * shows guide to the player
     */
    private void showGuide() {
        VBox moving = new VBox();

        Label movingButtons = new Label(" moving:");
        movingButtons.setPrefSize(300, 50);
        movingButtons.setStyle("-fx-font: 30 arial;");

        Label left = new Label(" A moves to left");
        left.setPrefSize(300, 50);
        left.setStyle("-fx-font: 20 arial;");

        Label down = new Label(" S moves down");
        down.setPrefSize(300, 50);
        down.setStyle("-fx-font: 20 arial;");

        Label right = new Label(" D moves right");
        right.setPrefSize(300, 50);
        right.setStyle("-fx-font: 20 arial;");

        Label counterClockwise = new Label(" Q turns counterclockwise");
        counterClockwise.setPrefSize(300, 50);
        counterClockwise.setStyle("-fx-font: 20 arial;");

        Label clockwise = new Label(" E turns counterclockwise");
        clockwise.setPrefSize(300, 50);
        clockwise.setStyle("-fx-font: 20 arial;");

        Label toBottom = new Label(" spacebar drops completely to the bottom");
        toBottom.setPrefSize(300, 50);
        toBottom.setStyle("-fx-font: 20 arial;");


        moving.getChildren().addAll(movingButtons,left,down,right,
                                        counterClockwise,clockwise,toBottom);

        Button continueToGame = new Button("I understand tutorial");
        continueToGame.setPrefSize(200, 50);
        continueToGame.setStyle("-fx-font: 20 arial;");
        continueToGame.setOnAction(e -> {
            this.game = new Game();
            runGame();
        });
        BorderPane.setAlignment(continueToGame, Pos.BOTTOM_RIGHT);

        BorderPane root = new BorderPane();
        root.setLeft(moving);
        root.setRight(continueToGame);
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

        mainWindow.setScene(new Scene(root, width, height));
        mainWindow.show();
    }

    /**
     * includes loop where drawing and running the game is done
     * within certain intervals or after user input
     */
    private void runGame() {
        pause = false;
        //milliseconds
        final double turnLength = 1000;
        this.timer = new Timeline(new KeyFrame(Duration.millis(turnLength), e -> {

            if (pause == false) {
                if (game.isGameOn()) {
                    game.getFallingBlock().setShowGhostBlock(ghostSwitch.getState());
                    game.nextFrame();

                    gameWindow();
                }

                if (game.isGameOn() == false) {
                    try {
                        timer.stop();
                        gameOverMenu();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (pause) {
                try {
                    timer.stop();
                    pauseMenu();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                pauseMenu();
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    /**
     * draws window that has buttons and game grid
     */
    private void gameWindow() {

        GridPane grid = createGameGrid();

        String score = String.valueOf(game.getScore());
        Label scoreBoard = new Label("SCORE: " + score);
        scoreBoard.setPrefSize(400, 50);
        scoreBoard.setStyle("-fx-font-size:20");

        Button pauseButton = new Button("PAUSE");
        pauseButton.setPrefSize(120, 50);
        pauseButton.setStyle("-fx-font-size:20");
        pauseButton.setFocusTraversable(false);
        BorderPane.setAlignment(pauseButton, Pos.TOP_RIGHT);
        pauseButton.setOnAction(e -> {
            pause = true;
            pauseMenu();
        });

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

        root.setCenter(grid);
        root.setTop(scoreBoard);
        root.setRight(pauseButton);
        root.setBottom(musicSlider);
        root.setLeft(showNextBlocks());

        mainWindow.setScene(new Scene(root, width, height));
        mainWindow.getScene().setOnKeyPressed(e -> {

            switch (e.getCode()) {
                case A:
                    game.takeInput("left");
                    break;
                case S:
                    game.takeInput("down");
                    break;
                case D:
                    game.takeInput("right");
                    break;
                case Q:
                    game.takeInput("counterClockwise");
                    break;
                case E:
                    game.takeInput("clockwise");
                    break;
                case SPACE:
                    game.takeInput("dropToBottom");
                    break;
                case ESCAPE:
                    pause = true;
                    pauseMenu();
                    break;
                default:
                    break;

            }

            gameWindow();
        });

        mainWindow.show();
    }

    /**
     * models game graphically
     */
    private GridPane createGameGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 10, 10, 10));

        final int gridSize = 20;

        for (int row = 0; row < Grid.HEIGHT; row++) {
            for (int column = 0; column < Grid.WIDTH; column++) {
                StackPane square = new StackPane();
                square.setPrefSize(gridSize, gridSize);
                square.setBorder(new Border(new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));

                String color = game.getGrid().getSquares()[column + Grid.BORDER_WIDTH][row + Grid.TOP_AREA].getColorAsString();


                square.setStyle("-fx-background-color: " + color + ";");
                grid.add(square, column, row);
            }
        }
        for (int i = 0; i < Grid.HEIGHT; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(gridSize, gridSize, gridSize, Priority.ALWAYS, HPos.CENTER, true));
            grid.getRowConstraints().add(new RowConstraints(gridSize, gridSize, gridSize, Priority.ALWAYS, VPos.CENTER, true));
        }
        grid.prefHeight(gridSize * Grid.HEIGHT);
        grid.prefWidth(gridSize * Grid.WIDTH);
        grid.setAlignment(Pos.CENTER);

        return grid;
    }

    /**
     * models next blocks graphically
     * @return next blocks upright in their own grids
     */
    private VBox showNextBlocks() {
        LinkedList<Block> nextBlocks = game.getNextBlocks();

        VBox blocks = new VBox();

        for(Block block : nextBlocks) {
            GridPane blockGrid = new GridPane();

            //height of blocks tallest side, eg. L block: height = 3
            int blockLength = block.getShape().length;
            //largest block (I block) height (tallest side)
            final int maxBlockLength = 4;
            int gridSize = 20;

            for (int row = 0; row < maxBlockLength; row++) {
                for (int column = 0; column < maxBlockLength; column++) {
                    StackPane square = new StackPane();
                    square.setPrefSize(gridSize, gridSize);
                    square.setBorder(new Border(new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY, new BorderWidths(2, 2, 2, 2))));

                    String color;
                    //if block is smaller than maximum area, put an empty block
                    if(column >= blockLength || row >= blockLength) {
                        //empty block color
                        color = new SquareFactory().createEmptySquare().getColorAsString();
                    }
                    else {
                        color = block.getShape()[column][row].getColorAsString();
                    }

                    square.setStyle("-fx-background-color: " + color + ";");
                    blockGrid.add(square, column, row);
                }
            }
            for (int i = 0; i < maxBlockLength; i++) {
                blockGrid.getColumnConstraints().add(new ColumnConstraints(gridSize, gridSize, gridSize, Priority.ALWAYS, HPos.CENTER, true));
                blockGrid.getRowConstraints().add(new RowConstraints(gridSize, gridSize, gridSize, Priority.ALWAYS, VPos.CENTER, true));
            }
            //any block max height 4 squares (I block)
            blockGrid.prefHeight(gridSize * blockLength);
            blockGrid.prefWidth(gridSize * blockLength);
            blockGrid.setAlignment(Pos.CENTER);

            blocks.getChildren().add(blockGrid);
        }
        return blocks;
    }

    /**
     * creates pause menu (from game)
     */
    private void pauseMenu() {
        Button continueButton = new Button("Continue Game");
        continueButton.setOnAction(e -> {
            pause = false;
            runGame();
        });
        continueButton.setPrefSize(200, 60);
        continueButton.setStyle("-fx-font-size:25");

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> {
            settingsWindow(true);
        });
        settingsButton.setPrefSize(200, 60);
        settingsButton.setStyle("-fx-font-size:25");

        Button backToMenuButton = new Button("Main Menu");
        backToMenuButton.setOnAction(e -> {
            mainMenu();
        });
        backToMenuButton.setPrefSize(200, 60);
        backToMenuButton.setStyle("-fx-font-size:25");

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> {
            this.game = new Game();
            runGame();
        });
        newGameButton.setPrefSize(200, 60);
        newGameButton.setStyle("-fx-font-size:25");

        Button closeProgramButton = new Button("Close Program");
        closeProgramButton.setOnAction(e -> closeProgram());
        closeProgramButton.setPrefSize(200, 60);
        closeProgramButton.setStyle("-fx-font-size:25");

        VBox menu = new VBox();
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(continueButton, settingsButton, backToMenuButton, newGameButton, closeProgramButton);

        BorderPane root = new BorderPane();
        root.setBottom(musicSlider);
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setCenter(menu);

        mainWindow.setScene(new Scene(root, width, height));
        mainWindow.show();
    }

    /**
     * models settings menu
     * @param gameIsOn if game is on you can return to it
     */
    private void settingsWindow(boolean gameIsOn) {
        VBox menu = new VBox();
        menu.setAlignment(Pos.TOP_CENTER);

        TextField windowWidthButton = new TextField();
        windowWidthButton.setPromptText("Window Width: " + width);
        windowWidthButton.setFocusTraversable(false);
        windowWidthButton.setMaxSize(250, 60);
        windowWidthButton.setStyle("-fx-font-size:20");

        TextField windowHeightButton = new TextField();
        windowHeightButton.setPromptText("Window Height: " + height);
        windowHeightButton.setFocusTraversable(false);
        windowHeightButton.setMaxSize(250, 60);
        windowHeightButton.setStyle("-fx-font-size:20");

        menu.getChildren().addAll(windowWidthButton, windowHeightButton, ghostSwitch);

        Button confirmButton = new Button("Confirm Changes");
        confirmButton.setPrefSize(250, 60);
        confirmButton.setStyle("-fx-font-size:20");
        confirmButton.setOnAction(e -> {
            if(windowWidthButton.getText().equals("") == false){
                this.width = Integer.parseInt(windowWidthButton.getText());
            }
            if(windowHeightButton.getText().equals("") == false)
            this.height = Integer.parseInt(windowHeightButton.getText());

            //re-draws the settings menu with new size
            settingsWindow(gameIsOn);
        });
        BorderPane.setAlignment(confirmButton, Pos.BOTTOM_RIGHT);

        if(gameIsOn == false) {
            Button backToMainMenuButton = new Button("Exit Settings");
            backToMainMenuButton.setOnAction(tapahtuma -> {
                mainMenu();
            });
            backToMainMenuButton.setPrefSize(250, 60);
            backToMainMenuButton.setStyle("-fx-font-size:20");
            menu.getChildren().add(backToMainMenuButton);
        }

        //was in pause menu
        else if(game.isGameOn()) {
            Button backToGame = new Button("Return to the Game");
            backToGame.setOnAction(e -> {
                pauseMenu();
            });
            backToGame.setPrefSize(250, 60);
            backToGame.setStyle("-fx-font-size:20");
            menu.getChildren().add(backToGame);
        }
        //was in game, game was lost
        else {
            Button backToGameButton = new Button("Return to the Game");
            backToGameButton.setOnAction(e -> {
                gameOverMenu();
            });
            backToGameButton.setPrefSize(250, 60);
            backToGameButton.setStyle("-fx-font-size:20");
            menu.getChildren().add(backToGameButton);
        }

         BorderPane root = new BorderPane();
         root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));

         root.setCenter(menu);
         root.setRight(confirmButton);

        mainWindow.setScene(new Scene(root, width, height));
        mainWindow.show();
    }

    /**
     * shown after game is lost
     */
    private void gameOverMenu() {
        GridPane grid = createGameGrid();

        String score = String.valueOf(game.getScore());
        Label scoreBoard = new Label("SCORE: " + score);
        scoreBoard.setPrefSize(400, 50);
        scoreBoard.setStyle("-fx-font-size:20");

        Button backToMenuButton = new Button("Main Menu");
        backToMenuButton.setOnAction(e -> {
            mainMenu();
        });
        backToMenuButton.setPrefSize(200, 60);
        backToMenuButton.setStyle("-fx-font-size:25");

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> {
            this.game = new Game();
            runGame();
        });
        newGameButton.setPrefSize(200, 60);
        newGameButton.setStyle("-fx-font-size:25");

        Button exitProgramButton = new Button("Exit Program");
        exitProgramButton.setOnAction(e -> closeProgram());
        exitProgramButton.setPrefSize(200, 60);
        exitProgramButton.setStyle("-fx-font-size:25");

        VBox menu = new VBox();
        menu.getChildren().addAll(newGameButton, backToMenuButton, exitProgramButton);
        menu.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setLeft(grid);
        root.setCenter(menu);
        root.setTop(scoreBoard);
        root.setBottom(musicSlider);

        mainWindow.setScene(new Scene(root, width, height));
    }

    /**
     * closes the whole program
     */
    private void closeProgram() {
        mainWindow.close();
    }

    /**
     * creates a slider to modify sound volume
     * @return sound volume slider
     */
    private Slider createMusicSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        //slider scale 0-100, volume 0-1 -> multiplied by 100
        slider.setValue(musicPlayer.getVolume() * 100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(25);
        slider.setBlockIncrement(25);
        slider.valueProperty().addListener(e -> {
            //volume scale 0-1, slider 0-100 -> divided by 100
            musicPlayer.setVolume(slider.getValue() / 100.0);
        });

        BorderPane.setAlignment(slider, Pos.BOTTOM_RIGHT);
        slider.setMaxWidth(150);

        return slider;
    }

    /**
     * allows playing tetris theme music
     * @return object that playes music
     */
    private MediaPlayer createMusicPlayer() {

        Media sound = new Media(this.getClass().getResource("/music/Tetris_theme.mp3").toString());
        musicPlayer = new MediaPlayer(sound);
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
        musicPlayer.setVolume(0.5);
        return musicPlayer;
    }
}
