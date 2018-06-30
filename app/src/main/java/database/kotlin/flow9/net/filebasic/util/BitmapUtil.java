package database.kotlin.flow9.net.filebasic.util;

import android.graphics.Bitmap;
import android.view.View;

public class BitmapUtil {

    /**
     * capture View to store at external cache directory
     */
    public static Bitmap getViewBitmap(View view){
        view.clearFocus();
        view.setPressed(false);
        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);

        if(color != 0) {
            view.destroyDrawingCache();
        }

        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        view.getContentDescription();
        view.setWillNotDraw(willNotCache);
        view.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

}
