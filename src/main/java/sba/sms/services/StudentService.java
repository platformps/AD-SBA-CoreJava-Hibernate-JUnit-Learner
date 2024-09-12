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


    @Override
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Student";
            Query<Student> query = session.createQuery(hql, Student.class);
            students = query.list();
        }
        catch (HibernateException e) {
            log.severe("Error while retrieving students: " + e.getMessage());
        }
        return students;
    }

    @Override
    public void createStudent(Student student){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "from Student where email = :email";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("email", student.getEmail());
            Student currentStudent = query.uniqueResult();
            if(currentStudent != null){
                session.persist(currentStudent);
                log.info("Student with id:  " + student.getEmail() + " has been created");
            }
            else{
                session.merge(student);
                log.warning("Student with id: " + student.getEmail() + " already exists");
            }
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.severe("Error creating student" + e.getMessage());
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Student student = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession(); session) {
            String hql = "FROM Student where email = :email";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("email", email);
            student = query.uniqueResult();
        }
        catch (HibernateException e) {
            log.severe("Error: couldn't get student by email " + e.getMessage());
        }

        return student;
    }

    @Override
    public boolean validateStudent(String email, String password){

        try(Session session = HibernateUtil.getSessionFactory().openSession(); session) {
            String hql = "from Student where email = :email and password = :password";
            Query<Student> studentQuery = session.createQuery(hql, Student.class);
            studentQuery.setParameter("email", email);
            studentQuery.setParameter("password", password);
            Student studentToValidate = studentQuery.uniqueResult();

            if(studentToValidate != null){
                log.info("Student is valid ");
                return true;
            }
            else {
                log.warning("Student is not valid ");
                return false;
            }
        }
        catch (HibernateException e) {
            log.severe("Error in validate Student " + e.getMessage());
            return false;
        }
    }

    @Override
    public void registerStudentToCourse(String email, int courseId){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession(); session) {
            transaction = session.beginTransaction();

            String studentHql = "FROM Student WHERE email = :email";
            Query<Student> studentQuery = session.createQuery(studentHql, Student.class);
            studentQuery.setParameter("email", email);
            Student student = studentQuery.uniqueResult();

            String courseHql = "FROM Course WHERE id = :courseId";
            Query<Course> courseQuery = session.createQuery(courseHql, Course.class);
            courseQuery.setParameter("courseId", courseId);
            Course courseToRegister = courseQuery.uniqueResult();

            if(student != null && courseToRegister != null){
                student.getCourses().add(courseToRegister);
                courseToRegister.getStudents().add(student);
                session.merge(student);
                session.merge(courseToRegister);
                log.info("Student with id: " + student.getEmail() + " has been registered");
            }
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.severe("Error in registering student " + e.getMessage());
            }

        }
    }

    @Override
    public List<Course> getStudentCourses(String email){
        List<Course> courses = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession(); session) {
            String hql = "Select c from Course c inner join c.students s where s.email = :email";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("email", email);
            courses = query.list();
        }
        catch (HibernateException e) {
            log.throwing("StudentService", "getStudentCourses", e);
            throw e;
        }
        return courses;
    }
}
