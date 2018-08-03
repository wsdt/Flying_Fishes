package yourowngame.com.yourowngame.classes.background.layers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;

import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.background.Background;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

@Enhance (message = {"Maybe create an alternative constructor for an URI/URL to load a bg-image from " +
        "the web (making the apk smaller).", "E.g. api key of pexels (but here attribution required?!!): " +
        "https://www.pexels.com/de/api/documentation/"}, priority = Enhance.Priority.MEDIUM, byDeveloper = Constants.Developers.WSDT)
public class BL_FullscreenImage extends Background {
    private static final String TAG = "BL_FullscreenImage";
    private int resBgImage; //normal drawable resource int
    private static Bitmap initializedBgImage;

    /**
     * @param bgImage: Background image for whole screen
     *               Recommended resolutions to support almost all screens: (w/h) --> for Landscape mode
     *                   xxxhdpi: 1920x1280 px
     *               --> ONLY place biggest resolution, bc. we scale regardless of the size (with that we make the apk smaller)
     *
     *               Recommended source: pexels.com (no attribution required and free to use)
     *
     *               IMPORTANT: DO NOT add too much such huge images to the app as the app grows with
     *               each image (maybe really download images on app(!)/level start).
     */
    public BL_FullscreenImage(@NonNull Activity activity, int bgImage) {
        super(activity);
        this.setResBgImage(bgImage);
    }

    @Override
    public void update() {
    } //not necessary for this layer (lowest layer)

    @Override
    public void draw() {

        this.getCanvas().drawBitmap(this.getInitializedBgImage(),0,0,null);
        //this.getCanvas().drawBitmap(this.getInitializedBgImage(),0,0,null); //fullscreen (left and top 0 to cover everything)
    }


    /** No param to provide. */
    @Override
    public void initialize() {
        if (!isInitialized()) {
            try {
                setInitializedBgImage(getCraftedDynamicBitmap(this.getActivity(), this.getResBgImage(), null, DrawableSurfaces.getDrawWidth(), DrawableSurfaces.getDrawHeight())); //only here in initialize() for performance enhancement
                this.setInitialized(true);
            } catch (NullPointerException e) {
                Log.e(TAG, "initialize: Could not initialize Static background!");
                e.printStackTrace();
            }
        } else {
            Log.w(TAG, "initialize: Already initialized.");
        }
    }

    @Override
    public boolean cleanup() {
        //its more efficient to do here nothing for this layer
        return true;
    }

    //GETTER/SETTER -------------------------------------------------------------
    public int getResBgImage() {
        return resBgImage;
    }

    public void setResBgImage(int resBgImage) {
        this.resBgImage = resBgImage;
    }

    public static Bitmap getInitializedBgImage() {
        return initializedBgImage;
    }

    public static void setInitializedBgImage(Bitmap initializedBgImage) {
        BL_FullscreenImage.initializedBgImage = initializedBgImage;
    }
}
