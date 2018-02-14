/*package yourowngame.com.yourowngame.gameview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

//TODO: In my point of view not the best method (see WhatsApp)

@SuppressLint("AppCompatCustomView")
public class GameView extends ImageView {

    //AttributSet not needed, gets created when the activity gets created!
    public GameView(Context context) {
        super(context);
    }


    //Get filling!
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(5);

        canvas.drawLine(0, 0, getWidth(), getHeight(), p);
        p.setTextSize(75);
        canvas.drawText("Create GameObjects here", 0, getHeight()-5, p);

    }
}
*/