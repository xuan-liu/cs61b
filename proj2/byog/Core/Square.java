package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

abstract class Square {

    /**
     * the 2d TETile should be initialized before using any Square classes
     */
    protected static TETile[][] world;

    static void setWorld(TETile[][] world) {
        Square.world = world;
    }

    /**
     * the left-bottom point of the current square <br>
     * corner is the point where we start drawing the square
     */
    protected Point corner;

    protected int width;
    protected int height;

    /**
     * @return return new squares from the current square
     */
    abstract Square[] generateSquares(Random random);
}
