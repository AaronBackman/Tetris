package gamelogic;

/**
 * includes information about the properties of a single square:
 * is falling?, is full? and color
 */
public class Square {
    private boolean isFalling;
    private boolean isFull;
    private Color color;

    /**
     * changes the squares color from enum to a String, which javafx can recognize
     * @return color in String form
     */
    public String getColorAsString() {
        if(color == Color.BLACK) {
            return "black";
        }
        else if(color == Color.AQUA) {
            return "aqua";
        }
        else if(color == Color.BLUE) {
            return "blue";
        }
        else if(color == Color.ORANGE) {
            return "orangered";
        }
        else if(color == Color.YELLOW) {
            return "yellow";
        }
        else if(color == Color.GREEN) {
            return "lime";
        }
        else if(color == Color.PURPLE) {
            return "magenta";
        }
        else if(color == Color.RED) {
            return "red";
        }
        else if(color == Color.GRAY) {
            return "grey";
        }
        else {
            return "black";
        }
    }

    /**
     * compares if 2 squares are the same
     * @param anotherSquare square that is compared to
     * @return true if they are the same, otherwise false
     */
    public boolean equals(Square anotherSquare) {
        //are both squares are full?
        if(this.isFull() != anotherSquare.isFull()) {
            return false;
        }

        // are both are falling?
        if(this.isFalling() != anotherSquare.isFalling()) {
            return false;
        }

        //do both have the same color?
        if(this.getColor() != anotherSquare.getColor()) {
            return false;
        }
        //otherwise all properties are the same -> equals
        return true;
    }

    public boolean isFalling() {
        return isFalling;
    }
    public void setFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }

    public boolean isFull() {
        return isFull;
    }
    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}
