package yourowngame.com.yourowngame.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.viewAdapter.SliderAdapter;

public class SwipeFruitsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout sideDots;
    private SliderAdapter adapter;

    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_fruits);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sideDots = (LinearLayout) findViewById(R.id.sideDots);

        adapter = new SliderAdapter(this);
        viewPager.setAdapter(adapter);

        /** adds the dots at page-end!*/
        addDotsIndicator();

    }

    public void addDotsIndicator(){
        dots = new TextView[3];

        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            sideDots.addView(dots[i]);
        }
    }

}
