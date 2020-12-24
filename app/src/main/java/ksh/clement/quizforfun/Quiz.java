package ksh.clement.quizforfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Quiz {
    // FINAL VALUES
    public static final int NUMBER_OF_QUESTIONS = 5;
    public static final int Correct = 0;
    public static final int Wrong = 1;

    // Quiz Setup
    private static String Subject = "";
    private static Question currentQuestion;
    private static int CurrentQuestionNumber = 0;
    private static ArrayList<Question> Questions = null;
    private static int Score = 0;
    private static User CurrentUser;
    private static Date Timestamp;
    private static int correctAnswered = 0;

    public static void startQuiz(String subject, ArrayList<Question> questions) {
        Subject = subject;
        Questions = questions;
        Score = 0;
        CurrentUser = User.getCurrentUser();
        Timestamp = Calendar.getInstance().getTime();
        correctAnswered = 0;
        CurrentQuestionNumber = 0;
    }

    // Returns a question. Null is returned if Quiz Question Limit is reached
    public static void nextQuestion() {
        if (CurrentQuestionNumber < NUMBER_OF_QUESTIONS) {

            // Get a random Question.
            int random = (int) (Math.random() * Questions.size());
            Question selectedQuestion = Questions.get(random);

            // Remove selectedQuestion
            Questions.remove(selectedQuestion);

            // Increase current question count
            CurrentQuestionNumber++;

            // Return random question
            currentQuestion = selectedQuestion;

        } else {
            currentQuestion = null;
        }
    }

    public static String getSubject() {
        return Subject;
    }

    public static Question getCurrentQuestion() {
        return currentQuestion;
    }

    public static int getCurrentQuestionNumber() {
        return CurrentQuestionNumber;
    }

    public static void quitQuiz(Context context, Activity currentActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(
                "Leaving now will nullify this quiz attempt.\n" +
                "Current Attempt will not be saved.\n\n" +
                "Do you wish to proceed?")
                .setTitle("End Quiz Attempt")
                .setCancelable(true)
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Reset Values
                        Subject = "";
                        currentQuestion = null;
                        CurrentQuestionNumber = 0;
                        ArrayList<Question> Questions = null;
                        Score = 0;
                        CurrentUser = null;
                        Timestamp = null;
                        int correctAnswered = 0;

                        // End current activity
                        currentActivity.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Create and show alert
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static void endQuiz(DatabaseHelper myDB) {
        // To Save to database
        myDB.addNewQuizResult(User.getCurrentUser(), Subject, Timestamp, Score);
    }

    // Changes the score based on answer
    public static void submitAnswer(int answer) {
        if (answer == Correct) {
            Score += 5;
            correctAnswered++;
        } else {
            Score = Score - 2 <= 0 ? 0 : Score - 2;
        }
    }

    public static int getScore() {
        return Score;
    }

    public static int getCorrectAnswered() {
        return correctAnswered;
    }
}
