package controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.matt.android.topquiz.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.google.gson.Gson;

public class LadderBordActivity extends AppCompatActivity {
    private TextView player1;
    private TextView player2;
    private TextView player3;
    private TextView player4;
    private TextView player5;
    private String currentPlayerName;
    private int currentPlayerScore;
    private Map<String, Integer> playerList = new HashMap<>();
    private List<TextView> playerListTextViews;
    private SharedPreferences mPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder_bord);

        player1 = (TextView) findViewById(R.id.ladderbord_player1_id);
        player2 = (TextView) findViewById(R.id.ladderbord_player2_id);
        player3 = (TextView) findViewById(R.id.ladderbord_player3_id);
        player4 = (TextView) findViewById(R.id.ladderbord_player4_id);
        player5 = (TextView) findViewById(R.id.ladderbord_player5_id);


    }

}
