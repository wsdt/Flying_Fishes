package yourowngame.com.yourowngame.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yourowngame.com.yourowngame.R;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
    }

    private void printHighscoreEntry() {
       //TODO: implement sharedprefs before
    }

    public void printAllHighscoreEntries() {
        //TODO: foreach do printHighscoreEntry()
    }
}
