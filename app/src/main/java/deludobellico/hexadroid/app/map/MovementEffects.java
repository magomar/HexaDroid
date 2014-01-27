package deludobellico.hexadroid.app.map;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface MovementEffects {
    final static int IMPASSABLE = Integer.MAX_VALUE;
    final static int MIN_MOVEMENT_COST = 1;

    int getMovementCost();
}
