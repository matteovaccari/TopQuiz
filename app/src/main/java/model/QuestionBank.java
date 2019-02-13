package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;

      //Mélange la liste de questions
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {

        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        } // Si j'ai fais le tour des questions l'index repart à la première

        //içi l'incrémentation me pousse à la prochaine question
        return mQuestionList.get(mNextQuestionIndex++);
    }



}