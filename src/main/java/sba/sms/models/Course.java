package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of  that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity

public class Course {

    // Getter Setter methods
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;


    @Column(name = "instructor", length = 50, nullable = false)
    private String instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Student> students = new HashSet<>();

    public Course(){

    }


    public Course(int id, String name, String instructor, Set<Student> students){
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.students = students;
    }

    // Required-args constructor
    public Course(String name, String instructor){
        this.name = name;
        this.instructor = instructor;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getInstructor(){
        return instructor;
    }

    public void setInstructor(String instructor){
        this.instructor = instructor;
    }

    public Set<Student> getStudents(){
        return students;
    }

    public void setStudents(Set<Student> students){
        this.students = students;
    }

    // toString method
    @Override
    public String toString(){
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor) && Objects.equals(students, course.students);
    }


    // hash code method
    @Override
    public int hashCode(){
        return Objects.hash(id, name ,instructor);
    }

    //Helper method
    public boolean hasStudent(Student student){
        return this.students.contains(student);
    }



}