package yourowngame.com.yourowngame.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.manager.AdManager;
import yourowngame.com.yourowngame.classes.manager.storage.SharedPrefStorageMgr;

//TODO: This class might/should be connected/reviewed by Google-Play-Services in future or similar!

/** Class not fine-tuned yet (strings.xml etc.), because this activity will be changed completely in
 * future (nicer UI etc.) */
public class HighscoreActivity extends AppCompatActivity {
    private SharedPrefStorageMgr sharedPreferencesStorageMgr;
    private LinearLayout highscoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        setSharedPreferencesStorageMgr(new SharedPrefStorageMgr(getApplicationContext()));
        setHighscoreList((LinearLayout) findViewById(R.id.highscoreActivity_highscoreList));

        printAllHighscoreEntries();

        // Load ads
        AdManager.loadBannerAd(getApplicationContext(),(RelativeLayout) findViewById(R.id.highscoreActivity_RL));
    }

    private void printHighscoreEntry(int points) {
        TextView highscoreEntry = new TextView(this);
        highscoreEntry.setTextColor(getResources().getColor(R.color.colorWhite));
        highscoreEntry.setText(points+" Points");
        getHighscoreList().addView(highscoreEntry);
    }

    public void printAllHighscoreEntries() {
        List<Integer> highscoreList = this.getSharedPreferencesStorageMgr().getSortedHighscoreEntries();
        for (int points : highscoreList) {
            printHighscoreEntry(points);
        }
        if (highscoreList.size() <= 0) {
            TextView noEntryFound = new TextView(this);
            noEntryFound.setTextColor(getResources().getColor(R.color.colorWhite));
            noEntryFound.setText("No entry found.");
            getHighscoreList().addView(noEntryFound);
        }
    }


    //GETTER/SETTER --------------------------------------
    public SharedPrefStorageMgr getSharedPreferencesStorageMgr() {
        return sharedPreferencesStorageMgr;
    }

    public void setSharedPreferencesStorageMgr(SharedPrefStorageMgr sharedPreferencesStorageMgr) {
        this.sharedPreferencesStorageMgr = sharedPreferencesStorageMgr;
    }

    public LinearLayout getHighscoreList() {
        return highscoreList;
    }

    public void setHighscoreList(LinearLayout highscoreList) {
        this.highscoreList = highscoreList;
    }
}
