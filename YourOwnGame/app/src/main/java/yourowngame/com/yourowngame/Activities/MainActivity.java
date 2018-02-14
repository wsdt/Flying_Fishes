package yourowngame.com.yourowngame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yourowngame.com.yourowngame.R;

/**
 *
 *
 * Welcomes the user
 *
 */

public class MainActivity extends AppCompatActivity {

    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GameViewActivity.class));
            }
        });

    }






}
