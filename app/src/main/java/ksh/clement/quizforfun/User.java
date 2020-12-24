package ksh.clement.quizforfun;

public class User {
    private static User currentUser;

    // Instance Variable
    private int id;
    private String username;
    private String password;
    private String email;

    // Constructor
    public User(int id, String username, String password, String email) {
        setInfo(username, password, email);
        this.id = id;
    }

    // Static Methods
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    // Setter
    public void setInfo(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username;
    }
}
