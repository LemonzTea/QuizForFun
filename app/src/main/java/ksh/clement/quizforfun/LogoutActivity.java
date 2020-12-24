package ksh.clement.quizforfun;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoutActivity extends AppCompatActivity {
    // UI Element
    TextView messageBox;

    // Database Helper
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // Link UI Element
        messageBox = (TextView) findViewById(R.id.LogoutActivity_TextView_Message);

        // Connect to DB
        myDB = new DatabaseHelper(this, null, 1);

        // Set Action Bar back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display Message
        messageBox.setText(String.format("%s, you have overall %d points", User.getCurrentUser().getUsername(), myDB.getUserTotalScore(User.getCurrentUser())));

        // Log User off
        User.logout();

        // Closes the screen after 3 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}