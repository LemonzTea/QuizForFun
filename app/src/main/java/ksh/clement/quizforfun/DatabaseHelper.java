package ksh.clement.quizforfun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    public static final String DATABASE_NAME = "QuizForFun.db";

    // USERS Table
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_USERS_COL_1 = "user_id";
    public static final String TABLE_USERS_COL_2 = "username";
    public static final String TABLE_USERS_COL_3 = "email";
    public static final String TABLE_USERS_COL_4 = "password";


    // SCORES Table
    public static final String TABLE_SCORES = "SCORES";
    public static final String TABLE_SCORES_COL_1 = "attempt_id";
    public static final String TABLE_SCORES_COL_2 = "user_id";
    public static final String TABLE_SCORES_COL_3 = "subject";
    public static final String TABLE_SCORES_COL_4 = "timedate";
    public static final String TABLE_SCORES_COL_5 = "points";

    // QUESTIONS Table
    public static final String TABLE_QUESTIONS = "QUESTIONS";
    public static final String TABLE_QUESTIONS_COL_1 = "question_id";
    public static final String TABLE_QUESTIONS_COL_2 = "subject";
    public static final String TABLE_QUESTIONS_COL_3 = "question";
    public static final String TABLE_QUESTIONS_COL_4 = "answer";
    public static final String TABLE_QUESTIONS_COL_5 = "option1";
    public static final String TABLE_QUESTIONS_COL_6 = "option2";
    public static final String TABLE_QUESTIONS_COL_7 = "option3";





    // Constructor
    public DatabaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Commands to create user Table
        String usersCommand = String.format(
            "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s Text," +
                    "UNIQUE(%s)" +
                    ")",
            TABLE_USERS,
            TABLE_USERS_COL_1, TABLE_USERS_COL_2,
            TABLE_USERS_COL_3, TABLE_USERS_COL_4,
            TABLE_USERS_COL_2
        );

        // Command to create score Table
        String scoresCommand = String.format(
            "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s INTEGER," +
                    "FOREIGN KEY(%s) REFERENCES %s(%s)" +
                    ")",
            TABLE_SCORES,
            TABLE_SCORES_COL_1, TABLE_SCORES_COL_2, TABLE_SCORES_COL_3,
            TABLE_SCORES_COL_4, TABLE_SCORES_COL_5,
            TABLE_SCORES_COL_2, TABLE_USERS, TABLE_USERS_COL_1
        );

        // Command to create questionTable
        String questionsCommand = String.format(
                "CREATE TABLE %s(" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT" +
                        ")",
                TABLE_QUESTIONS,
                TABLE_QUESTIONS_COL_1, TABLE_QUESTIONS_COL_2, TABLE_QUESTIONS_COL_3,
                TABLE_QUESTIONS_COL_4, TABLE_QUESTIONS_COL_5, TABLE_QUESTIONS_COL_6,
                TABLE_QUESTIONS_COL_7
        );

        // Storage all commands in an array
        String[] commands = {usersCommand, scoresCommand, questionsCommand};

        // Execute all the commands
        for (String command: commands) {
            db.execSQL(command);
        }

        // Insert all Questions into Database
        insertQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    // Returns a user based on username and password
    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get Result
        Cursor result = db.rawQuery("SELECT * FROM "+ TABLE_USERS +
                        " WHERE "+ TABLE_USERS_COL_2 +" = ? " +
                        "and " + TABLE_USERS_COL_4 + " = ?",
                new String[] {username, password}
        );

        // Return null if no result is returned.
        if (result.getCount() != 1) {
            return null;
        }


        // Make result into User
        result.moveToNext();
        User userResult = new User(result.getInt(0),
                result.getString(1),
                result.getString(3),
                result.getString(2)
        );

        // Return the user
        return userResult;
    }

    // Creates a new user
    public User addNewUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Insert Values
        contentValues.put(TABLE_USERS_COL_2, username);
        contentValues.put(TABLE_USERS_COL_3, email);
        contentValues.put(TABLE_USERS_COL_4, password);

        // Attempt to add
        long result = db.insertOrThrow(TABLE_USERS, null, contentValues);

        // Check if user added successfully
        if (result == -1) {
            return null;
        } else {
            return getUser(username, password);
        }
    }

    public User updateExistingUser(User user, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Set Values to update
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_USERS_COL_1, user.getId());
        contentValues.put(TABLE_USERS_COL_2, user.getUsername());
        contentValues.put(TABLE_USERS_COL_3, user.getEmail());
        contentValues.put(TABLE_USERS_COL_4, newPassword);


        // Get Number of rows updated
        int numberOfRowUpdated = db.update(TABLE_USERS, contentValues, TABLE_USERS_COL_1 + " = ?",
                new String[] {String.valueOf(user.getId())});

        Log.d("Number of users updated", String.valueOf(numberOfRowUpdated));

        if (numberOfRowUpdated == 1) {
            return getUser(user.getUsername(), newPassword);
        } else {
            return null;
        }
    }

    public ArrayList<Question> getQuizQuestions(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get results from DB
        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_QUESTIONS, TABLE_QUESTIONS_COL_2);
        Cursor res = db.rawQuery(query , new String[]{subject});

        // Add all questions into Arraylist
        ArrayList<Question> questionList = new ArrayList<Question>();
        while(res.moveToNext()) {
            String question = res.getString(2);
            String answer = res.getString(3);
            String option1 = res.getString(4);
            String option2 = res.getString(5);
            String option3 = res.getString(6);

            if (option1 == null || option1 == null || option3 == null) {
                questionList.add(new Question(question, answer));
            } else {
                questionList.add(new Question(question, answer, option1, option2, option3));
            }
        }

        return questionList;
    }

    // Add new quiz result
    public boolean addNewQuizResult(User user, String subject, Date timestamp, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Insert Values
        contentValues.put(TABLE_SCORES_COL_2, user.getId());
        contentValues.put(TABLE_SCORES_COL_3, subject);
        contentValues.put(TABLE_SCORES_COL_4, timestamp.toString());
        contentValues.put(TABLE_SCORES_COL_5, score);

        // Attempt to add
        long result = db.insertOrThrow(TABLE_SCORES, null, contentValues);

        // Check if score record was added successfully
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Insert All Quiz Questions into Database
    private void insertQuestions(SQLiteDatabase db) {
        Question[][] questionList = {Question.DB_MATH_QUESTIONS, Question.DB_GEOGRAPHY_QUESTIONS, Question.DB_LITERATURE_QUESTIONS};

        for (int i = 0; i < questionList.length; i++) {

            String subject = "";
            switch (i) {
                case 0:
                    subject = Subjects.Math.toString();
                    break;
                case 1:
                    subject = Subjects.Geography.toString();
                    break;
                case 2:
                    subject = Subjects.Literature.toString();
                    break;
            }

            for (Question question: questionList[i]) {
                boolean added = insertQuestion(question, subject, db);
                if (!added) {
                    Log.e("Failed to add question", subject + " " + question);
                }
            }
        }
    }

    public boolean insertQuestion(Question question, String subject, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();

        // Insert Values
        contentValues.put(TABLE_QUESTIONS_COL_2, subject);
        contentValues.put(TABLE_QUESTIONS_COL_3, question.getQuestion());
        contentValues.put(TABLE_QUESTIONS_COL_4, question.getAnswer());
        contentValues.put(TABLE_QUESTIONS_COL_5, question.getOption()[1]);
        contentValues.put(TABLE_QUESTIONS_COL_6, question.getOption()[2]);
        contentValues.put(TABLE_QUESTIONS_COL_7, question.getOption()[3]);

        // Attempt to add
        long result = db.insertOrThrow(TABLE_QUESTIONS, null, contentValues);

        // Check if score record was added successfully
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // get totalScore of user
    public int getUserTotalScore(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        // Get results from DB
        String query = String.format("SELECT SUM(%s) FROM %s WHERE %s = ?", TABLE_SCORES_COL_5 ,TABLE_SCORES, TABLE_SCORES_COL_2);
        Cursor res = db.rawQuery(query , new String[]{String.valueOf(user.getId())});

        res.moveToNext();
        Log.d("dadsada", String.valueOf(res.getInt(0)));
        return res.getInt(0);
    }

    // get user scores
    public ArrayList<Score> getUserResults(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Score> list = new ArrayList<Score>();

        // Get results from DB
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_SCORES, TABLE_SCORES_COL_2);
        Cursor res = db.rawQuery(query , new String[]{String.valueOf(user.getId())});

        while (res.moveToNext()) {
            list.add(new Score(res.getString(2), res.getString(3), res.getInt(4)));
        }
        return list;
    }
}
