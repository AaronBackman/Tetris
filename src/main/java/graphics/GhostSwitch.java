package graphics;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * on/off switch can be used to choose if the ghost block is shown or not
 */
public class GhostSwitch extends StackPane {
    private final Button button = new Button("Hide Ghost Block");
    private final String styleOff = "-fx-font-size:20;-fx-background-color: RED;";
    private final String styleOn = "-fx-font-size:20;-fx-background-color: GREEN;";

    /**
     * switch state: true = ON, false = OFF, by default the ghost block is shown
     */
    private boolean state = true;

    /**
     * set some basic properties
     */
    private void init() {
        getChildren().addAll(button);
        setAlignment(button, Pos.CENTER);
        button.setPrefSize(250, 60);
        if(state) {
            button.setStyle(styleOn);
        }
        else {
            button.setStyle(styleOff);
        }
    }

    /**
     * initializes some properties to the switch (eg. size) and button functionality
     */
    public GhostSwitch() {
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state) {
                    button.setStyle(styleOff);
                    button.setText("Show ghost block");
                    state = false;
                } else {
                    button.setStyle(styleOn);
                    button.setText("Hide ghost block");
                    state = true;
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }

    /**
     * @return switch state: is ghost block is shown?, true = ON, false = OFF
     */
    public boolean getState() {
        return state;
    }
}
