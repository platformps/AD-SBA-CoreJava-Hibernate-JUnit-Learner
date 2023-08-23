package sba.sms;

import lombok.extern.java.Log;
import sba.sms.entity.Course;
import sba.sms.entity.Student;
import sba.sms.dao.CourseDAO;
import sba.sms.dao.StudentDAO;
import sba.sms.utils.CommandLine;

import java.util.List;
import java.util.Scanner;

/**
 * SBA Core Java Hibernate/Junit
 * Business Requirement:
 * task is to create a basic School Management System
 * where students can register for courses, and view the course assigned to them.
 *<br />
 * Main uses <br />
 * Initialize dummy data: {@link CommandLine#addData()} <br />
 * Two models: {@link Student} & {@link Course} <br />
 * Two services: {@link StudentDAO} & {@link CourseDAO}
 *
 * @author  Jafer Alhaboubi
 * @since sba-core-java-hibernate-junit 1.0
 */
@Log
public class Main {

    private StudentDAO studentDao = new StudentDAO();
    private CourseDAO courseDao = new CourseDAO();

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {

        CommandLine.addData();

        Scanner input = new Scanner(System.in);
        int userInput;
        do {
            System.out.printf("Select # from menu:%n1.Student%n2.Quit%n");
            userInput = input.nextInt();
            if (userInput == 1) {
                System.out.printf("Enter student email: ");
                String email = input.next();
                System.out.printf("Enter %s's password: ", email.substring(0, email.indexOf("@")));
                String password = input.next();
                if (studentDao.validateStudent(email, password)) {
                    printStudentCourses(email);
                    System.out.printf("select # from menu: %n1.Register %s to class: %n2.Logout%n", studentDao.getStudentByEmail(email).getName());
                    userInput = input.nextInt();
                    if (userInput == 2) {
                        System.exit(0);
                    } else {
                        List<Course> courseList = courseDao.getAllCourses();
                        System.out.printf("All courses:%n-----------------------------%n");
                        System.out.printf("%-2s | %-20s | %s%n", "ID", "Course", "Instructor");
                        if (courseList.isEmpty()) System.out.printf("No courses to view%n");
                        for (Course course : courseList) {
                            System.out.printf("%-2d | %-20s | %s%n", course.getId(), course.getName(), course.getInstructor());
                        }
                        System.out.printf("select course #: ");
                        int courseId = input.nextInt();
                        if (courseId > 0 && courseId <= courseList.size()) {
                            studentDao.registerStudentToCourse(email, (courseId));
                            System.out.printf("successfully register %s to %s%n", studentDao.getStudentByEmail(email).getName(), courseDao.getCourseById(courseId).getName());
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

    private void printStudentCourses(String email) {
        System.out.printf("%s courses:%n-----------------------------%n", email);
        System.out.printf("%-2s | %-20s | %s%n", "ID", "Course", "Instructor");
        List<Course> userCourses = studentDao.getStudentCourses(email);
        if (userCourses.isEmpty()) System.out.printf("No courses to view%n");
        for (Course course : userCourses) {
            System.out.printf("%-2d | %-20s | %s%n", course.getId(), course.getName(), course.getInstructor());
        }
    }
}
