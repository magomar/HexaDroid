package deludobellico.hexadroid.app.map;

import android.graphics.Point;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public enum Directions implements Serializable {

    N,
    NE,
    N_NE,
    SE,
    N_SE,
    NE_SE,
    N_NE_SE,
    S,
    N_S,
    NE_S,
    N_NE_S,
    SE_S,
    N_SE_S,
    NE_SE_S,
    N_NE_SE_S,
    SW,
    N_SW,
    NE_SW,
    N_NE_SW,
    SE_SW,
    N_SE_SW,
    NE_SE_SW,
    N_NE_SE_SW,
    S_SW,
    N_S_SW,
    NE_S_SW,
    N_NE_S_SW,
    SE_S_SW,
    N_SE_S_SW,
    NE_SE_S_SW,
    N_NE_SE_S_SW,
    NW,
    N_NW,
    NE_NW,
    N_NE_NW,
    SE_NW,
    N_SE_NW,
    NE_SE_NW,
    N_NE_SE_NW,
    S_NW,
    N_S_NW,
    NE_S_NW,
    N_NE_S_NW,
    SE_S_NW,
    N_SE_S_NW,
    NE_SE_S_NW,
    N_NE_SE_S_NW,
    SW_NW,
    N_SW_NW,
    NE_SW_NW,
    N_NE_SW_NW,
    SE_SW_NW,
    N_SE_SW_NW,
    NE_SE_SW_NW,
    N_NE_SE_SW_NW,
    S_SW_NW,
    N_S_SW_NW,
    NE_S_SW_NW,
    N_NE_S_SW_NW,
    SE_S_SW_NW,
    N_SE_S_SW_NW,
    NE_SE_S_SW_NW,
    N_NE_SE_S_SW_NW,
    C;
    private final int bitmask;
    private final Point coordinates;
    private final Set<Direction> directions;
    public static final Directions[] ALL_COMBINED_DIRECTIONS = Directions.values();

    private Directions() {
        this.bitmask = ordinal() + 1;
        int column = ordinal() / 8;
        int row = ordinal() % 8;
        coordinates = new Point(column, row);
        directions = Direction.getDirections(bitmask);
    }

    /**
     * Obtains a bitmask for the combination of {@link #directions}. The resulting bitmasks are easily transformed into
     * indexes to locate a terrain image in the terrain image file. Each direction is encoded as a simple bitflag (the
     * ordinal of Direction enum)
     * <b>Examples:
     * <b>"N" -> 000001
     * <b>"N NE" -> 000011
     * <b>"S SE" -> 001100
     *
     * @return
     */
    public int getBitmask() {
        return bitmask;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public boolean contains(Direction direction) {
        return directions.contains(direction);
    }

    public static Directions getDirections(int bitmask) {
        return ALL_COMBINED_DIRECTIONS[bitmask - 1];
    }

    public boolean containsSome(Set<Direction> directions) {
        Set<Direction> result = EnumSet.copyOf(directions);
        result.retainAll(this.directions);
        return !result.isEmpty();
    }
}
