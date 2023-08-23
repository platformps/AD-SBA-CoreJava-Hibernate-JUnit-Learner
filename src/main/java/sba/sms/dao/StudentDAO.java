package sba.sms.dao;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.entity.Course;
import sba.sms.entity.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

@Log
public class StudentDAO implements StudentInterface {
    private static final CourseDAO COURSE_DAO = new CourseDAO();

    @Override
    public List<Student> getAllStudents() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Student> studentList = new ArrayList<>();
        try {
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student", Student.class);
            studentList = q.getResultList();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
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
        try {
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Student student = null;
        try {
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student where email = :email", Student.class);
            q.setParameter("email", email);
            student = q.getSingleResult();
            tx.commit();

        } catch (Exception exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student s = getStudentByEmail(email);
        return s != null && s.getPassword().equals(password);
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {


        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            Student student = getStudentByEmail(email);
            student.addCourse(COURSE_DAO.getCourseById(courseId));
            s.merge(student);
            tx.commit();
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
        try {
            tx = s.beginTransaction();
            String nativeGetStudentCourses = "select c.id, c.name, c.instructor from course as c join student_courses as sc on c.id = sc.courses_id join student as s on s.email = sc.student_email where s.email = :email";
            NativeQuery<Course> studentCourses = s.createNativeQuery(nativeGetStudentCourses, Course.class);
            studentCourses.setParameter("email", email);
            courseList = studentCourses.getResultList();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return courseList;
    }
}
