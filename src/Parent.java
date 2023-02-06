import java.util.ArrayList;

public class Parent extends User implements Observer{

    private Student student;
    private ArrayList<Notification> notifications = new ArrayList<>();
    public Parent(String firstName, String lastName, Student student) {
        super(firstName, lastName);
        this.student = student;
    }

    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void update(Notification notification) {
        notifications.add(notification);
        System.out.println("New notification for " + getStudent().getFullName() + " 's parent: " + notification.getMessage());
    }
}
