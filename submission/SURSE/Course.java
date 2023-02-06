import java.util.*;

abstract class Course {
    public String courseName;

    private Teacher teacher;
    private HashSet<Assistant> assistants;
    private ArrayList<Grade> grades;
    private HashMap<String, Group> groups;
    private int credits;
    private Strategy strategy;
    private Snapshot snapshot;

    public Course(CourseBuilder builder) {
        this.courseName = builder.courseName;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.credits = builder.credits;
        this.strategy = builder.strategy;
        this.snapshot = new Snapshot(grades);
    }

    private class Snapshot {
        private ArrayList<Grade> gradesBackup;

        public Snapshot(ArrayList<Grade> grades) {
            this.gradesBackup = grades;
        }

        public ArrayList<Grade> getGrades() {
            return gradesBackup;
        }
    }

    public void makeBackup() {
        this.snapshot = new Snapshot(new ArrayList<Grade>());
        for (Grade grade : grades) {
            this.snapshot.getGrades().add(grade.clone());
        }
    }

    public void undo() {
        this.grades = this.snapshot.getGrades();
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setAssistants(HashSet<Assistant> assistants) {
        this.assistants = assistants;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }


    public String getCourseName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public int getCredits() {
        return credits;
    }

    public void addAssistant(String ID, Assistant assistant) {

        // find group with given id in dictionary
        if (groups.containsKey(ID)) {
            // add assistant to group
            groups.get(ID).setAssistant(assistant);
        } else {
            System.out.println("Group with ID " + ID + " does not exist.");
        }

        if (assistants.contains(assistant)) {
            System.out.println("Assistant already exists in this course's assistants list.");
        } else {
            assistants.add(assistant);
        }
    }

    public void addStudent(String ID, Student student) {
        if (groups.containsKey(ID)) {
            // add student to group
            groups.get(ID).add(student);
        } else {
            System.out.println("Group with ID " + ID + " does not exist.");
        }
    }

    public void addGroup(Group group) {
        if (groups.containsKey(group.getID())) {
            System.out.println("Group with ID " + group.getID() + " already exists.");
        } else {
            groups.put(group.getID(), group);
        }
    }

    public void addGroup(String ID, Assistant assistant) {
        if (groups.containsKey(ID)) {
            System.out.println("Group with ID " + ID + " already exists.");
        } else {
            groups.put(ID, new Group(ID, assistant));
        }
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        if (groups.containsKey(ID)) {
            System.out.println("Group with ID " + ID + " already exists.");
        } else {
            groups.put(ID, new Group(ID, assistant, comp));
        }
    }

    public Grade getGrade(Student student) {
        for (Grade grade : grades) {
            if (grade.getStudent().equals(student)) {
                return grade;
            }
        }
        System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " does not have a grade in this course.");
        return null;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> allStudents = new ArrayList<Student>();
        for (Grade grade : grades) {
            allStudents.add(grade.getStudent());
        }
        return allStudents;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> allStudentGrades = new HashMap<Student, Grade>();
        for (Grade grade : grades) {
            allStudentGrades.put(grade.getStudent(), grade);
        }
        return allStudentGrades;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    static abstract class CourseBuilder {
        private String courseName;
        private Teacher teacher;
        private HashSet<Assistant> assistants;
        private ArrayList<Grade> grades;
        private HashMap<String, Group> groups;
        private int credits;
        private Strategy strategy;
        private Snapshot snapshot;

        public CourseBuilder(String courseName) {
            this.courseName = courseName;
        }

        public CourseBuilder setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder setAssistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder setGrades(ArrayList<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder setGroups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        public CourseBuilder setCredits(int credits) {
            this.credits = credits;
            return this;
        }

        public CourseBuilder setStrategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        abstract Course build();
    }

    public Student getBestStudent() {
        Double bestScore = strategy.getBestScore(getGrades());

        for (Grade grade : getGrades()) {
            if (strategy.getScore(grade).equals(bestScore)) {
                return grade.getStudent();
            }
        }
        return null;
    }
}
