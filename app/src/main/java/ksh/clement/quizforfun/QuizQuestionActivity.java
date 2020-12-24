package ksh.clement.quizforfun;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizQuestionActivity extends AppCompatActivity {

    // UI Elements
    TextView heading, question, message;
    EditText inputBox;
    Button option1, option2, option3, option4, submitButton;
    Button[] options;

    // Question
    Question randomQuestion;

    // Animation
    Animation animationShow;
    Animation animationHide;

    // Colors
    int colorCorrect;
    int colorIncorrect;
    int colorOptionDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        // Link UI Elements
        heading = (TextView) findViewById(R.id.QuizQuestionActivity_Label_QuizType);
        question = (TextView) findViewById(R.id.QuizQuestionActivity_EditText_Question);
        message = (TextView) findViewById(R.id.QuizQuestionActivity_Label_ErrorMessage);
        inputBox = (EditText) findViewById(R.id.QuizQuestionActivity_EditText_MathAnswer);
        option1 = (Button) findViewById(R.id.QuizQuestionActivity_Button_Option1);
        option2 = (Button) findViewById(R.id.QuizQuestionActivity_Button_Option2);
        option3 = (Button) findViewById(R.id.QuizQuestionActivity_Button_Option3);
        option4 = (Button) findViewById(R.id.QuizQuestionActivity_Button_Option4);
        submitButton = (Button) findViewById(R.id.QuizQuestionActivity_Button_SubmitButton);

        options = new Button[] {option1, option2, option3, option4};

        // Initialise Animations
        animationShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show);
        animationHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);

        // Initialise Colors
        colorCorrect = getResources().getColor(android.R.color.holo_green_light);
        colorIncorrect = getResources().getColor(android.R.color.holo_red_light);
        colorOptionDefault = getResources().getColor(android.R.color.holo_blue_light);

        // Get a random Question
        randomQuestion = Quiz.getCurrentQuestion();

        // Set Views on Page
        heading.setText(String.format("%s Quiz", Quiz.getSubject()));
        setNextQuestion();

        if (Quiz.getSubject().equals(Subjects.Math.toString())) {
            // Remove Options Buttons
            for (Button option : options) {
                option.setVisibility(View.GONE);
            }
        } else {
            // Remove inputBox, error Message and submit button
            inputBox.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
        }

        // Update Button Text
        updateButtonText();

        // Set ActionListener
        option1.setOnClickListener(optionListener);
        option2.setOnClickListener(optionListener);
        option3.setOnClickListener(optionListener);
        option4.setOnClickListener(optionListener);
        submitButton.setOnClickListener(submitListener);
    }

    // Action Listener for options
    private View.OnClickListener optionListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Disable all Buttons
            disableAllButtons();

            // Cast View to Button
            Button selectedButton = (Button) v;

            // Check if selected Button is the correct Answer
            if (selectedButton.getText().toString().equals(randomQuestion.getAnswer())) {
                Quiz.submitAnswer(Quiz.Correct);
                showOptionAnswer(selectedButton, Quiz.Correct);
            } else {
                Quiz.submitAnswer(Quiz.Wrong);
                showOptionAnswer(selectedButton, Quiz.Wrong);
            }


            // Hides the question , Loads the next question
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    animationHide();
                }
            }, 3000);   //3 seconds
        }
    };

    // Action Lister for submit button
    private View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Disable all buttons
            disableAllButtons();

            String answerInput = inputBox.getText().toString();

            if (answerInput.equals("")) {
                Quiz.submitAnswer(Quiz.Wrong);
                showTextFeedback("The answer is " + randomQuestion.getAnswer(), colorIncorrect);

                // Next Question
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        animationHide();
                    }
                }, 3000);   //3 seconds
                return;
            }

            // Check if number is valid
            double answerNum;
            try {
                answerNum = Double.parseDouble(answerInput);

            } catch (NumberFormatException ex) {
                // Show Feedback
                showTextFeedback("Answer must be a valid number. No number or special characters allowed except '-'", colorIncorrect);

                enableAllButtons();
                inputBox.requestFocus();
                return;
            }

            // Clear Error Message
            message.setText("");

            // Submit Answer
            Quiz.submitAnswer(randomQuestion.getAnswer().equals(answerInput) ? Quiz.Correct : Quiz.Wrong);

            // Feedback on answer
            if (randomQuestion.getAnswer().equals(answerInput)) {
                showTextFeedback("That is correct!", colorCorrect);
            } else {
                showTextFeedback("That is incorrect. The answer is " + randomQuestion.getAnswer(), colorIncorrect);
            }

            // Next Question
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    animationHide();
                }
            }, 3000);   //3 seconds
        }
    };

    // Update button Text
    private void updateButtonText() {
        // End if Math
        if (Quiz.getSubject().equals(Subjects.Math.toString())) {
            return;
        }

        // Add all options into ArrayList
        ArrayList<String> options = new ArrayList<String>();
        options.add(randomQuestion.getOption()[0]);
        options.add(randomQuestion.getOption()[1]);
        options.add(randomQuestion.getOption()[2]);
        options.add(randomQuestion.getOption()[3]);

        // Randomly populate answer
        Button[] buttons = {option1, option2, option3, option4};
        int i = 0;
        while (options.size() != 0) {
            int random = (int) (Math.random() * options.size());
            buttons[i].setText(options.get(random));
            options.remove(random);
            i++;
        }
    }

    @Override
    public void onBackPressed() {
        Quiz.quitQuiz(this, QuizQuestionActivity.this);
    }

    private void animationHide() {
        // Hide Question
        question.startAnimation(animationHide);
        inputBox.startAnimation(animationHide);
        message.startAnimation(animationHide);
        option1.startAnimation(animationHide);
        option2.startAnimation(animationHide);
        option3.startAnimation(animationHide);
        option4.startAnimation(animationHide);

        // Change Button background color to default, then load next Question
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (Quiz.getCurrentQuestionNumber() != 5) {
                    for (Button option : options) {
                        option.setBackgroundColor(colorOptionDefault);
                    }
                }
                loadNextQuestion();
            }
        }, 500);   //0.5 seconds
    }

    private void animationShow() {
        // Animation to show view
        question.startAnimation(animationShow);
        inputBox.startAnimation(animationShow);
        message.startAnimation(animationShow);
        option1.startAnimation(animationShow);
        option2.startAnimation(animationShow);
        option3.startAnimation(animationShow);
        option4.startAnimation(animationShow);

    }

    private void loadNextQuestion() {
        Quiz.nextQuestion();
        randomQuestion = Quiz.getCurrentQuestion();

        // Update View to show next Question
        setNextQuestion();
    }

    private void setNextQuestion() {
        if (randomQuestion != null) {
            // Set Text
            updateButtonText();
            question.setText(String.format("Question %d:\n\n%s", Quiz.getCurrentQuestionNumber(), randomQuestion));
            inputBox.setText("");
            message.setText("");


            // Show Animation
            animationShow();
            enableAllButtons();
        } else {
            finish();
            Intent intent = new Intent(getApplicationContext(), QuizSummaryActivity.class);
            startActivity(intent);
        }
    }

    private void showOptionAnswer(Button button, int answer) {
        // Prepare Animation for correct Answer
        ValueAnimator correctAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorOptionDefault, colorCorrect);
        correctAnimation.setDuration(1000); // milliseconds

        // Prepare Animation for incorrectAnswer
        ValueAnimator IncorrectAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorOptionDefault, colorIncorrect);
        IncorrectAnimation.setDuration(1000); // milliseconds

        if (answer == Quiz.Correct) {
            // Add Update Lister
            correctAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    button.setBackgroundColor((int) animator.getAnimatedValue());
                }
            });

            // Start Animation
            correctAnimation.start();
        } else {
            // Prepare animation for correct answer
            for (Button btn : options) {
                if (btn.getText().toString().equals(randomQuestion.getAnswer())) {
                    correctAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            btn.setBackgroundColor((int) animator.getAnimatedValue());
                        }
                    });
                    break;
                }
            }

            // Prepare animation for wrong answer
            IncorrectAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    button.setBackgroundColor((int) animator.getAnimatedValue());
                }
            });

            // Start Animation
            correctAnimation.start();
            if (answer == Quiz.Wrong) {
                IncorrectAnimation.start();
            }
        }
    }

    // Disable Buttons to prevent user from clicking multiple times
    public void disableAllButtons() {
        for (Button option : options) {
            option.setEnabled(false);
        }

        submitButton.setEnabled(false);
    }

    // Enable Buttons to allow user to submit an answer
    public void enableAllButtons() {
        for (Button option : options) {
            option.setEnabled(true);
        }

        submitButton.setEnabled(true);
    }

    public void showTextFeedback(String feedback, int color) {
        // Start animation
        message.startAnimation(animationShow);

        // Set messageField Details
        message.setText(feedback);
        message.setTextColor(color);
    }
}