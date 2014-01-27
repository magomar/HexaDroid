package deludobellico.hexadroid.app.util;

import android.content.Context;

import deludobellico.hexadroid.app.util.ImageProvider;

/**
 * Created by mario on 3/01/14.
 */
public interface ImageProviderFactory {
    /**
     * @return the filename.
     */
    String getFilename();

    /**
     * @return an image provider
     */
    ImageProvider createImageProvider(Context context);
}
