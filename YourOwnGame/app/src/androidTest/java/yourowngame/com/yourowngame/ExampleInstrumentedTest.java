package yourowngame.com.yourowngame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import yourowngame.com.yourowngame.activities.DrawableSurfaceActivity;
import yourowngame.com.yourowngame.gameEngine.DrawableSurfaces;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("yourowngame.com.yourowngame", appContext.getPackageName());
    }

    @Test
    public void setInitialDim() {
        Context c = InstrumentationRegistry.getTargetContext();
        c.startActivity(new Intent(c, DrawableSurfaceActivity.class));
        assertNotEquals(0,DrawableSurfaces.getDrawHeight());
        assertNotEquals(0,DrawableSurfaces.getDrawWidth());
    }
}
