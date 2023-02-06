public class Student extends User{

    private Parent mother = null, father = null;

    public Student(String firstName, String lastName, Parent mother, Parent father) {
        super(firstName, lastName);
        this.mother = mother;
        this.father = father;
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother() {
        return mother;
    }

    public Parent getFather() {
        return father;
    }
}
