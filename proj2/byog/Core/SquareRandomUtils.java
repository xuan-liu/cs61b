package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

class SquareRandomUtils {

    private static final int DEFAULT_MAX_SQUARE_WIDTH = 6;
    private static final int DEFAULT_MAX_SQUARE_HEIGHT = 6;

    /**
     * Directions are where we generate new squares.
     */
    private static final String[] DIRECTIONS = new String[] {"left", "right", "upper", "down"};
    private static final double PROB_HW_TO_GEN_ROOM = 0.9;
    private static final int HW_LENGTH_BOUND = 10;


    static String[] getRandomDirections(Random random) {
        RandomUtils.shuffle(random, DIRECTIONS);
        int size = RandomUtils.uniform(random, 1, 4);
        String[] temp = new String[size];
        System.arraycopy(DIRECTIONS, 0, temp, 0, size);
        return temp;
    }

    static boolean getRandomSquareTypeAsRoom(Random random) {
        return  (RandomUtils.bernoulli(random, PROB_HW_TO_GEN_ROOM));
    }

    static Room getInitialRandomRoom(Random random, TETile[][] world) {
        int width = RandomUtils.uniform(random, 3, DEFAULT_MAX_SQUARE_WIDTH + 1);
        int height = RandomUtils.uniform(random, 3, DEFAULT_MAX_SQUARE_HEIGHT + 1);
        int x = RandomUtils.uniform(random, world.length / 2 - 5, world.length / 2 + 5);
        int y = RandomUtils.uniform(random, 5, world[0].length / 2);
        return new Room(new Point(x, y), width, height);
    }

    private static int getNextHallWayLength(Random random, Square square, String direction) {
        switch (direction) {
            case "left":
                return RandomUtils.uniform(random, 1,
                        Math.min(square.corner.x, HW_LENGTH_BOUND));
            case "right":
                return RandomUtils.uniform(random, 1, Math.min(Square.world.length
                        - (square.corner.x + square.width), HW_LENGTH_BOUND));
            case "upper":
                return RandomUtils.uniform(random, 1, Math.min(Square.world[0].length
                        - (square.corner.y + square.height), HW_LENGTH_BOUND));
            case "bottom":
                return RandomUtils.uniform(random, 1, Math.min(square.corner.y, HW_LENGTH_BOUND));
            default:
                throw new IllegalArgumentException("unknown direction: " + direction);
        }
    }

    /** Only Room class can call this method and on upper and bottom directions */
    static VerticalHW generateRandomVerHW(Random random, Square square, String direction) {
        int length;
        try {
            length = getNextHallWayLength(random, square, direction);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int x = RandomUtils.uniform(random, square.corner.x, square.corner.x + square.width);
        int y;
        switch (direction) {
            case "upper":
                y = square.corner.y + square.height;
                break;
            case "bottom":
                y = square.corner.y - length;
                break;
            default:
                throw new RuntimeException("not yet defined for left and right directions");
        }
        return new VerticalHW(new Point(x, y), length);
    }

    /** Only Room class can call this method and on left and right directions
     */
    static HorizontalHW generateRandomHoriHW(Random random, Square square, String direction) {
        int length;
        try {
            length = getNextHallWayLength(random, square, direction);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int y = RandomUtils.uniform(random, square.corner.y, square.corner.y + square.height);
        int x;
        switch (direction) {
            case "left":
                x = square.corner.x - length;
                break;
            case "right":
                x = square.corner.x + square.width;
                break;
            default:
                throw new RuntimeException("not yet defined for upper and bottom directions");
        }
        return new HorizontalHW(new Point(x, y), length);
    }

    /** This method is written only for HorizontalHW to generate side Vertical HallWays*/
    static VerticalHW generateRandomSideVerHW(Random random,
                                              HorizontalHW square, String direction) {
        // the max bound is up to you
        int length = RandomUtils.uniform(random, 2, 7);
        int y = RandomUtils.uniform(random,
                square.corner.y - length + 1, square.corner.y + 1);

        int x;
        switch (direction) {
            case "left":
                x = square.corner.x - 1;
                break;
            case "right":
                x = square.corner.x + square.width;
                break;
            default:
                throw new RuntimeException("generateRandomSideVerHW "
                        + "only supported left and right directions");
        }
        return new VerticalHW(new Point(x, y), length);
    }

    /** This method is written only for VerticalHW to generate top down Horizontal HallWays*/
    static HorizontalHW generateRandomTopDownHoriHW(Random random,
                                                    VerticalHW square, String direction) {
        // the max bound is up to you
        int length = RandomUtils.uniform(random, 2, 7);
        int x = RandomUtils.uniform(random,
                square.corner.x - length + 1, square.corner.x + 1);

        int y;
        switch (direction) {
            case "upper":
                y = square.corner.y + square.height;
                break;
            case "bottom":
                y = square.corner.y - 1;
                break;
            default:
                throw new RuntimeException("generateRandomTopDownHoriHW "
                        + "only supported upper and bottom directions");
        }
        return new HorizontalHW(new Point(x, y), length);
    }

    static Room generateRandomTopDownRoom(Random random, HallWay square, String direction) {
        int width = RandomUtils.uniform(random, 3, 8);
        int height = RandomUtils.uniform(random, 3, 9);

        int x;
        try {
            x = RandomUtils.uniform(random,
                    square.corner.x - width + 1, square.corner.x + square.width - 1 + width - 1);
        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
            return null;
        }

        int y;
        switch (direction) {
            case "upper":
                y = square.corner.y - 1;
                break;
            case "bottom":
                y = square.corner.y + square.height;
                break;
            default:
                return null;
        }
        return new Room(new Point(x, y), width, height);
    }

    static Room generateRandomSideRoom(Random random, HallWay square, String direction) {
        int width = RandomUtils.uniform(random, 1, 5);
        int height = RandomUtils.uniform(random, 2, 9);

        int y;
        try {
            y = RandomUtils.uniform(random,
                    square.corner.y - height + 1, square.corner.y + square.height);
        } catch (IllegalArgumentException e) {
//            System.out.println(e.getMessage());
            return null;
        }

        int x;
        switch (direction) {
            case "left":
                x = square.corner.x - width;
                break;
            case "right":
                x = square.corner.x + square.width;
                break;
            default:
                return null;
        }
        return new Room(new Point(x, y), width, height);
    }

}
