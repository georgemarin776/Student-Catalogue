import java.util.ArrayList;

public class FullCourse extends Course {
    private FullCourse(Course.CourseBuilder builder) {
        super(builder);
    }

    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String courseName) {
            super(courseName);
        }

        public FullCourse build() {
            return new FullCourse(this);
        }
    }

    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();

        for (Grade grade : getGrades()) {
            if (grade.getPartialScore() >= 3 && grade.getExamScore() >= 2) {
                graduatedStudents.add(grade.getStudent());
            }
        }

        return graduatedStudents;
    }
}
