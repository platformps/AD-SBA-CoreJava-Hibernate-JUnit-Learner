package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object
 * contains fields that represent student login credentials
 * and a join table containing a registered student's
 * email and course(s) data.
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter@Getter@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student")
@Entity
public class Student {
    @NonNull
    @Id @Column(length = 50,name = "email")
    String email;
    @NonNull
    @Column(length = 50, nullable = false, name = "name")
    String name;
    @NonNull
    @Column(length = 50,name = "password")
    String password;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))
     Set<Course> courses = new HashSet<>();

    /**
     * Method takes-in a course object as an argument,
     * adds it to course list, then adds
     * the current student(this) object
     * to the students list.
     */
    public void addCourse(Course c){
        courses.add(c);
        c.getStudents().add(this);


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email.equals(student.email) && name.equals(student.name) && password.equals(student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password);
    }
}
