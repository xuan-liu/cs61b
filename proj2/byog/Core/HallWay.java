package byog.Core;

import java.util.Random;

abstract class HallWay extends Square {

    abstract Square generateRoom(Random random, String direction);

    abstract Square generateHallWay(Random random, String direction);

    @Override
    Square[] generateSquares(Random random) {
        // now only generate Vertical HallWays
        String[] directions = SquareRandomUtils.getRandomDirections(random);
        Square[] results = new Square[directions.length];
        for (int i = 0; i < results.length; i++) {
            if (SquareRandomUtils.getRandomSquareTypeAsRoom(random)) {
                results[i] = generateRoom(random, directions[i]);
            } else {
                results[i] = generateHallWay(random, directions[i]);
            }
        }
        return results;
    }
}
