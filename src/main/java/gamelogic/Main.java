package gamelogic;

public class Main {
    public static void main(String[] args) {
        Peli peli = new Peli();
        try {
            peli.aloitaPeli();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
