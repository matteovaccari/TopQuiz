package model;

public class User {
    private String mFirstName;
    private int mScore;

    public User() {
        mFirstName = null;
        mScore = 0;
    }

    public User(String firstName, int score) {
        mFirstName = firstName;
        mScore = score;
    }
    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String name) {
        this.mFirstName = name;
    }
}
