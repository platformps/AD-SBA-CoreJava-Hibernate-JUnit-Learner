package sba.sms.utils;

import sba.sms.entity.Course;
import sba.sms.entity.Student;
import sba.sms.dao.CourseDAO;
import sba.sms.dao.StudentDAO;

public class CommandLine {
    private CommandLine(){
        // Utility classes should not have public constructors
    }
    private static final String PASSWORD = "password";


    public static void addData(){
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        String instructorPhillip = "Phillip Witkin";
        studentDAO.createStudent(new Student("reema@gmail.com", "reema brown", PASSWORD));
        studentDAO.createStudent(new Student("annette@gmail.com", "annette allen", PASSWORD));
        studentDAO.createStudent(new Student("anthony@gmail.com", "anthony gallegos", PASSWORD));
        studentDAO.createStudent(new Student("ariadna@gmail.com", "ariadna ramirez", PASSWORD));
        studentDAO.createStudent(new Student("bolaji@gmail.com", "bolaji saibu", PASSWORD));

        courseDAO.createCourse(new Course("Java", instructorPhillip));
        courseDAO.createCourse(new Course("Frontend", "Kasper Kain"));
        courseDAO.createCourse(new Course("JPA", "Jafer Alhaboubi"));
        courseDAO.createCourse(new Course("Spring Framework", instructorPhillip));
        courseDAO.createCourse(new Course("SQL", instructorPhillip));


    }
}
