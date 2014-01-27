package deludobellico.hexadroid.app.map;

import android.graphics.Point;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import deludobellico.hexadroid.app.util.Util;

public enum Direction implements Serializable {
    N(0, -1, -1),
    NE(1, 0, -1),
    SE(1, 1, 0),
    S(0, 1, 1),
    SW(-1, 1, 0),
    NW(-1, 0, -1),
    C(0, 0, 0);
    private final int incColumn;
    private final int incRowEven;
    private final int incRowOdd;

    /**
     * All real directions, that is all directions except {@link #C}
     */
    public final static Set<Direction> DIRECTIONS = EnumSet.range(Direction.N, Direction.NW);
    /**
     * All directions, which includes the 6 real directions plus {@link #C}
     */
    private final static Direction[] ALL_DIRECTIONS = Direction.values();

    private Direction(final int incI, final int incJEven, final int incJOdd) {
        this.incColumn = incI;
        this.incRowEven = incJEven;
        this.incRowOdd = incJOdd;
    }

    public int getIncColumn() {
        return incColumn;
    }

    public int getIncRowEven() {
        return incRowEven;
    }

    public int getIncRowOdd() {
        return incRowOdd;
    }

    public Point getNeighborCoordinates(Point coordinates) {
        int column = coordinates.x + getIncColumn();
        int row = coordinates.y + (Util.isEven(coordinates.x) ? getIncRowEven() : getIncRowOdd());
        return new Point(column, row);
    }
    /**
     * Obtains a bitmask for the combination of {@code directions} passed as parameter. The resulting bitmasks are
     * easily transformed into indexes to locate a terrain image in the terrain image file. Each direction is encoded as
     * a simple bitflag (the ordinal of Direction enum)
     * <b>Examples:
     * <b>"N" -> 000001
     * <b>"N NE" -> 000011
     * <b>"S SE" -> 001100
     *
     * @param directions
     * @return
     */
    public static int getBitmask(Set<Direction> directions) {
        int mask = 0;
        for (Direction dir : directions) {
            int bit = 1 << dir.ordinal();
            mask |= bit;
        }
        return mask;
    }

    public static Set<Direction> getDirections(int bitmask) {
        Set<Direction> directions = EnumSet.noneOf(Direction.class);
        for (int bit = 0; bit < ALL_DIRECTIONS.length; bit++) {
            if (testBitFlag(bitmask, bit)) {
                directions.add(ALL_DIRECTIONS[bit]);
            }
        }
        return directions;
    }

    private static boolean testBitFlag(int bitmask, int bit) {
        int flag = 1 << bit;
        return (bitmask & flag) != 0;
    }
}
