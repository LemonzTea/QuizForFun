package ksh.clement.quizforfun;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizSummaryActivity extends AppCompatActivity {

    // UI Elements
    TextView textView;
    Button exitButton;

    // Database Helper
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_summary);

        // Link UI Elements
        textView = (TextView) findViewById(R.id.QuizSummaryActivity_TextView_score);
        exitButton = (Button) findViewById(R.id.QuizSummaryActivity_Button_exit);

        // Link to database
        myDB = new DatabaseHelper(this, null, 1);

        // Set Page Title
        getSupportActionBar().setTitle("Attempt Summary");

        // Set Action Bar back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display Result
        textView.setText(String.format(
                "Well Done %s\n\n" +
                "You have finished the \"%s\" quiz with\n" +
                "%10d correct answers\n" +
                "%10d incorrect answers\n" +
                "and have earned %d points for this attempt\n\n" +
                "Overall, you have %d points",
                User.getCurrentUser().getUsername(),
                Quiz.getSubject(),
                Quiz.getCorrectAnswered(),
                Quiz.NUMBER_OF_QUESTIONS - Quiz.getCorrectAnswered(),
                Quiz.getScore(),
                myDB.getUserTotalScore(User.getCurrentUser()) + Quiz.getScore()));

        // Add ActionListener
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Send Quiz Result to Database
        Quiz.endQuiz(myDB);

    }
}