package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@Table(name="courses")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(exclude = "students")


@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", unique = true)
    int id;

    @NonNull
    @Column(name = "course_name", length = 50)
    String name;

    @NonNull
    @Column(name = "instructor_name", length = 50)
    String instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = {DETACH, REMOVE, MERGE, REFRESH})
    Set<Student> students = new LinkedHashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
