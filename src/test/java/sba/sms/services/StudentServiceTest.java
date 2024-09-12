package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class StudentServiceTest {

    private static StudentService studentService;

    @BeforeAll
    static void setUp() {
        studentService = new StudentService();
    }

    @AfterAll
    static void tearDown() {
        studentService = null;
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setEmail("kayla@gmail.com");
        student.setName("Kayla");
        student.setPassword("password1");

        studentService.createStudent(student);

        assertNotNull(student);
    }
}