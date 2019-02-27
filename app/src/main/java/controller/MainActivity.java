package controller;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.matt.android.topquiz.R;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.User;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

   private TextView mGreetingText;   // Déclaration des variables
   private EditText mNameInput;
   private Button mPlayButton;
   private User mUser;
   private Button mLadderBordButton;
   public static final int GAME_ACTIVITY_REQUEST_CODE = 2;
   public String KEY_PREFS = mUser.getFirstName();
   private SharedPreferences mPreferences;
   private LadderBordActivity mLadderBordActivity;
   private Map<String, Integer> playerList = new HashMap<>();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            int score = data.getIntExtra(GameActivity.intendID,0); // Récup du score

            mPreferences.edit().putInt("score",score).apply(); //Score sur l'écran principal

            welcomeBack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out.println("MainActivity::OnCreate()");

        mUser = new User();

        mPreferences = getPreferences(MODE_PRIVATE);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);   // Association des variables aux Vues graphiques
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton =(Button) findViewById(R.id.activity_main_play_btn);
        mLadderBordButton =(Button) findViewById(R.id.activity_main_ladderbord);

        mPlayButton.setEnabled(false);  //Désactivation du bouton

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

                mPreferences.edit().putString("Firstname",mUser.getFirstName()).apply();

                Intent gameActivity = new Intent (MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity,GAME_ACTIVITY_REQUEST_CODE);  // Quand on clique, switch d'activités.

            }
        });

        mLadderBordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ladderBordActivity = new Intent(MainActivity.this,LadderBordActivity.class);
                startActivity(ladderBordActivity);
            }
        });
    }

    public void welcomeBack() {
        String firstname = mPreferences.getString("Firstname", null);

        if (null != firstname) {
            int score = mPreferences.getInt("score", 0);

            String fulltext = "Le dernier joueur était " + firstname
                    + "!\nEt avait réalisé un score de " + score
                    + "/15, peut-tu mieux faire ?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
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
