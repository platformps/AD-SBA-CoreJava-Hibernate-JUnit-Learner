package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

@Log
public class StudentService implements StudentI {
    private  static final CourseService courseService = new CourseService();

    @Override
    public List<Student> getAllStudents() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Student> studentList = new ArrayList<>();

        try{
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student", Student.class);
            studentList = q.getResultList();
            tx.commit();
        } catch (HibernateException exception){
            if(tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return studentList;
    }

    @Override
    public void createStudent(Student student) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch (HibernateException exception){
            if(tx != null) tx.rollback();
            exception.
                    printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Student student = null;
        try{
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student where email = :email", Student.class);
            q.setParameter("email", email);
            student = q.getSingleResult();
            tx.commit();
        } catch (Exception exception){
            if(tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        Student s = getStudentByEmail(email);
        if (s != null && s.getPassword() != null) {
            return s.getPassword().equals(password);
        }
        return false;
    }




    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = s.beginTransaction();
            Student student = getStudentByEmail(email);
            Course course = courseService.getCourseById(courseId);
            if (student != null && course != null) {
                student.addCourse(course);  // Ensure addCourse is an instance method
                s.merge(student);
                tx.commit();
            } else {
                System.out.println("Student or Course not found.");
            }
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Course> courseList = new ArrayList<>();

        try{
            tx = s.beginTransaction();
            String nativeGetStudentCourses = "SELECT c.id, c.name, c.instructor " +
                    "FROM course AS c " +
                    "JOIN student_courses AS sc " +
                    "ON c.id = sc.course_id " +
                    "JOIN student AS s " +
                    "ON s.email = sc.student_email " +
                    "WHERE s.email = ?";
            NativeQuery<Course> studentCourses = s.createNativeQuery(nativeGetStudentCourses, Course.class);
            studentCourses.setParameter(1, email);
            courseList = studentCourses.getResultList();
            tx.commit();
        } catch (HibernateException exception){
            if(tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return courseList;
    }
}