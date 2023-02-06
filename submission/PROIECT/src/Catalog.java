import java.util.ArrayList;

public class Catalog implements Subject{

    private static Catalog catalog = new Catalog();

    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Observer> observers = new ArrayList<>();

    private Catalog() {
    }
    public static Catalog getCatalog() {
        return catalog;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
    public void addCourse(Course course) {
        courses.add(course);
    }

    public void print() {
        for (Course course : courses) {
            System.out.println(course.getCourseName());
            System.out.println("Teacher: " + course.getTeacher());

            for(Assistant assistant : course.getAssistants()) {
                System.out.println("Assistant: " + assistant);
            }

            System.out.println("------------------------");

            for(Grade grade : course.getGrades()) {
                System.out.println(grade);
            }

            //System.out.println(course.getGroups());
            System.out.println("Credits: " + course.getCredits() + "\n");
        }
    }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Grade grade) {
        for (Observer observer : observers) {
            if (observer instanceof Parent) {
                Parent parent = (Parent) observer;
                if (parent.getStudent().equals(grade.getStudent())) {
                    observer.update(new Notification("New grade for " + grade.getStudent().getFullName() + " in " + grade.getCourse() + ": " + grade.getTotal(), grade));
                }
            }
        }
    }
}