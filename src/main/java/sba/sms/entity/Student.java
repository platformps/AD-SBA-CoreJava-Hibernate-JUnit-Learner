package sba.sms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "student")
@Entity
public class Student {

    @NonNull
    @Id
    @Column(length = 50, name = "email")
    private String email;

    @NonNull
    @Column(length = 50, nullable = false, name = "name")
    private String name;

    @NonNull
    @Column(length = 50, name = "password")
    private String password;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))
    private Set<Course> courses = new LinkedHashSet<>();

    public void addCourse(Course c) {
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
