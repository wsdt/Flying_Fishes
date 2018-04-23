package yourowngame.com.yourowngame.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.handler.SharedPrefStorageMgr;

public class HighscoreActivity extends AppCompatActivity {
    private SharedPrefStorageMgr sharedPreferencesStorageMgr;
    private LinearLayout highscoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        setSharedPreferencesStorageMgr(new SharedPrefStorageMgr(this));
        setHighscoreList((LinearLayout) findViewById(R.id.highscoreList));

        printAllHighscoreEntries();
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
