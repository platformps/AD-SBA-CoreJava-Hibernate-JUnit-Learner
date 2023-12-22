package sba.sms.utils;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.services.CourseService;
import sba.sms.services.StudentService;

/**
 * CommandLine is a Utility class that runs each time the application
 * executes. It performs a common routine by creating and persisting
 * student objects to the 'student' table and course objects to the
 * 'course' table.
 */
public class CommandLine {
    private CommandLine(){
        // Utility classes should not have public constructors
    }
    private static final String PASSWORD = "password";

    /**
     * Creates and persist student object to the 'student' table and
     * course objects to the 'course' table
     *
     * <b style="color:red">ATTENTION PLEASE READ</b>
     * Uncomment the following code after creating both models and entities for ease of adding and dropping dummy data into the database, remember that
     * in hibernate.cfg.xml <code>hibernate.hbm2ddl.auto = create-drop </code> will create and drop the tables every time the application
     * re-runs.
     *
     *
     */
    public static void addData(){
        /*

        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        studentService.createStudent(new Student("reema@gmail.com", "reema brown", PASSWORD));
        studentService.createStudent(new Student("annette@gmail.com", "annette allen", PASSWORD));
        studentService.createStudent(new Student("anthony@gmail.com", "anthony gallegos", PASSWORD));
        studentService.createStudent(new Student("ariadna@gmail.com", "ariadna ramirez", PASSWORD));
        studentService.createStudent(new Student("bolaji@gmail.com", "bolaji saibu", PASSWORD));
        studentService.createStudent(new Student("shirese@gmail.com", "shirese smith", PASSWORD));

        courseService.createCourse(new Course("Java", "Roger Boaitey"));
        courseService.createCourse(new Course("Frontend", "William Roales"));
        courseService.createCourse(new Course("JPA", "Jafer Alhaboubi"));
        courseService.createCourse(new Course("Spring Framework", "LaTonya Lewis"));
        courseService.createCourse(new Course("SQL", "Ezra Williams"));
        courseService.createCourse(new Course("GitHub", "Igor Adulyan"));
        courseService.createCourse(new Course("Web Services", "Raheem Abolfathzadeh"));
        courseService.createCourse(new Course("Microservices", "Eric Heilig"));

        */
    }
}
