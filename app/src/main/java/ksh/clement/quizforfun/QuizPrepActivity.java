package ksh.clement.quizforfun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizPrepActivity extends AppCompatActivity {
    // INTENT EXTRA
    public static final String QUIZ_SUBJECT = "SUBJECT";

    // UI Elements
    private TextView titleLabel;
    private Button startButton;
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_prep);

        // Link UI Elements
        titleLabel = (TextView) findViewById(R.id.QuizPrep_TextView_Title);
        startButton = (Button) findViewById(R.id.QuizPrep_Button_Start);

        // Get Intent Extras
        String subject = getIntent().getExtras().get(QUIZ_SUBJECT).toString();

        // Link Database
        myDB = new DatabaseHelper(this, null, 1);

        // Set Action Bar back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Title
        titleLabel.setText(subject + " Quiz");

        // Set Action Listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Quiz Question
                Quiz.startQuiz(subject, myDB.getQuizQuestions(subject));
                Quiz.nextQuestion();

                // Start Quiz
                Intent intent = new Intent(getApplicationContext(), QuizQuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}