package deludobellico.hexadroid.app.map;

import java.io.Serializable;

/**
 * Created by mario on 20/12/13.
 */
public class Board implements Serializable {
    private Tile[][] tiles;
    private int width;
    private int height;

    private Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public static Board createRandomMap(int width, int height, TerrainType terrainType) {
        Board board = new Board(width, height);
        board.tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            Tile[] tileColumn = board.tiles[i];
            for (int j = 0; j < height; j++) {
                tileColumn[j] = Tile.createSingleTerrainRandomTile(terrainType, 0.5);
            }
        }
        return board;
    }

}
