package gamelogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testaa palikan siirtamista ruudukolla yksikkotesteilla
 */
class BlockTest {
    Grid grid;
    Block block;

    /**
     * kaytannossa konstruktori
     */
    @BeforeEach
    public void init() {
        this.grid = new Grid();

        BlockFactory pl = new BlockFactory();
        this.block = pl.makeRandomBlock(grid);
    }

    /**
     * tarkistaa vain tapauksen jossa paivityksen pitaisi onnistua, toinen metodi tarkistaa onko se mahdollista
     */
    @Test
    public void paivitaRuudukkoTesti() {
        Square[][] kaantoAlue = block.getShape();

        int[] sijainti = new int[]{10,10};
        grid.updateGrid(sijainti, kaantoAlue);
        for(int i=0; i<kaantoAlue.length; i++) {
            for(int j=0; j<kaantoAlue.length; j++) {
                //tarkistaa ovatko palikan ruudut ja paivitettava kohta molemmat taynna
                assertEquals(kaantoAlue[j][i], grid.getSquares()[j+sijainti[0]][i+sijainti[1]]);
            }
        }

        sijainti = new int[]{7,3};
        grid.getSquares()[kaantoAlue.length + sijainti[0]][kaantoAlue.length + sijainti[1]].setFull(true);
        grid.updateGrid(sijainti, kaantoAlue);
        for(int i=0; i<kaantoAlue.length; i++) {
            for(int j=0; j<kaantoAlue.length; j++) {
                //tarkistaa ovatko palikan ruudut ja paivitettava kohta molemmat taynna
                if(kaantoAlue[j][i].isFull()) {
                    assertEquals(kaantoAlue[j][i], grid.getSquares()[j+sijainti[0]][i+sijainti[1]]);
                }
            }
        }
    }

