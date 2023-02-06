import java.text.DecimalFormat;

public class Grade implements Comparable, Cloneable{

    private DecimalFormat df = new DecimalFormat("#.##");
    private Double partialScore, examScore;
    private Student student;
    private String course; // name of course

    public Grade(Double partialScore, Double examScore, Student student, String course) {
        this.student = student;
        this.course = course;
        this.partialScore = partialScore;
        this.examScore = examScore;
    }

    public Grade(Student student, String course) {
        this(0.0, 0.0, student, course);
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public Student getStudent() {
        return student;
    }

    public String getCourse() {
        return course;
    }
    public String toString() {
        return student.getFullName() + "'s grade: " + partialScore + " " + examScore + " = " + df.format(getTotal());
    }
    public Double getTotal() {
        return partialScore + examScore;
    }

    @Override
    public Grade clone() {
        try {
            return (Grade) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Object o) {
        Grade g = (Grade) o;
        return this.getTotal().compareTo(g.getTotal()); // compare by total score (using compareTo from double)
    }

    public Object Clone() {
        return new Grade(partialScore, examScore, student, course);
    }
}
