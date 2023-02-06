public class UserFactory {
    public User getUser(String userType, String firstName, String lastName) {
        if(userType.equalsIgnoreCase("STUDENT")) {
            return new Student(firstName, lastName);
        } else if(userType.equalsIgnoreCase("TEACHER")) {
            return new Teacher(firstName, lastName);
        } else if(userType.equalsIgnoreCase("ASSISTANT")) {
            return new Assistant(firstName, lastName);
        }
        return null;
    }
}