    /**
     * testaa toimiiko taysien ruutujen poistaminen ja pisteidenlasku
     */
    @Test
    public void poistaTaydetRuudutTesti() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        //uusi ruudukko, jossa on 2 taytta rivia, tarkoituksena poistaa taydet rivit ja laskea pisteet
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });

        int pisteTulos = grid.removeFullSquares();
        int odotetutPisteet = 100; //kaksi poistettua rivia -> 100 pistetta

        assertFalse(grid.getSquares()[6][20].isFull());
        assertFalse(grid.getSquares()[6][21].isFull());

        assertFalse(grid.getSquares()[12][18].isFull());
        assertFalse(grid.getSquares()[12][19].isFull());

        assertFalse(grid.getSquares()[11][18].isFull());
        assertFalse(grid.getSquares()[11][19].isFull());

        assertTrue(grid.getSquares()[11][20].isFull());
        assertTrue(grid.getSquares()[12][20].isFull());

        //reunaruutuja ei saa poistaa
        assertTrue(grid.getSquares()[2][20].isFull());
        assertTrue(grid.getSquares()[2][21].isFull());
        assertTrue(grid.getSquares()[6][22].isFull());
        assertTrue(grid.getSquares()[6][23].isFull());
        assertTrue(grid.getSquares()[6][24].isFull());
        assertTrue(grid.getSquares()[6][25].isFull());

        assertEquals(pisteTulos, odotetutPisteet);
    }

    /**
     * testaa toimiiko ruudukon paivittaminen
     */
    @Test
    public void voikoPaivittaaTesti() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });
        block = new IBlock(grid);
        assertFalse(grid.canUpdate(new int[]{10,17}, block.getShape()));//kohdassa on jo palikoita
        assertFalse(grid.canUpdate(new int[]{14,18}, block.getShape()));//reuna-alueella
        assertTrue(grid.canUpdate(new int[]{10,16}, block.getShape()));//kohta on tyhja
    }

    /**
     * testaa toimiiko haamupalikan asettaminen ruudukolle
     */
    @Test
    public void haamuPalikkaTesti() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });
        block = new Spalikka(grid);
        block.putIntoGrid();//asettaa myos haamupalikan

        assertFalse(grid.getSquares()[7][18].isFull());
        assertFalse(grid.getSquares()[7][18].isFalling());
        assertSame(grid.getSquares()[7][18].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[8][18].isFull());
        assertFalse(grid.getSquares()[8][18].isFalling());
        assertSame(grid.getSquares()[8][18].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[8][17].isFull());
        assertFalse(grid.getSquares()[8][17].isFalling());
        assertSame(grid.getSquares()[8][17].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[7][17].isFull());
        assertFalse(grid.getSquares()[7][17].isFalling());
        assertSame(grid.getSquares()[7][17].getColor(), Color.BLACK);

        assertTrue(grid.getSquares()[7][19].isFull());
        assertFalse(grid.getSquares()[7][19].isFalling());
        assertNotSame(grid.getSquares()[7][19].getColor(), Color.GRAY);
    }

    /**
     * testaa toimiiko haamupalikan liikuttaminen ruudukolla
     * putoavan palikan liikkeen mukaisesti
     */
    @Test
    public void haamuPalikkaLiikeTesti() {
        SquareFactory rt = new SquareFactory();
        Square I = rt.createBorderSquare();
        Square O = rt.createEmptySquare();
        grid.setSquares(new Square[][]{
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,O,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I,I,I},
                {O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,O,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I},
                {I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I,I}
        });
        block = new Spalikka(grid);
        block.putIntoGrid();//asettaa myos haamupalikan
        block.moveRight();

        //varmistavat, etta vanhasta kohdasta poistettiin haamupalikan osa
        assertFalse(grid.getSquares()[7][18].isFull());
        assertFalse(grid.getSquares()[7][18].isFalling());
        assertSame(grid.getSquares()[7][18].getColor(), Color.BLACK);

        assertFalse(grid.getSquares()[8][18].isFull());
        assertFalse(grid.getSquares()[8][18].isFalling());
        assertSame(grid.getSquares()[8][18].getColor(), Color.BLACK);

        assertFalse(grid.getSquares()[8][17].isFull());
        assertFalse(grid.getSquares()[8][17].isFalling());
        assertSame(grid.getSquares()[8][17].getColor(), Color.BLACK);

        //varmistaa etta uuteen kohtaan tulee haamupalikka
        assertFalse(grid.getSquares()[8][18].isFull());
        assertFalse(grid.getSquares()[8][18].isFalling());
        assertSame(grid.getSquares()[8][19].getColor(), Color.GRAY);

        assertFalse(grid.getSquares()[9][18].isFull());
        assertFalse(grid.getSquares()[9][18].isFalling());
        assertSame(grid.getSquares()[9][19].getColor(), Color.GRAY);
    }

    /**
     * testaa toimiiko palikan kaantaminen vastapaivaan ruudukolla
     */
    @Test
    public void kaannyVastapaivaanTesti() {
        block = new TBlock(grid);
        block.setLocation(new int[]{7, 2});

        block.putIntoGrid();

        block.turnCounterClockwiseInGrid();

        assertTrue(grid.getSquares()[7][3].isFull());
        assertTrue(grid.getSquares()[8][4].isFull());

        assertFalse(grid.getSquares()[7][4].isFull());
        assertFalse(grid.getSquares()[6][2].isFull());
        assertFalse(grid.getSquares()[7][1].isFull());
    }

    /**
     * testaa toimiiko palikan kaantaminen myotapaivaan ruudukolla
     */
    @Test
    public void kaannyMyotapaivaanTesti() {
        block = new TBlock(grid);
        block.setLocation(new int[]{7, 2});

        block.putIntoGrid();

        block.turnClockwiseInGrid();

        assertTrue(grid.getSquares()[9][3].isFull());
        assertTrue(grid.getSquares()[8][4].isFull());

        assertFalse(grid.getSquares()[9][4].isFull());
        assertFalse(grid.getSquares()[10][2].isFull());
        assertFalse(grid.getSquares()[9][1].isFull());
    }

    /**
     * testaa toimiiko palikan pudottaminen yhden ruudun ruudukolla
     */
    @Test
    public void pudotaYksiRuutuTesti() {
        block = new OBlock(grid);
        block.dropByOneSquare();

        //vanha palikan kohta
        assertFalse(grid.getSquares()[8][0].isFull());
        assertFalse(grid.getSquares()[8][0].isFalling());
        assertSame(grid.getSquares()[8][0].getColor(), Color.BLACK);

        //vanha palikan kohta
        assertFalse(grid.getSquares()[9][0].isFull());
        assertFalse(grid.getSquares()[9][0].isFalling());
        assertSame(grid.getSquares()[9][0].getColor(), Color.BLACK);

        //palikan uuden kohdan alla
        assertFalse(grid.getSquares()[8][3].isFull());
        assertFalse(grid.getSquares()[8][3].isFalling());
        assertSame(grid.getSquares()[8][3].getColor(), Color.BLACK);

        //palikan pitaisi viela olla tassa kohdassa
        assertTrue(grid.getSquares()[8][1].isFull());
        assertTrue(grid.getSquares()[8][1].isFalling());
        assertNotSame(grid.getSquares()[8][1].getColor(), Color.BLACK);

        //palikan pitaisi nyt olla tassa kohdassa
        assertTrue(grid.getSquares()[9][2].isFull());
        assertTrue(grid.getSquares()[9][2].isFalling());
        assertNotSame(grid.getSquares()[9][2].getColor(), Color.BLACK);
    }
}