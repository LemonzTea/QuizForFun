package ksh.clement.quizforfun;

public class Score {
    private String subject;
    private int date;
    private String month;
    private int year;
    private String time;
    private int score;

    public Score(String subject, String timedate, int score) {
        this.subject = subject;
        date = Integer.parseInt(timedate.substring(8, 10));
        month = timedate.substring(4,7);
        year = Integer.parseInt(timedate.substring(timedate.length() - 4));
        time = timedate.substring(11,19);
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public int getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public int getMonthNumber(){
        switch (month) {
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            default: return 12;
        }
    }

    public int getHour() {
        return Integer.parseInt(time.substring(0,2));
    }

    public int getMin() {
        return Integer.parseInt(time.substring(3,5));
    }

    public int getSec() {
        return Integer.parseInt(time.substring(6));
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format(
            "\n%-10s%s\n" +
            "%-10s%s\n" +
            "%-10s%s\n" +
            "%-10s%d\n",
            "Date:", date + " " + month + " " + year,
            "Time:", time,
            "Subject:", subject,
            "Score:", score
        );
    }
}
