public class Coordinate {
    // This class is used to represent the coordinates on the board

    int x;
    int y;

    // Constructor for the Coordinate class
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // retrieves the X coordinate
    public int getX() {
        return x;
    }

    // retrieves the Y coordinate
    public int getY() {
        return y;
    }

    // checks if two coordinates are equal
    public boolean equals(Coordinate c2) {
        return (this.x == c2.x) && (this.y == c2.y);
    }

    // puts the coordinate object in a readable format
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }


}