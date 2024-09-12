package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@Table(name="students")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = "courses")


@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    @Id
    @NonNull
    @Column(name = "email", length = 50, unique = true)
    private String email;

    @NonNull
    @Column(name = "student_name", length = 50)
    String name;

    @NonNull
    @Column(name = "password")
    String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {DETACH, REMOVE, MERGE, PERSIST})
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    Set<Course> courses = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

}