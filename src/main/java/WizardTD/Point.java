package WizardTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    public int x;
    public int y;
    public Point previous;
    /**
     * Initializes a new Point object with the specified coordinates and a reference to the previous point.
     *
     * @param x         The x-coordinate of the point.
     * @param y         The y-coordinate of the point.
     * @param previous  The previous point in the path.
     */
    public Point(int x, int y, Point previous) {
        this.x = x;
        this.y = y;
        this.previous = previous;
    }
    /**
     * Converts the Point readable string in the format "(x, y)".
     *
     * @return A string representation of the Point.
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    /**
     * Checks if this Point is equal to another object by comparing their x and y coordinates.
     *
     * @param o The object to compare to this Point.
     * @return True if the objects are equal, false otherwise.
     */

    @Override
    public boolean equals(Object o) {
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }
    
    /**
     * Generates a hash code for this Point based on its x and y coordinates.
     *
     * @return The hash code for the Point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a new Point object with coordinates offset by the specified values.
     *
     * @param ox The x-coordinate offset.
     * @param oy The y-coordinate offset.
     * @return A new Point with the offset coordinates.
     */
    public Point offset(int ox, int oy) {
        return new Point(x + ox, y + oy, this);
    }

/**
 * Checks if a given point is walkable on the map.
 *
 * @param map   The map grid.
 * @param point The point to check for walkability.
 * @return True if the point is walkable, false otherwise.
 */
public static boolean IsWalkable(char[][] map, Point point) {
    if (point.y < 0 || point.y >= map.length) {
        return false;
    }
    if (point.x < 0 || point.x >= map[0].length) {
        return false;
    }
    return map[point.y][point.x] == 'X' || map[point.y][point.x] == 'W'; // 'X' and 'W' are walkable
}

/**
 * Finds neighboring points that are walkable on the map.
 *
 * @param map   The map grid.
 * @param point The point for which neighbors are found.
 * @return A list of neighboring walkable points.
 */
public static List<Point> FindNeighbors(char[][] map, Point point) {
    List<Point> neighbors = new ArrayList<>();
    Point up = point.offset(0, 1);
    Point down = point.offset(0, -1);
    Point left = point.offset(-1, 0);
    Point right = point.offset(1, 0);
    if (IsWalkable(map, up)) {
        neighbors.add(up);
    } 
    if (IsWalkable(map, down)) {
        neighbors.add(down);
    }
        
    if (IsWalkable(map, left)) {
        neighbors.add(left);
    }
    if (IsWalkable(map, right)) {
        neighbors.add(right);
    }
    return neighbors;
}

/**
 * Finds a path from the start point to the end point on the map using A* search algorithm.
 *
 * @param map   The map grid.
 * @param start The starting point.
 * @param end   The destination point.
 * @return A list of points representing the path from start to end, or null if no path is found.
 */
public static List<Point> FindPath(char[][] map, Point start, Point end) {

    boolean finished = false;
    List<Point> used = new ArrayList<>();
    used.add(start);
    while (!finished) {
        List<Point> newOpen = new ArrayList<>();
        for (int i = 0; i < used.size(); ++i) {
            Point point = used.get(i);
            for (Point neighbor : FindNeighbors(map, point)) {
                if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                    newOpen.add(neighbor);
                }
            }
        }

        for (Point point : newOpen) {
            used.add(point);
            if (end.equals(point)) {
                finished = true;
                break;
            }
        }

        if (!finished && newOpen.isEmpty()) {
            return null;
        }
            
    }

    List<Point> path = new ArrayList<>();
    Point point = used.get(used.size() - 1);
    while (point.previous != null) {
        path.add(0, point);
        point = point.previous;
    }
    return path;
    }
}