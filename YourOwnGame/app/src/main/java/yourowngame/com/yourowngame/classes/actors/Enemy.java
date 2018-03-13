package yourowngame.com.yourowngame.classes.actors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.activities.GameViewActivity;
import yourowngame.com.yourowngame.classes.annotations.TestingPurpose;
import yourowngame.com.yourowngame.classes.configuration.Constants;
import yourowngame.com.yourowngame.classes.exceptions.NoDrawableInArrayFound_Exception;
import yourowngame.com.yourowngame.classes.handler.RandomHandler;
import yourowngame.com.yourowngame.gameEngine.GameView;

/**
 * So if we use the following pattern
 *
 * GameObject
 * --Enemy
 * ----RoboticEnemy
 * ----SuperEnemy
 * ----ChemicalEnemy (and so on.. be imaginative!)
 *
 * Drawing, Updating as well as AI-Handling will be processed by the Subclasses,
 * so the Enemy Superclass is more like a "looks-good-no-feature" class,
 * but i would keep it that way without to think.
 *
 *
 */

public abstract class Enemy extends GameObject {
    private static final String TAG = "Enemy";

    public Enemy(double posX, double posY, double speedX, double speedY, int[] img, int rotationDegree, @Nullable String name) {
        super(posX, posY, speedX, speedY, img, rotationDegree, name);
    }

    public abstract void createRandomEnemies(int numberOfEnemies);

    public Enemy(){}
}
