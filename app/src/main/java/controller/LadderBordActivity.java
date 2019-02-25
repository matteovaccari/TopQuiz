package controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.matt.android.topquiz.R;

public class LadderBordActivity extends AppCompatActivity {
    private TextView player1Score;
    private TextView player2Score;
    private TextView player3Score;
    private TextView player4Score;
    private TextView player5Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder_bord);
        player1Score = (TextView) findViewById(R.id.ladderbord_player1_id);
        player2Score = (TextView) findViewById(R.id.ladderbord_player2_id);
        player3Score = (TextView) findViewById(R.id.ladderbord_player3_id);
        player4Score = (TextView) findViewById(R.id.ladderbord_player4_id);
        player5Score = (TextView) findViewById(R.id.ladderbord_player5_id);

    }
}
