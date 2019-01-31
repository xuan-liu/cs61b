package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

class WorldGenerator {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 60;
    private static final TETile DEFAULT_TILE = Tileset.FLOOR;
    private static final TETile DEFAULT_BORDER_TILE = Tileset.WALL;
    private static TERenderer renderer = new TERenderer();

    /**
     * RANDOM should be initialized when generateWorld method is called.
     */
    private static Random RANDOM;
    private static void setRANDOM(long seed) {
        WorldGenerator.RANDOM = new Random(seed);
    }

    /** Fill the world with TileSet.NOTHING*/
    private static void initializeTiles(TETile[][] world) {
        Square.setWorld(world);
        Square worldSquare = new Room(new Point(0, 0), world.length, world[0].length);
        drawSquare(worldSquare, Tileset.NOTHING);
    }

    /** used for drawSquareBorders to avoid overlap existed FLOOR*/
    private static void drawTile(TETile[][] world, int x, int y, TETile tile) {
        // TODO fix this ugly redundant code
        if (world[x][y].equals(Tileset.NOTHING)) {
            world[x][y] = tile;
        }
    }

    private static void drawSquareBorders(Square square) {
        int leftX = square.corner.x - 1;
        int rightX = square.corner.x + square.width;
        int bottomY = square.corner.y - 1;
        int upperY = square.corner.y + square.height;

        // left and right columns
        for (int y = bottomY; y <= upperY; y++) {
            drawTile(Square.world, leftX, y, DEFAULT_BORDER_TILE);
            drawTile(Square.world, rightX, y, DEFAULT_BORDER_TILE);
        }
        // upper and bottom rows
        for (int x = leftX; x <= rightX; x++) {
            drawTile(Square.world, x, upperY, DEFAULT_BORDER_TILE);
            drawTile(Square.world, x, bottomY, DEFAULT_BORDER_TILE);
        }
    }

    /** Fill the specific square with the given tile*/
    private static void drawSquare(Square square, TETile tile) {
        for (int x = square.corner.x; x < (square.corner.x + square.width); x++) {
            for (int y = square.corner.y; y < (square.corner.y + square.height); y++) {
                Square.world[x][y] = tile;
            }
        }

        // skip the `world square`
        if (!square.corner.equals(new Point(0, 0))) {
            drawSquareBorders(square);
        }
    }

    /** Fill the specific square with the default Tileset.FLOOR tile*/
    private static void drawSquare(Square square) {
//        if (square instanceof Room) {
//            drawSquare(square, Tileset.SAND);
//        } else if (square instanceof HallWay){
//            drawSquare(square, Tileset.FLOOR);
//        } else {
//            drawSquare(square, Tileset.FLOWER);
//        }
        drawSquare(square, Tileset.FLOOR);
    }

    // TODO This method may be in the Square classes?
    /** Check the validity of the square.
     * @return return false if the square overlaps with other squares' FLOOR tile
     */
    private static boolean isValidSquare(Square square) {
        // you need to consider the square's borders
        if (square == null
                || square.corner.x <= 0 || square.corner.x >= (Square.world.length - 1)
                || (square.corner.x + square.width - 1) >= (Square.world.length - 1)

                || square.corner.y <= 0 || square.corner.y >= (Square.world[0].length - 1)
                || (square.corner.y + square.height - 1) >= (Square.world[0].length - 1)) {

            return false;
        }

        for (int x = square.corner.x; x < (square.corner.x + square.width); x++) {
            for (int y = square.corner.y; y < (square.corner.y + square.height); y++) {
                if (Square.world[x][y].equals(Tileset.FLOOR)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Recursively evolve the world.
     */
    private static void evolveWorld(Square square) {
        if (!isValidSquare(square)) {
            return;
        }

        drawSquare(square);

        Square[] newSquares = square.generateSquares(RANDOM);
        if (newSquares == null) {
            return;
        }

        for (int i = 0; i < newSquares.length; i++) {
            if (newSquares[i] != null) {
                evolveWorld(newSquares[i]);
            }
        }
    }

    public static void generateWorld(long seed, TETile[][] world) {
        setRANDOM(seed);
        initializeTiles(world);
        Square initialRoom = SquareRandomUtils.getInitialRandomRoom(RANDOM, world);
        evolveWorld(initialRoom);

        // add door in the initialRoom
        Square.world[initialRoom.corner.x - 1][initialRoom.corner.y + 1] = Tileset.LOCKED_DOOR;
    }

    /** used only for tests*/
//    private static void generateWorld(TETile[][] world) {
//        generateWorld(new Random().nextLong(), world);
//    }

//    public static void main(String[] args) {
//        renderer.initialize(WIDTH, HEIGHT);
//
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        generateWorld(world);
//
//        renderer.renderFrame(world);
//    }
}
