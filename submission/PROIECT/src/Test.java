import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Test {
    static Teacher findTeacher(String firstName, String lastName, HashSet<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            if (teacher.getFirstName().equals(firstName) && teacher.getLastName().equals(lastName)) {
                return teacher;
            }
        }
        return null;
    }

    static Assistant findAssistant(String firstName, String lastName, HashSet<Assistant> assistants) {
        for (Assistant assistant : assistants) {
            if (assistant.getFirstName().equals(firstName) && assistant.getLastName().equals(lastName)) {
                return assistant;
            }
        }
        return null;
    }

    static Student findStudent(String firstName, String lastName, HashSet<Student> students) {
        for (Student student : students) {
            if (student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)) {
                return student;
            }
        }
        return null;
    }

    static Parent findParent(String firstName, String lastName, HashSet<Parent> parents) {
        for (Parent parent : parents) {
            if (parent.getFirstName().equals(firstName) && parent.getLastName().equals(lastName)) {
                return parent;
            }
        }
        return null;
    }

    static Grade findGrade(Student student, ArrayList<Grade> grades) {
        for (Grade grade : grades) {
            if (grade.getStudent().equals(student)) {
                return grade;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Catalog catalog = Catalog.getCatalog();

        HashSet<Teacher> allTeachers = new HashSet<>();
        HashSet<Assistant> allAssistants = new HashSet<>();
        HashSet<Student> allStudents = new HashSet<>();
        HashSet<Parent> allParents = new HashSet<>();

        Object obj = new JSONParser().parse(new FileReader("catalog.json"));
        JSONObject jo = (JSONObject) obj;
        JSONArray courses = (JSONArray) jo.get("courses");

        // System.out.println(courses.get(0));

        for (Object course : courses) {
            JSONObject courseObj = (JSONObject) course;
            String type = (String) courseObj.get("type");
            String strategy = (String) courseObj.get("strategy");
            String name = (String) courseObj.get("name");

            JSONObject teacher = (JSONObject) courseObj.get("teacher");
            String teacherFirstName = (String) teacher.get("firstName");
            String teacherLastName = (String) teacher.get("lastName");

            Teacher teacherObj;

            // Daca profesorul nu exista in catalog, il adaugam in lista si cream in obiect de tip Teacher

            if(findTeacher(teacherFirstName, teacherLastName, allTeachers) == null) {
                teacherObj = new Teacher(teacherFirstName, teacherLastName);
                allTeachers.add(teacherObj);
            }

            // Daca exista, ii luam 'instanta' din lista

            else {
                teacherObj = findTeacher(teacherFirstName, teacherLastName, allTeachers);
            }

            JSONArray assistants = (JSONArray) courseObj.get("assistants");

            // Lista de asisteni pentru cursul curent
            HashSet<Assistant> assistantList = new HashSet<>();

            for(Object assistant : assistants) {
                JSONObject assistantObj = (JSONObject) assistant;
                String assistantFirstName = (String) assistantObj.get("firstName");
                String assistantLastName = (String) assistantObj.get("lastName");

                if(findAssistant(assistantFirstName, assistantLastName, allAssistants) == null) {
                    Assistant assistantItem = new Assistant(assistantFirstName, assistantLastName);
                    allAssistants.add(assistantItem);
                    assistantList.add(assistantItem);
                }
                else {
                    if (findAssistant(assistantFirstName, assistantLastName, assistantList) == null) {
                        assistantList.add(findAssistant(assistantFirstName, assistantLastName, allAssistants));
                    }
                }
            }
            // System.out.println(assistantList);

            JSONArray groups = (JSONArray) courseObj.get("groups");

            HashMap<String, Group> groupList = new HashMap<>();

            for (Object group : groups) {
                JSONObject groupObj = (JSONObject) group;
                JSONArray groupStudents = (JSONArray) groupObj.get("students");
                String groupID = (String) groupObj.get("ID");
                //System.out.println(groupID);

                JSONObject assistantObj = (JSONObject) groupObj.get("assistant");
                String assistantFirstName = (String) assistantObj.get("firstName");
                String assistantLastName = (String) assistantObj.get("lastName");


                Group tempGroup = new Group(groupID,
                        findAssistant(assistantFirstName, assistantLastName, allAssistants));

                for (Object groupStudent : groupStudents) {
                    JSONObject groupStudentObj = (JSONObject) groupStudent;
                    String studentFirstName = (String) groupStudentObj.get("firstName");
                    String studentLastName = (String) groupStudentObj.get("lastName");

                    Student studentItem;

                    if(findStudent(studentFirstName, studentLastName, allStudents) == null) {
                        studentItem = new Student(studentFirstName, studentLastName);

                        try {
                            JSONObject mother = (JSONObject) groupStudentObj.get("mother");
                            String motherFirstName = (String) mother.get("firstName");
                            String motherLastName = (String) mother.get("lastName");

                            Parent motherItem = new Parent(motherFirstName, motherLastName);
                            motherItem.setStudent(studentItem);
                            allParents.add(motherItem);
                            studentItem.setMother(motherItem);
                        }
                        catch (Exception e) {
                            //System.out.println("No mother found for student " + studentItem.getFullName());
                        }

                        try {
                            JSONObject father = (JSONObject) groupStudentObj.get("father");
                            String fatherFirstName = (String) father.get("firstName");
                            String fatherLastName = (String) father.get("lastName");

                            Parent fatherItem = new Parent(fatherFirstName, fatherLastName);
                            fatherItem.setStudent(studentItem);
                            allParents.add(fatherItem);
                            studentItem.setFather(fatherItem);
                        }
                        catch (Exception e) {
                            //System.out.println("No father found for student " + studentItem.getFullName());
                        }
                        allStudents.add(studentItem);
                    }
                    else {
                        studentItem = findStudent(studentFirstName, studentLastName, allStudents);
                    }

                    // Studentul nu se va repeta in grupa, decizia de mai sus este suficienta

                    tempGroup.add(studentItem);

                    //System.out.print(studentFirstName + " " + studentLastName + ", ");
                }
                groupList.put(groupID, tempGroup);
                //System.out.println();
            }

            if (type.equals("FullCourse")) {
                FullCourse.FullCourseBuilder builder = new FullCourse.FullCourseBuilder(name);

                builder.setTeacher(teacherObj);
                builder.setAssistants(assistantList);
                builder.setGrades(new ArrayList<Grade>());
                builder.setGroups(groupList);
                builder.setCredits(0);
                if (strategy.compareTo("BestPartialScore") == 0) {
                    builder.setStrategy(new BestPartialScore());
                }
                else {
                    if (strategy.compareTo("BestTotalScore") == 0) {
                        builder.setStrategy(new BestTotalScore());
                    }
                    else {
                        builder.setStrategy(new BestExamScore());
                    }
                }

                FullCourse fullCourse = builder.build();
                catalog.addCourse(fullCourse);
            }
            else {
                PartialCourse.PartialCourseBuilder builder = new PartialCourse.PartialCourseBuilder(name);

                builder.setTeacher(teacherObj);
                builder.setAssistants(assistantList);
                builder.setGrades(new ArrayList<Grade>());
                builder.setGroups(groupList);
                builder.setCredits(0);
                if (strategy.compareTo("BestPartialScore") == 0) {
                    builder.setStrategy(new BestPartialScore());
                }
                else {
                    if (strategy.compareTo("BestTotalScore") == 0) {
                        builder.setStrategy(new BestTotalScore());
                    }
                    else {
                        builder.setStrategy(new BestExamScore());
                    }
                }

                PartialCourse partialCourseCourse = builder.build();
                catalog.addCourse(partialCourseCourse);
            }
        }

        JSONArray examGrades = (JSONArray) jo.get("examScores");

        for (Object examGrade : examGrades) {
            JSONObject examGradeObj = (JSONObject) examGrade;
            String courseName = (String) examGradeObj.get("course");
            Double gradeValue = Double.parseDouble(examGradeObj.get("grade").toString());

            JSONObject studentObj = (JSONObject) examGradeObj.get("student");
            String studentFirstName = (String) studentObj.get("firstName");
            String studentLastName = (String) studentObj.get("lastName");

            Student studentItem = findStudent(studentFirstName, studentLastName, allStudents);

            JSONObject teacherObj = (JSONObject) examGradeObj.get("teacher");
            String teacherFirstName = (String) teacherObj.get("firstName");
            String teacherLastName = (String) teacherObj.get("lastName");

            Teacher teacherItem = findTeacher(teacherFirstName, teacherLastName, allTeachers);

            Grade gradeItem = new Grade(studentItem, courseName);
            gradeItem.setExamScore(gradeValue);

            // Am creat nota, ramane sa fie adaugata in cursul corespunzator

            for (Course course : catalog.getCourses()) {
                if (course.getCourseName().compareTo(courseName) == 0) {
                    course.addGrade(gradeItem);
                    break;
                }
            }
        }

        JSONArray partialGrades = (JSONArray) jo.get("partialScores");

        for (Object partialGrade : partialGrades) {
            JSONObject partialGradeObj = (JSONObject) partialGrade;
            String courseName = (String) partialGradeObj.get("course");
            Double gradeValue = Double.parseDouble(partialGradeObj.get("grade").toString());

            JSONObject studentObj = (JSONObject) partialGradeObj.get("student");
            String studentFirstName = (String) studentObj.get("firstName");
            String studentLastName = (String) studentObj.get("lastName");

            Student studentItem = findStudent(studentFirstName, studentLastName, allStudents);

            JSONObject assistantObj = (JSONObject) partialGradeObj.get("assistant");
            String assistantFirstName = (String) assistantObj.get("firstName");
            String assistantLastName = (String) assistantObj.get("lastName");

            Assistant assistantItem = findAssistant(assistantFirstName, assistantLastName, allAssistants);

            for (Course course : catalog.getCourses()) {
                if (course.getCourseName().compareTo(courseName) == 0) {
                    if(findGrade(studentItem, course.getGrades()) == null) {
                        Grade gradeItem = new Grade(studentItem, courseName);
                        gradeItem.setExamScore(gradeValue);
                    }
                    else{
                        findGrade(studentItem, course.getGrades()).setPartialScore(gradeValue);
                    }
                    break;
                }
            }

            // Daca nu s-a gasit nota (nu avea deja examscore), atunci se va crea o noua nota
        }
        System.out.println("Teachers: " + allTeachers);
        System.out.println("Assistants: " + allAssistants);
        System.out.println("Students: " + allStudents);
        System.out.println("Parents: " + allParents);

        System.out.println();

        catalog.print();
    }
}
