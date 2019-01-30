package byog.Core;

import java.util.Random;

class VerticalHW extends HallWay {

    VerticalHW(Point corner, int height) {
        if (Square.world == null) {
            throw new RuntimeException("Oops! The world has not already been initialized!");
        }

        this.corner = corner;
        this.width = 1;
        this.height = height;
    }

    @Override
    Square generateHallWay(Random random, String direction) {
        switch (direction) {
            case "left": case "right":
                return SquareRandomUtils.generateRandomHoriHW(random, this, direction);
            case "upper": case "bottom":
                return SquareRandomUtils.generateRandomTopDownHoriHW(random, this, direction);
            default:
                return null;
        }
    }

    @Override
    Square generateRoom(Random random, String direction) {
        switch (direction) {
            case "upper": case "bottom":
                return SquareRandomUtils.generateRandomTopDownRoom(random, this, direction);
            case "left": case "right":
                return generateHallWay(random, direction);
            default:
                return null;
        }
    }

}
