package controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.matt.android.topquiz.R;

import model.User;

public class MainActivity extends AppCompatActivity {

   private TextView mGreetingText;   // Déclaration des variables
   private EditText mNameInput;
   private Button mPlayButton;
    User user;

    User user1 = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);   // Association des variables aux Vues graphiques
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton =(Button) findViewById(R.id.activity_main_play_btn);

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
                Intent gameActivity = new Intent (MainActivity.this, GameActivity.class);
                startActivity(gameActivity);  // Quand on clique, switch d'activités.
                user.setFirstName(mNameInput.getText().toString());  // Set le name de l'user en fonction de ce qui est rentré par l'utilisateur
            }
        });
    }
}
