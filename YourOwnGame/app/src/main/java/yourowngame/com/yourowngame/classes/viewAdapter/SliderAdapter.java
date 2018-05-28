package yourowngame.com.yourowngame.classes.viewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.counters.FruitCounter;
import yourowngame.com.yourowngame.classes.counters.Highscore;
import yourowngame.com.yourowngame.classes.gamelevels.LevelManager;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == (RelativeLayout) obj;
    }

    //Here we MUST return the number of views to switch!
    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView fruitImage    = (ImageView) view.findViewById(R.id.fruitImageView);
        TextView amountLbl      = (TextView) view.findViewById(R.id.amountCollectedLbl);
        TextView descriptionLbl = (TextView) view.findViewById(R.id.descriptionLbl);

        amountLbl.setText(context.getResources().getString(R.string.sliderViewAmountCollected, FruitCounter.getTotalAmountOfMeloons()));  //beware of OO
        descriptionLbl.setText("la dkfjas ökfjaskdfj askldjf askdjf askdjf askdjf asködjf asökldjf aksöldjf");    /**TODO --> we need to differ the text, we could do it like slide_headings[1]... but localization would be gone! */

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object obj) {
        container.removeView((RelativeLayout) obj);
    }
}
