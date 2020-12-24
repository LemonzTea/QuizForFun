package ksh.clement.quizforfun;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewResultsActivity extends AppCompatActivity {

    // UI Elements
    TextView totalScoreLabel;
    Button dateButton, mathButton, geographyButton, literatureButton;
    ListView listview;

    // Database Helper
    private DatabaseHelper myDB;

    // Other Variables
    ArrayList<Button> buttons = new ArrayList<Button>();
    ArrayList<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);

        // Link UI Elements
        totalScoreLabel = (TextView) findViewById(R.id.ViewResultActivity_TextView_totalScore);
        dateButton = (Button) findViewById(R.id.ViewResultActivity_Button_sortDate);
        mathButton = (Button) findViewById(R.id.ViewResultActivity_Button_sortSubjectMath);
        geographyButton = (Button) findViewById(R.id.ViewResultActivity_Button_sortSubjectGeography);
        literatureButton = (Button) findViewById(R.id.ViewResultActivity_Button_sortSubjectLiterature);
        listview = (ListView) findViewById(R.id.ViewResultActivity_ListView_resultList);

        // Connect to db
        myDB = new DatabaseHelper(this, null, 1);

        // Set Action Bar back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Add Buttons into ArrayList
        buttons.add(dateButton);
        buttons.add(mathButton);
        buttons.add(geographyButton);
        buttons.add(literatureButton);

        // Change ActionBar Title
        getSupportActionBar().setTitle("View Results");

        // Set Total Score
        totalScoreLabel.setText(String.valueOf(myDB.getUserTotalScore(User.getCurrentUser())));

        // get Results
        scores = myDB.getUserResults(User.getCurrentUser());


        // Set Result List
        setResultList(sort(scores, "date"));


        // Add action listener to buttons
        for (Button button: buttons) {
            button.setOnClickListener(listener);
        }
    }


    // Action Listener for Buttons
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Change Button Color
            changeButtonColor(v);

            String criteria = "date";

            switch(v.getId()) {
                case R.id.ViewResultActivity_Button_sortSubjectMath:
                    criteria = Subjects.Math.toString();
                    break;
                case R.id.ViewResultActivity_Button_sortSubjectGeography:
                    criteria = Subjects.Geography.toString();
                    break;
                case R.id.ViewResultActivity_Button_sortSubjectLiterature:
                    criteria = Subjects.Literature.toString();
                    break;

            }
            // Update List
            setResultList(sort(scores, criteria));

        }
    };

    // Change button colors based on selection
    private void changeButtonColor(View v) {
        for (Button button: buttons) {
            // Set button background to drawable
            Drawable buttonDrawable = button.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);

            // Set Color
            if (button.getId() == v.getId()) {
                DrawableCompat.setTint(buttonDrawable, ContextCompat.getColor(ViewResultsActivity.this, R.color.faded_green));
            } else {
                DrawableCompat.setTint(buttonDrawable, ContextCompat.getColor(ViewResultsActivity.this, android.R.color.darker_gray));
            }

            // Set Background
            button.setBackground(buttonDrawable);
        }
    }

    // Set listview to display results
    private void setResultList(ArrayList<Score> scores) {
        ArrayAdapter adapter = new ArrayAdapter<Score>(this, android.R.layout.simple_list_item_1, scores);
        listview.setAdapter(adapter);
    }

    private ArrayList<Score> sort(ArrayList<Score> scores, String criteria) {
        ArrayList<Score> newList = new ArrayList<Score>();
        if (!criteria.equals("date")) {
            for (Score score : scores) {
                if (score.getSubject().equals(criteria)) {
                    newList.add(score);
                }
            }
        } else {
            newList = scores;
        }

        Collections.sort(newList, comparator);
        return newList;
    }

    private Comparator comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Score s1 = (Score) o1;
            Score s2 = (Score) o2;

            String s1datetime = String.format("%02d%02d%02d%02d%02d%02d",
                    s1.getYear(), s1.getMonthNumber(), s1.getDate(),
                    s1.getHour(), s1.getMin(), s1.getSec());
            String s2datetime = String.format("%02d%02d%02d%02d%02d%02d",
                    s2.getYear(), s2.getMonthNumber(), s2.getDate(),
                    s2.getHour(), s2.getMin(), s2.getSec());

            return s2datetime.compareTo(s1datetime);
        }
    };
}

