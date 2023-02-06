public class Notification {
    private String message;
    private Grade grade;

    public Notification(String message, Grade grade) {
        this.message = message;
        this.grade = grade;
    }

    public String getMessage() {
        return message;
    }

    public Grade getGrade() {
        return grade;
    }
}