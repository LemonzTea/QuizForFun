package ksh.clement.quizforfun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LoadScreen extends AppCompatActivity {

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        // Hide ActionBar
        getSupportActionBar().hide();

        // Loads / Create Database
        myDB = new DatabaseHelper(this, null, 1);
        myDB.getWritableDatabase();

        // Close Current Page after 2 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                goToLogin();
            }
        }, 2000);   // 2 seconds
    }

    private void goToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}