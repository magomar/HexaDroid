package deludobellico.hexadroid.app.map;


import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import deludobellico.hexadroid.app.map.TerrainType;
import deludobellico.hexadroid.app.util.RandomGenerator;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class Tile implements Serializable{
    private java.util.Map<TerrainType, Directions> terrain;

    private Tile() {
        this.terrain = new EnumMap<TerrainType, Directions>(TerrainType.class);
    }

    public Map<TerrainType, Directions> getTerrain() {
        return terrain;
    }

    private void setTerrain(Map<TerrainType, Directions> terrain) {
        this.terrain = terrain;
    }

    public static Tile createSingleTerrainRandomTile(TerrainType terrainType, double probability) {
        if (probability > 1 || probability < 0)
            throw new IllegalArgumentException("Wrong probability parameter (should be in range [0,1])");
        Tile tile = new Tile();
        EnumMap<TerrainType, Directions> terrain = new EnumMap<TerrainType, Directions>(TerrainType.class);
        if (RandomGenerator.probabilityCheck(probability)) {
            int directions = RandomGenerator.getInstance().nextInt(Directions.ALL_COMBINED_DIRECTIONS.length);
            terrain.put(terrainType, Directions.ALL_COMBINED_DIRECTIONS[directions]);
        }
        tile.setTerrain(terrain);
        return tile;
    }
}
