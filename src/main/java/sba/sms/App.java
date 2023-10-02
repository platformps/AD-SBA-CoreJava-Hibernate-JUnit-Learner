package sba.sms;

import lombok.extern.java.Log;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.services.CourseService;
import sba.sms.services.StudentService;
import sba.sms.utils.CommandLine;

import java.util.List;
import java.util.Scanner;

/**
 * SBA Core Java Hibernate/Junit
 * Business Requirement:
 * The task is to create a basic School Management System
 * where students can register for courses, and view the course assigned to them.
 *<br />
 * App uses <br />
 * Initialize dummy data: {@link CommandLine#addData()} <br />
 * Two models: {@link Student} & {@link Course} <br />
 * Two services: {@link StudentService} & {@link CourseService}
 *
 *
 * <b style="color:red">WARNING! </b>
 * <b>DO NOT MODIFY THIS CODE</b>
 *
 * @author  Jafer Alhaboubi & LaTonya Lewis
 * @since sba-core-java-hibernate-junit 1.0
 */

@Log
public class App {
    static final  StudentService studentService = new StudentService();
    static final  CourseService courseService = new CourseService();

    public static void main(String[] args) {

       CommandLine.addData();

        Scanner input = new Scanner(System.in);
        int userInput;
        do {
            System.out.printf("Select # from menu:%n1.Student%n2.Quit%n");
            userInput = input.nextInt();
            if (userInput == 1) {
                System.out.print("Enter student email: ");
                String email = input.next();
                System.out.printf("Enter %s's password: ", email.substring(0, email.indexOf("@")));
                String password = input.next();
                if (studentService.validateStudent(email, password)) {
                    printStudentCourses(email);
                    System.out.printf("select # from menu: %n1.Register %s to class: %n2.Logout%n", studentService.getStudentByEmail(email).getName());
                    userInput = input.nextInt();
                    if (userInput == 2) {
                        System.exit(0);
                    } else {
                        List<Course> courseList = courseService.getAllCourses();
                        System.out.printf("All courses:%n-----------------------------%n");
                        System.out.printf("%-2s | %-20s | %s%n", "ID", "Course", "Instructor");
                        if (courseList.isEmpty()) System.out.printf("No courses to view%n");
                        for (Course course : courseList) {
                            System.out.printf("%-2d | %-20s | %s%n", course.getId(), course.getName(), course.getInstructor());
                        }
                        System.out.print("select course #: ");
                        int courseId = input.nextInt();
                        if (courseId > 0 && courseId <= courseList.size()) {
                            studentService.registerStudentToCourse(email, (courseId));
                            System.out.printf("successfully register %s to %s%n", studentService.getStudentByEmail(email).getName(), courseService.getCourseById(courseId).getName());
                            printStudentCourses(email);
                        } else {
                            System.out.printf("course id not found!%n");
                        }
                        System.out.printf("session ended!%n");
                    }
                } else {
                    System.out.printf("Incorrect username or password%n");
                }
            }
        } while (userInput != 2);
        input.close();
    }

    private static void printStudentCourses(String email) {
        System.out.printf("%s courses:%n-----------------------------%n", email);
        System.out.printf("%-2s | %-20s | %s%n", "ID", "Course", "Instructor");
        List<Course> userCourses = studentService.getStudentCourses(email);
        if (userCourses.isEmpty()) System.out.printf("No courses to view%n");
        for (Course course : userCourses) {
            System.out.printf("%-2d | %-20s | %s%n", course.getId(), course.getName(), course.getInstructor());
        }
    }
}
