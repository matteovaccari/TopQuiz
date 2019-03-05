package controller;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matt.android.topquiz.R;

import java.util.Arrays;

import model.Question;
import model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestion;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private ProgressBar mProgressBar;
    QuestionBank mQuestionBank;
    Question mCurrentQuestion;
    private int mNumberOfQuestions;
    private int mPlayerScore;
    public static final String intendID = "intendID";
    private boolean mEnableTouchEvents;
    public static final String BUNDLE_SCORE = "current score";
    public static final String BUNDLE_CURRENT_QUESTION = "current question";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState != null) {
            mPlayerScore = savedInstanceState.getInt(BUNDLE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_CURRENT_QUESTION);
        } else {
            mNumberOfQuestions = 5;
            mPlayerScore =0;
        }

        mQuestionBank = this.generateQuestions();
        mEnableTouchEvents = true;

        //Branchement des widgets
        mQuestion = (TextView) findViewById(R.id.activity_game_question_text);
        mButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mButton4 = (Button) findViewById(R.id.activity_game_answer4_btn);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Utilisation de la propriété tag pour identifier les bouttons
        mButton1.setTag(0);
        mButton2.setTag(1);
        mButton3.setTag(2);
        mButton4.setTag(3);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_SCORE,mPlayerScore);
        outState.putInt(BUNDLE_CURRENT_QUESTION,mNumberOfQuestions);
        super.onSaveInstanceState(outState);
    }

    private QuestionBank generateQuestions() {

        Question question1 = new Question("Quel est le plus gros animal vivant sur Terre ?",
                Arrays.asList("L'éléphant", "Le Calamar Géant", "La baleine", "Le requin baleine"),2);
        Question question2 = new Question("Quelle est la capitale de l'Australie ?",
                Arrays.asList("Sydney", "Melbourne", "Canberra", "Nyah"),2);
        Question question3 = new Question("Combien y'a-t-il d'étoiles sur le drapeau Américain ?",
                Arrays.asList("49","50","51", "Ca dépend"), 1);
        Question question4 = new Question("Quelle est la lanque principalement parlée en Uruguay ?",
                Arrays.asList("Espagnol", "Anglais", "Portugais", "Uruguayien"), 0);
        Question question5 = new Question ("Quel est le pays oû le taux de bonheur était le plus élevé dans le monde en 2017?",
                Arrays.asList("Danemark", "France", "Etats-Unis", "Japon"), 0);
        Question question6 = new Question ("Quelle place occupe la France dans le classement des pays les plus heureux en 2018 ?",
                Arrays.asList("5ème", "3ème", "9ème", "23ème"), 3);
        Question question7 = new Question("Combien gagne BAYER pharmacologie chaque seconde qui passe ?",
                Arrays.asList("1€", "6€", "15€", "44€"), 3);
        Question question8 = new Question ("Combien d'animaux sont tués chaque seconde pour nourrir l'homme à travers le monde ?",
                Arrays.asList("10", "70","300","2200"), 3);
        Question question9 = new Question ("Quel nom porte le siège de la police londonienne ?",
                Arrays.asList("Buckingham", "Scotland Yard", "Camden Town", "Notting Hill"), 1);
        Question question10 = new Question ("Quel est l'autre nom de l'étude des fossiles:",
                Arrays.asList("La Paléontologie", "La Geologie", "La Spéléologie","L'archéologie"), 0);
        Question question11 = new Question ("Quelle planète du système solaire a une densité inférieure à l'eau ?",
                Arrays.asList("Saturne","Jupiter", "Uranus","Pluton"),0);
        Question question12 = new Question("Combien représente le montant de la fraude fiscale en 2018 ?",
                Arrays.asList("15Mds €", "45Mds €", "70Mds €","100Mds €"), 3);
        Question question13= new Question ("Quel pays est le 3ème plus gros vendeur d'armes au monde ?",
                Arrays.asList("La Chine", "La France", "La Russie", "Les Etats-Unis"), 1);
        Question question14 = new Question ("Quel Pays a vendu des armes à la Syrie entre 2005 et 2009 ?",
                Arrays.asList("L'Arabie Saoudite","La Turquie", "La France","La Russie"),2);

        return new QuestionBank(Arrays.asList(question1,question2,question3,question4,
                question5,question6,question7,question8,question9,question10,question11,question12, question13, question14));

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "Bonne réponse", Toast.LENGTH_SHORT).show();
            mPlayerScore++;
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.win_effect);
            mp.start();

        } else {
            Toast.makeText(this, "Mauvaise Réponse !", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.fail_effect);
            mp.start();
        }

        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               mEnableTouchEvents = true;
                if (--mNumberOfQuestions == 0) {
                    Intent intent = new Intent();
                    intent.putExtra(intendID, mPlayerScore);
                    setResult(RESULT_OK, intent);
                    showEndDialogBox();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();  // Sinon je relance une question
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000);
        ObjectAnimator animation = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 500);
        animation.setDuration(2000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void displayQuestion (final Question question) {  //prend en paramètre une question
        mQuestion.setText(question.getQuestion()); //met à jour l'interface graphique en rajoutant la question entrée en paramètre
        mButton1.setText((CharSequence) question.getChoiceList().get(0)); // de même pour chaque réponse sur chaque boutton
        mButton2.setText((CharSequence) question.getChoiceList().get(1));
        mButton3.setText((CharSequence) question.getChoiceList().get(2));
        mButton4.setText((CharSequence) question.getChoiceList().get(3));
    }

    public void showEndDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //Boite de dialogue de fin
        builder.setTitle("Fin du Jeu !")
                .setMessage("Votre score est de : " + mPlayerScore + "/5")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Ajout item à l'action bar
        getMenuInflater().inflate(R.menu.action_bar_menu,menu);
        return true;
    }

    //Fait l'action correspondante à l'item cliqué
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh_button_id:
                Toast.makeText(this,"Jeu relancé",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
