package sba.sms.dao;

import sba.sms.entity.Course;

import java.util.List;

public interface CourseInterface {
    void createCourse(Course course);
    Course getCourseById(int courseId);
    List<Course> getAllCourses();

}
