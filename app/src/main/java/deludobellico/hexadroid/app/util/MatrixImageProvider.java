package deludobellico.hexadroid.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import java.lang.ref.SoftReference;


/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class MatrixImageProvider implements ImageProvider {
    private Context context;
    private final String filename;
    /**
     * The Size of the sprite image in pixels
     */
    private final Dimension imageSize;
    private final int rows;
    private final int columns;
    /**
     * The Size of the image where the sprites are stored
     */
    private final Dimension fullImageSize;
    private SoftReference<Bitmap> image;


    public MatrixImageProvider(String filename, int rows, int columns, int fullImageWidth, int fullImageHeight, Context context) {
        this.filename = filename;
        this.rows = rows;
        this.columns = columns;
        fullImageSize = new Dimension(fullImageWidth, fullImageHeight);
        imageSize = new Dimension(fullImageWidth / columns, fullImageHeight / rows);
        this.context = context;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public Bitmap getImage(int column, int row) {
        Bitmap bi;
        if (image == null || image.get() == null) {
            bi = loadGraphics();
            image = new SoftReference<Bitmap>(bi);
        } else {
            bi = image.get();
        }
        try {
            assert bi != null;
            Bitmap result = Bitmap.createBitmap(bi, column * imageSize.width, row * imageSize.height,
                    imageSize.width, imageSize.height);
            return result;
        } catch (Exception e) {
            Log.d("MatrixImageProvider", " Error getting subimage from " + filename);
        }
        return null;
    }

    @Override
    public Bitmap getImage(int index) {
        int column = index / rows;
        int row = index % rows;
        return getImage(column, row);
    }

    @Override
    public Bitmap getImage(Point coordinates) {
        return getImage(coordinates.x, coordinates.y);
    }

    @Override
    public Bitmap getFullImage() {
        Bitmap bi;
        if (image == null || image.get() == null) {
            bi = loadGraphics();
            image = new SoftReference<Bitmap>(bi);
        } else {
            bi = image.get();
        }
        return bi;
    }

    @Override
    public Dimension getImageSize() {
        return imageSize;
    }

    @Override
    public Dimension getFullImageSize() {
        return fullImageSize;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getRows() {
        return rows;
    }

    private Bitmap loadGraphics() {
        Bitmap bitmap = Util.loadImage(context,filename);
        if (bitmap == null) {
            Log.e("MatrixImageProvider","Image cannot be loaded " + filename);
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            return Bitmap.createBitmap(fullImageSize.width, fullImageSize.height, config);
        }
        return bitmap;
    }
//    public void saveGraphics(RenderedImage image, String path, FileIO fileSystem) {
//        File file = fileSystem.getFile(path, filename);
//        FileIO.saveImage(image, file);
//    }

}
