package controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.matt.android.topquiz.R;

import java.util.Arrays;

import model.Question;
import model.QuestionBank;

public class GameActivity extends AppCompatActivity {

    private TextView mQuestion;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    QuestionBank mQuestionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestion = (TextView) findViewById(R.id.activity_game_question_text);
        mButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mButton3 = (Button) findViewById(R.id.activity_game_answer4_btn);

        mQuestionBank = this.generateQuestions();
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

        return new QuestionBank(Arrays.asList(question1 /*,question2, */));

    }
}
