package byog.Core;

import java.util.Random;


class Room extends Square {

    Room(Point corner, int width, int height) {
        if (Square.world == null) {
            throw new RuntimeException("Oops! The world has not already been initialized!");
        }

        this.corner = corner;
        this.width = width;
        this.height = height;
    }

    private HallWay generateHallWay(Random random, String direction) {
        switch (direction) {
            case "upper": case "bottom":
                return SquareRandomUtils.generateRandomVerHW(random, this, direction);
            case "left": case "right":
                return SquareRandomUtils.generateRandomHoriHW(random, this, direction);
            default:
                return null;
        }
    }

    /**
     * @return Room Square will only return HallWay Square
     */
    @Override
    HallWay[] generateSquares(Random random) {
        String[] directions = SquareRandomUtils.getRandomDirections(random);
        HallWay[] results = new HallWay[directions.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = generateHallWay(random, directions[i]);
        }
        return results;
    }
}
