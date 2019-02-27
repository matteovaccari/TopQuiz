package controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.matt.android.topquiz.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.google.gson.Gson;

import model.User;

public class LadderBordActivity extends AppCompatActivity {

    private TextView mRankTextView;
    private Button mRankButtonScore;
    private Button mRankButtonName;
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder_bord);

        mRankTextView = (TextView) findViewById(R.id.activity_ranking_textview);
        mRankButtonScore = (Button) findViewById(R.id.activity_ranking_sort_by_score);
        mRankButtonName = (Button) findViewById(R.id.activity_ranking_sort_by_name);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (mPreferences.contains("PREF_KEY_LADDERBORD")) {
            // On créé un objet Gson afin de convertir la nouvelle liste en json pour la sauvegarder dans les prefs
            Gson gson = new Gson();

            // On récupère la liste Ladderbord courante en json depuis les sharedPref et on la convertit en List
            String json = mPreferences.getString("PREF_KEY_LADDERBORD", null);
            Type type = new TypeToken<List<User>>() {
            }.getType();
            final List<User> Ladderbord = gson.fromJson(json, type);
            // On trie la liste par score
            sortByScore(Ladderbord);

            // On affiche la liste
            displayLadderBord(Ladderbord);

            mRankButtonScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // On trie par score puis on affiche la liste
                    sortByScore(Ladderbord);
                    displayLadderBord(Ladderbord);
                }

            });
            mRankButtonName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // On trie par nom puis on affiche
                    sortByName(Ladderbord);
                    displayLadderBord(Ladderbord);
                }
            });
        }
    }
    private void displayLadderBord (List<User> LadderBord) {
        // On utilise StringBuilder pour générer dynamiquement la textview selon la liste obtenue
        StringBuilder builder = new StringBuilder();

        for(int i=0; i<LadderBord.size(); i++){

            builder.append((i + 1) + ". " + LadderBord.get(i).getFirstName() + " (" + LadderBord.get(i).getScore() + ")");
            if (i < LadderBord.size() - 1){ builder.append("\n\n"); }
        }

        mRankTextView.setText(builder.toString());
    }
    private void sortByScore (List<User> LadderBord) {
        // on trie la liste par score décroissant puis par ordre alphabétique croissant afin de placer en 1er
        // le plus grand score et si égalité le 1er nom par ordre alphabétique
        Collections.sort(LadderBord, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if (user2.getScore() != user1.getScore()) {
                    return user2.getScore() - user1.getScore();
                }
                else {
                    return user1.getFirstName().compareTo(user2.getFirstName());
                }
            }
        });
    }

    private void sortByName (List<User> LadderBord) {
        // on trie la liste par ordre alphabétique croissant puis par score décroissant afin de placer en 1er
        // le 1er nom par ordre alphabétique et si égalité le plus grand score
        Collections.sort(LadderBord, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if (user2.getFirstName() != user1.getFirstName()) {
                    return user1.getFirstName().compareTo(user2.getFirstName());
                }
                else {
                    return user2.getScore() - user1.getScore();
                }
            }
        });
    }
}


