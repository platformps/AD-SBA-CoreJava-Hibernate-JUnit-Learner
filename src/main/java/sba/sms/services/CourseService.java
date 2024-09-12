package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
@Log
public class CourseService implements CourseI{

    @Override
    public void createCourse(Course course) {

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            String hql = "from Course where name = :name";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("name", course.getName());
            Course newCourse = query.uniqueResult();
            if(newCourse != null){
                session.persist(newCourse);
                log.info("New Course created " + newCourse.getName());
            }
            else {
                session.merge(course);
                log.warning("Course updated " + course.getName());
            }
            transaction.commit();
        }
        catch(HibernateException e){
            if(transaction != null){
                transaction.rollback();
            }
            log.severe("Error creating or updating course: " + course.getName());
        }
    }

    @Override
    public Course getCourseById(int id) {
        Course course = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Course where id = :id";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("id", id);
            course = query.uniqueResult();

            log.info("Course retrieved: " + course.getName());
        }
        catch(HibernateException e){
            assert course != null;
            log.severe("Could not retrieve course with name: " + course.getName());
        }
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Course";
            Query<Course> query = session.createQuery(hql, Course.class);
            courses = query.getResultList();
        }
        catch(HibernateException e) {
            log.severe("Course retrieval failed");
        }
        return courses;
    }
}
