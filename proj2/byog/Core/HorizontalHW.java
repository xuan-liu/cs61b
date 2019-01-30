package byog.Core;

import java.util.Random;

class HorizontalHW extends HallWay {

    HorizontalHW(Point corner, int width) {
        if (Square.world == null) {
            throw new RuntimeException("Oops! The world has not already been initialized!");
        }

        this.corner = corner;
        this.width = width;
        this.height = 1;
    }

    @Override
    Square generateHallWay(Random random, String direction) {
        switch (direction) {
            case "upper": case "bottom":
                return SquareRandomUtils.generateRandomVerHW(random, this, direction);
            case "left": case "right":
                return SquareRandomUtils.generateRandomSideVerHW(random, this, direction);
            default:
                return null;
        }
    }

    @Override
    Square generateRoom(Random random, String direction) {
        switch (direction) {
            case "upper": case "bottom":
                return generateHallWay(random, direction);
            case "left": case "right":
                return SquareRandomUtils.generateRandomSideRoom(random, this, direction);
            default:
                return null;
        }
    }
}
