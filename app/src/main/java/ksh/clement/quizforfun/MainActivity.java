package ksh.clement.quizforfun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    private ImageButton mathImgBtn, geographyImgBtn, literatureImgBtn, viewResultImgBtn;
    private TextView mathLabel, geographyLabel, literatureLabel, viewResultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI Elements
        mathImgBtn = (ImageButton) findViewById(R.id.MainActivity_ImageButton_Math);
        geographyImgBtn = (ImageButton) findViewById(R.id.MainActivity_ImageButton_Geography);
        literatureImgBtn = (ImageButton) findViewById(R.id.MainActivity_ImageButton_Literature);
        viewResultImgBtn = (ImageButton) findViewById(R.id.MainActivity_ImageButton_ViewResults);
        mathLabel = (TextView) findViewById(R.id.MainActivity_TextView_Math);
        geographyLabel = (TextView) findViewById(R.id.MainActivity_TextView_Geography);
        literatureLabel = (TextView) findViewById(R.id.MainActivity_TextView_Literature);
        viewResultLabel = (TextView) findViewById(R.id.MainActivity_TextView_ViewResults);

        // Add Action Listeners
        mathImgBtn.setOnClickListener(listener);
        geographyImgBtn.setOnClickListener(listener);
        literatureImgBtn.setOnClickListener(listener);
        viewResultImgBtn.setOnClickListener(listener);
        mathLabel.setOnClickListener(listener);
        geographyLabel.setOnClickListener(listener);
        literatureLabel.setOnClickListener(listener);
        viewResultLabel.setOnClickListener(listener);
    }

    // Add menu
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu - adds items to the app bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // Menu Action Listener
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.actions_instructions:
                Intent intent = new Intent(this, RulesActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_changePassword:
                Intent intent_1 = new Intent(getApplicationContext(), UserDetailsActivity.class);
                startActivity(intent_1);
                return true;

            case R.id.action_logout:
                Intent intent_2;
                finish();
                intent_2 = new Intent(this, LogoutActivity.class);
                startActivity(intent_2);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Intent for QuizPrep


            // Intent for Results OR set Extra for QuizPrep
            switch (v.getId()){
                case R.id.MainActivity_ImageButton_Math:
                case R.id.MainActivity_TextView_Math:
                    prepForQuiz(Subjects.Math.toString());
                    break;

                case R.id.MainActivity_ImageButton_Geography:
                case R.id.MainActivity_TextView_Geography:
                    prepForQuiz(Subjects.Geography.toString());
                    break;

                case R.id.MainActivity_ImageButton_Literature:
                case R.id.MainActivity_TextView_Literature:
                    prepForQuiz(Subjects.Literature.toString());
                    break;

                case R.id.MainActivity_ImageButton_ViewResults:
                case R.id.MainActivity_TextView_ViewResults:
                    Intent intent = new Intent(getApplicationContext(), ViewResultsActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void prepForQuiz(String subject) {
        Intent intent = new Intent(this, QuizPrepActivity.class);
        intent.putExtra(QuizPrepActivity.QUIZ_SUBJECT, subject);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Logout Now?")
                .setCancelable(true)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                        startActivity(intent);
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
}