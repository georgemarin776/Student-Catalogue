import java.util.HashMap;

public class ScoreVisitor {
    private HashMap<Teacher, Tuple<Student, String, Double>> examScores = new HashMap<Teacher, Tuple<Student, String, Double>>();
    private HashMap<Assistant, Tuple<Student, String, Double>> partialScores = new HashMap<Assistant, Tuple<Student, String, Double>>();

    public ScoreVisitor() {
    }

    public void visit(Teacher teacher) {
        Catalog catalog = Catalog.getCatalog();

        for (Course course : catalog.getCourses()) {
            if (course.getTeacher() == teacher) {
            }
        }
    }

    public void visit(Assistant assistant) {
        Catalog catalog = Catalog.getCatalog();

        for (Course course : catalog.getCourses()) {
            if (course.getAssistants().contains(assistant)) {
            }
        }
    }

    private class Tuple<T, U, V> {
        private T first;
        private U second;
        private V third;

        public Tuple(T first, U second, V third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }

        public V getThird() {
            return third;
        }
    }
}
