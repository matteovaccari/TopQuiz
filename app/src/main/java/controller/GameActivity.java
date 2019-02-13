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

        return new QuestionBank(Arrays.asList(question1 /*,question2, */));

    }
}
