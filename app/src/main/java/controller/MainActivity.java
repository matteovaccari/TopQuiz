package controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.matt.android.topquiz.R;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

   private TextView mGreetingText;   // Déclaration des variables
   private EditText mNameInput;
   private Button mPlayButton;
   private User mUser;
   private Button mLadderBordButton;
   public SharedPreferences mPreferences;
   private ArrayList<User> LadderBord;

    public static final int GAME_ACTIVITY_REQUEST_CODE = 2;
   public static final int LADDERBORD_ACTIVITY_REQUEST_CODE = 3;

   public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";
   public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
   public static final String PREF_KEY_LADDERBORD = "PREF_KEY_LADDERBORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out.println("MainActivity::OnCreate()");

        mUser = new User();
        LadderBord = new ArrayList<>();

        mPreferences = getPreferences(MODE_PRIVATE);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);   // Association des variables aux Vues graphiques
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton =(Button) findViewById(R.id.activity_main_play_btn);
        mLadderBordButton =(Button) findViewById(R.id.activity_main_ladderbord);

        mPlayButton.setEnabled(false);  //Désactivation du bouton

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(!mPreferences.contains("PREF_KEY_LADDERBORD")) { // Si les prefs ne contiennent pas la clé pref du ladder, le bouton est grisé
            mLadderBordButton.setVisibility(View.GONE);
        }

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() > 0 );  //Réactive le boutton si je click dessus
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {  //Notifie à chaque clique
            @Override
            public void onClick(View v) {
                // The user just clicked
                String firstname =mNameInput.getText().toString();
                mUser.setFirstName(firstname);

                mPreferences.edit().putString(PREF_KEY_FIRSTNAME,mUser.getFirstName()).apply();

                Intent gameActivity = new Intent (MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity,GAME_ACTIVITY_REQUEST_CODE);  // Quand on clique, switch d'activités.

            }
        });

        mLadderBordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ladderBordActivity = new Intent(MainActivity.this,LadderBordActivity.class);
                startActivityForResult(ladderBordActivity,LADDERBORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            int score = data.getIntExtra(GameActivity.intendID,0); // Récup du score

            mPreferences.edit().putInt("score",score).apply(); //Score sur l'écran principal

            // On prépare l'objet User à être inséré dans le classement
            mUser.setScore(score);
            Character first = Character.toUpperCase(mUser.getFirstName().charAt(0)); //On met le username en Majuscule
            mUser.setFirstName(first + mUser.getFirstName().substring(1,mUser.getFirstName().length()));

            //Objet Json pour convertir la liste et l'envoyer dans les sharedPrefs
            Gson gson = new Gson();

            // On récupère la liste Ladderbord courante en json depuis les sharedPref et on la convertit en List
            if (mPreferences.contains("PREF_KEY_LADDERBORD")) {
                String json = mPreferences.getString("PREF_KEY_LADDERBORD", null);
                Type type = new TypeToken<List<User>>() {}.getType();
                LadderBord = gson.fromJson(json, type);

                sortByScoreUp(LadderBord); // On trie pour comparer le current score avec le derier de la liste
            }
            // Si la liste est vide on ajoute direct
            if (LadderBord.isEmpty()) {
                LadderBord.add(new User(mUser.getFirstName(), mUser.getScore()));
            }

            //Sinon si le score est >= au plus petit score de la liste (1er car la liste est triée)
            // On ajoute l'user à la liste
            else if (score >= LadderBord.get(0).getScore() || LadderBord.size() <= 6) {
                LadderBord.add(new User(mUser.getFirstName(), mUser.getScore()));

                // Si la liste est égale à 6 on enlève le 1er score de la liste car la liste est triée
                if (LadderBord.size() == 6) {
                    LadderBord.remove(0);
                }
            }
            String jsonLeaderboard = gson.toJson(LadderBord); // Liste --> Json
            mPreferences.edit().putString(PREF_KEY_LADDERBORD, jsonLeaderboard).apply();  //Json --> Prefs
            mLadderBordButton.setVisibility(View.VISIBLE); // On réactive le bouton des score
            welcomeBack();
        }
    }


    public void welcomeBack() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Le dernier joueur était " + firstname
                    + "!\nEt avait réalisé un score de " + score
                    + "/15, peut-tu mieux faire ?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    private void sortByScoreUp (List<User> LadderBord) {
        // Sort la liste par score croissant puis par ordre alphabétique décroissant afin de retirer
        // le plus petit score et si égalité le dernier nom par ordre alphabétique
        Collections.sort(LadderBord, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                if (user2.getScore() != user1.getScore()) {
                    return user1.getScore() - user2.getScore();
                }
                else {
                    return user2.getFirstName().compareTo(user1.getFirstName());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }
}
