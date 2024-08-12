package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI{

    @Override
    public void createCourse(Course course) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try{
            tx = s.beginTransaction();
            s.persist(course);
            tx.commit();
        } catch (HibernateException exception){
            if(tx != null) tx.rollback();
        } finally {
            s.close();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Course course = new Course();

        try{
            tx = s.beginTransaction();
            Query<Course> q = s.createQuery("from Course where id = :id", Course.class);
            q.setParameter("id", courseId);
            course = q.getSingleResult();
            tx.commit();
        } catch (HibernateException exception){
            if(tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List<Course> courseList = new ArrayList<>();

        try{
            tx = s.beginTransaction();
            Query<Course> q = s.createQuery("from Course", Course.class);
            courseList = q.getResultList();
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