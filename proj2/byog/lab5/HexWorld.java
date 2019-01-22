package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static int s = 4;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }

        return s + 2 * effectiveI;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being grass.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.WATER;
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);

        }
    }

    /**
     * Computes the position of the next left hexagon while
     * we know one current hexagon position
     * @param p current hexagon position
     * @param s The size of the hex.
     * @return
     */
    public static Position nextLeftPos(Position p, int s) {
        int xRowStart = p.x + hexRowOffset(s, s);
        int yNext = p.y + s;
        int xNext = xRowStart - s;
        return new Position(xNext,yNext);
    }

    /**
     * Computes the position of the next right hexagon while
     * we know one current hexagon position
     * @param p current hexagon position
     * @param s The size of the hex.
     * @return
     */
    public static Position nextRightPos(Position p, int s) {
        int xRowEnd = p.x + hexRowOffset(s, s) + hexRowWidth(s,s);
        int yNext = p.y + s;
        int xNext = xRowEnd;
        return new Position(xNext,yNext);

    }

    /**
     * Computes the position of the next row of hexagon while
     * we know the current row of hexagon position
     * @param ps current row of hexagon position
     * @param s The size of the hex.
     * @param i the current row (row for the whole big hexagon)
     * @return
     */
    public static Position[] nextPos(Position[] ps, int s, int i) {
        Position[] nextps = new Position[3];
        if (i == 0) {
            nextps[0] = nextLeftPos(ps[0],s);
            nextps[1] = nextRightPos(ps[0],s);
            nextps[2] = null;
        } else if (i == 7) {
            nextps[0] = nextRightPos(ps[0],s);
            nextps[1] = null;
            nextps[2] = null;
        } else if (i % 2 == 1) {
            nextps[0] = nextLeftPos(ps[0],s);
            nextps[1] = nextRightPos(ps[0],s);
            nextps[2] = nextRightPos(ps[1],s);
        } else {
            nextps[0] = nextRightPos(ps[0],s);
            nextps[1] = nextRightPos(ps[1],s);
            nextps[2] = null;
        }
        return nextps;
    }

    public static void main(String[] args) {

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] hex = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hex[x][y] = Tileset.NOTHING;
            }
        }

        // define the start position of Hexagon and draw the first small Hexagon
        Position[] start = new Position[3];
        start[0] = new Position(20,10);

        addHexagon(hex, start[0], s, randomTile());

        // draw the other small Hexagon
        for (int i = 0; i < 8; i += 1) {
            Position[] next = nextPos(start,s,i);
            for (int j = 0; j < 3 && next[j] != null; j += 1) {
                addHexagon(hex, next[j], s, randomTile());
            }
            start = next;
        }

        // draws the world to the screen
        ter.renderFrame(hex);
    }

// My test for Hex World
//    @Test
//    public void testNextLeftPos() {
//        Position p = new Position(8,1);
//
//        assertEquals(5, nextLeftPos(p,2,1).x);
//        assertEquals(3, nextLeftPos(p,2,1).y);
//    }
//
//    @Test
//    public void testNextRightPos() {
//        Position p = new Position(8,1);
//
//        assertEquals(11, nextRightPos(p,2,1).x);
//        assertEquals(3, nextRightPos(p,2,1).y);
//    }

// Mycode for addHexagon and test
//    public static int computeWidth(int length, int row){
//        if (row <= length) {
//            return length + 2 * (row - 1);
//        } else {
//            return length + 2 * (2 * length - row);
//        }
//    }
//
//    public static int computeXPos(int startXPos, int length, int row){
//        if (row <= length) {
//            return startXPos - (row - 1);
//        } else {
//            return startXPos - (2 * length - row);
//        }
//    }
//
//    public static void addHexagon(TETile[][] tiles, int xStart, int yStart, int length){
//        for (int y = yStart; y > yStart - 2 * length; y -= 1) {
//            int row = yStart - y + 1;
//            int pos = computeXPos(xStart,length,row);
//            for (int x = pos; x < pos + computeWidth(length,row); x += 1) {
//                tiles[x][y] = Tileset.GRASS;
//            }
//
//        }
//    }

//    @Test
//    public void testComputeWidth() {
//        assertEquals(8, computeWidth(4, 3));
//        assertEquals(7, computeWidth(3, 4));
//    }
//
//    @Test
//    public void testComputePosition() {
//        assertEquals(4, computePosition(5,2,2));
//        assertEquals(5, computePosition(5,2,4));
//    }
}
