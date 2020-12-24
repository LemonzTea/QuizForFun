package ksh.clement.quizforfun;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {

    // UI Elements
    private EditText usernameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private Button continueButton;
    private Button cancelButton;

    // Database Helper
    private DatabaseHelper myDB;

    // Other Variable
    private User user = User.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        // Link UI Elements
        usernameField = (EditText) findViewById(R.id.UserDetails_EditText_Username);
        emailField = (EditText) findViewById(R.id.UserDetails_EditText_Email);
        passwordField = (EditText) findViewById(R.id.UserDetails_EditText_Password);
        confirmPasswordField = (EditText) findViewById(R.id.UserDetails_EditText_ConfirmPassword);
        continueButton = (Button) findViewById(R.id.UserDetails_Button_Continue);
        cancelButton = (Button) findViewById(R.id.UserDetails_Button_Cancel);

        // DatabaseHelper
        myDB = new DatabaseHelper(this, null, 1);

        // Check is user is logged in
        if (user != null) {
            // Set Text if user is logged in
            usernameField.setText(user.getUsername());
            emailField.setText(user.getEmail());

            // Disable the username and email field
            usernameField.setEnabled(false);
            emailField.setEnabled(false);

            // Change Button Text
            continueButton.setText("Confirm");

            // ActionBar Text
            getSupportActionBar().setTitle("Change Password");
        } else {
            // Change Button Text
            continueButton.setText("Register");
        }

        // Add Action Listeners
        continueButton.setOnClickListener(listener);
        cancelButton.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.UserDetails_Button_Continue:
                    setUserInfo();

                    break;
                case R.id.UserDetails_Button_Cancel:
                    // Return to previous page
                    finish();
                    break;
            }
        }
    };

    // This methods will try to update or create a new user.
    // Perform validation checks on input fields
    private void setUserInfo() {
        // Get Fields Values
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String email = emailField.getText().toString();

        // Validate User Input
        if (!validateInfo(username, email, password, confirmPassword)) {
            return;
        }

        // Attempt to Update / Create User
        User result = null;

        // Check if is new user
        if (user == null) {
            try {
                result = myDB.addNewUser(username, email, password);
            }

            // If username is taken
            catch (SQLiteConstraintException ex) {
                Toast toast = Toast.makeText(this, "Username Taken", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        // Else is existing user
        else {
            result = myDB.updateExistingUser(user, password);
        }

        // Check the result returned by DBHelper
        if (result == null) {
            Toast toast = Toast.makeText(this, "Error Saving", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            if (User.getCurrentUser() != null) {
                User.setCurrentUser(result);
            }
            Toast toast = Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    private boolean validateInfo(String username, String email, String password, String password2) {
        String message = "";
        Boolean status = true;

        // Check if fields are blank
        if (username.trim().isEmpty() || email.trim().isEmpty() ||
                password.trim().isEmpty() || password2.trim().isEmpty()) {
           message = "Fields cannot be empty";
           status = false;
        }

        // Check if fields contain spaces
        else if (username.contains(" ") || email.contains(" ") ||
                password.contains(" ") || password2.contains(" ")) {
            message = "Fields cannot contain spaces";
            status = false;
        }


        // Check if passwords entered are the same
        else if (!password.equals(password2)) {
            message = "Password Confirmation does not match Password";
            status = false;
        }


        // Display Toast if any error
        if (!status) {
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
        }

        return status;
    }
}