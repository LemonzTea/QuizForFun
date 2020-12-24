package ksh.clement.quizforfun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // UI Elements
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private Button registerButton;

    // Database Helper
    private DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Link UI Elements
        usernameField = (EditText) findViewById(R.id.LoginActivity_EditText_Username);
        passwordField = (EditText) findViewById(R.id.LoginActivity_EditText_Password);
        loginButton = (Button) findViewById(R.id.LoginActivity_Button_Login);
        registerButton = (Button) findViewById(R.id.LoginActivity_Button_Register);

        // DatabaseHelper
        myDB = new DatabaseHelper(this, null, 1);

        // Add ActionListener to Buttons
        loginButton.setOnClickListener(listener);
        registerButton.setOnClickListener(listener);
    }

    // Action Listener for Buttons
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.LoginActivity_Button_Login:
                    // Log user in
                    attemptLogin(usernameField.getText().toString(),
                            passwordField.getText().toString());
                    break;

                case R.id.LoginActivity_Button_Register:
                    // Go to registration Page
                    Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void attemptLogin(String username, String password) {
        User user = myDB.getUser(username, password);
        User.setCurrentUser(user);

        if (user != null) {
            // Clear Fields
            usernameField.setText("");
            passwordField.setText("");

            // Intent into main page
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else {
            // Show Login Failed
            Toast toast = Toast.makeText(this, "Username or Password Invalid", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}