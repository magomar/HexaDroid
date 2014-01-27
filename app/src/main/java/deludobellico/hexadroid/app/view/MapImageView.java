package deludobellico.hexadroid.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.EnumMap;
import java.util.Map;

import deludobellico.hexadroid.app.map.Board;
import deludobellico.hexadroid.app.map.Direction;
import deludobellico.hexadroid.app.map.Directions;
import deludobellico.hexadroid.app.map.TerrainType;
import deludobellico.hexadroid.app.map.Tile;
import deludobellico.hexadroid.app.util.ImageProvider;
import deludobellico.hexadroid.app.util.Util;

/**
 * Created by mario on 12/01/14.
 */
public class MapImageView extends ImageView {
    private int width; // Number of columns
    private int height; // Number of rows
    private int hexSide; // Side of the hexagon
    private int hexOffset; // Distance from left horizontal vertex to vertical axis
    private int hexApotheme; // Apotheme of the hexagon = radius of inscribed circumference
    private int hexRectWidth; // Width of the circumscribed rectangle
    private int hexRectHeight; // Height of the circumscribed rectangle
    private int hexGridWidth;  // hexOffset + hexSide (b + s)
    private Bitmap globalImage;
    private Board board;
    private Map<TerrainType, ImageProvider> terrainImageProvider;

    public MapImageView(Context context) {
        super(context);
    }

    public MapImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateBoard(Board board) {
        terrainImageProvider = new EnumMap<TerrainType, ImageProvider>(TerrainType.class);
        for (TerrainType tt : TerrainType.values()) {
            terrainImageProvider.put(tt, tt.createImageProvider(getContext()));
        }
        this.board = board;
        this.width = board.getWidth();
        this.height = board.getHeight();
        // load some graphics to obtain the measures of the hexagonal tiles
        ImageProvider someImageProvider = terrainImageProvider.get(TerrainType.FOREST);
        Bitmap someTerrainImage = someImageProvider.getImage(0);
        hexRectWidth = someTerrainImage.getWidth();
        hexRectHeight = someTerrainImage.getHeight();
        hexApotheme = hexRectHeight / 2;
        hexSide = (int) ((double) hexApotheme / Math.cos(Math.PI / 6));
        hexOffset = (int) (hexSide * Math.sin(Math.PI / 6));
        hexGridWidth = hexOffset + hexSide;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (globalImage == null)
            globalImage = Bitmap.createBitmap(width * hexGridWidth + hexOffset, height * hexRectHeight + hexApotheme, Bitmap.Config.ARGB_8888);
        Canvas imageCanvas = new Canvas(globalImage);
        // Paint it black!
        canvas.drawColor(Color.BLACK);
//        canvas.drawRect(0, 0, globalImage.getWidth(), globalImage.getHeight(), );
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < width; i++) {
            Tile[] tileColumn = tiles[i];
            for (int j = 0; j < height; j++) {
//                g.drawPolygon(buildHexagon(i,j));
                paintTile(imageCanvas, i, j, tileColumn[j]);
            }
        }

        canvas.drawBitmap(globalImage, 0, 0, null);
    }

    private void paintTile(Canvas canvas, int column, int row, Tile tile) {
        //Obtain tile position
        Point pos = tileToPixel(column, row);
        // First paint open terrain for the background
        Bitmap terrainImage = terrainImageProvider.get(TerrainType.OPEN).getImage(0);
        canvas.drawBitmap(terrainImage, pos.x, pos.y, null);
        Map<TerrainType, Directions> m = tile.getTerrain();
        for (Map.Entry<TerrainType, Directions> entry : m.entrySet()) {
            TerrainType terrainType = entry.getKey();
            Directions directions = entry.getValue();
            // Next paint the actual terrain type
            Point imageCoordinates = directions.getCoordinates();
            terrainImage = terrainImageProvider.get(terrainType).getImage(imageCoordinates);
            // Paint terrain image
            canvas.drawBitmap(terrainImage, pos.x, pos.y, null);
//            repaint(pos.x, pos.y, terrainImage.getWidth(), terrainImage.getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
        Log.d("MapImageView", "Got a touch event: " + event.getAction());
        return super.onTouchEvent(event);

    }

    public Point tileToPixel(int column, int row) {
        Point pixel = new Point();
        pixel.x = hexGridWidth * column;
        if (Util.isOdd(column)) pixel.y = hexRectHeight * row;
        else pixel.y = hexRectHeight * row + hexApotheme;
        return pixel;
    }

    public Point pixelToTile(int x, int y) {
        double hexRise = (double) hexApotheme / (double) hexOffset;
        Point p = new Point(x / hexGridWidth, y / hexRectHeight);
        Point r = new Point(x % hexGridWidth, y % hexRectHeight);
        Direction direction;
        if (Util.isOdd(p.x)) { //odd column
            if (r.y < -hexRise * r.x + hexApotheme) {
                direction = Direction.NW;
            } else if (r.y > hexRise * r.x + hexApotheme) {
                direction = Direction.SW;
            } else {
                direction = Direction.C;
            }
        } else { //even column
            if (r.y > hexRise * r.x && r.y < -hexRise * r.x + hexRectHeight) {
                direction = Direction.NW;
            } else if (r.y < hexApotheme) {
                direction = Direction.N;
            } else direction = Direction.C;
        }
        return new Point(direction.getNeighborCoordinates(p));
    }

    public boolean tileIsWithinBoard(Point coordinates) {
        int column = coordinates.x;
        int row = coordinates.y;
        return (column >= 0 && column < width) && (row >= 0 && row < height);
    }
}
