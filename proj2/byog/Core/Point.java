package byog.Core;

/** a simple class representing a pair in a rectangular coordinate system*/
class Point {

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x;
    int y;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            throw new IllegalArgumentException("Point cannot compare to " + obj.getClass());
        }
        Point other = (Point) obj;
        return this.x == other.x && this.y == other.y;
    }
}
