import java.util.ArrayList;

public class PartialCourse extends Course {
    private PartialCourse(Course.CourseBuilder builder) {
        super(builder);
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        public PartialCourseBuilder(String courseName) {
            super(courseName);
        }

        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }

    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();

        for (Grade grade : getGrades()) {
            if (grade.getTotal() >= 5) {
                graduatedStudents.add(grade.getStudent());
            }
        }

        return graduatedStudents;
    }
}
