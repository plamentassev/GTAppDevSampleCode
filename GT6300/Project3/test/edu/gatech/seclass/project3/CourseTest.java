package edu.gatech.seclass.project3;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourseTest {

    Students students = null;
    Course course = null;
    static final String DB = "DB" + File.separator + "CourseDatabase6300.xlsx";
    static final String DB_GOLDEN = "DB" + File.separator + "CourseDatabase6300-golden.xlsx";

    @Before
    public void setUp() throws Exception {
        FileSystem fs = FileSystems.getDefault();
        Path dbfilegolden = fs.getPath(DB_GOLDEN);
        Path dbfile = fs.getPath(DB);
        Files.copy(dbfilegolden, dbfile, REPLACE_EXISTING);
        course = new Course(DB);
    }

    @After
    public void tearDown() throws Exception {
        students = null;
        course = null;
    }

    @Test
    public void testGetNumStudents1() {
        int numStudents = course.getNumStudents();
        assertEquals(16, numStudents);
    }

    @Test
    public void testGetNumAssignments1() {
        int numAssignments = course.getNumAssignments();
        assertEquals(3, numAssignments);
    }

    @Test
    public void testGetNumProjects1() {
        int numProjects;
        numProjects = course.getNumProjects();
        assertEquals(3, numProjects);
    }

    @Test
    public void testGetStudents1() {
        HashSet<Student> studentsRoster = null;
        studentsRoster = course.getStudents();
        assertEquals(16, studentsRoster.size());
    }

    @Test
    public void testGetStudents2() {
        HashSet<Student> studentsRoster = null;
        studentsRoster = course.getStudents();
        Student student = null;
        for (Student s : studentsRoster) {
            if (s.getName().compareTo("Tendai Charpentier") == 0) {
                student = s;
                break;
            }
        }
        assertNotNull(student);
    }

    @Test
    public void testGetStudentsByName1() {
        Student student = null;
        student = course.getStudentByName("Rastus Kight");
        assertEquals(901234512, student.getGtid());
    }

    @Test
    public void testGetStudentsByName2() {
        Student student = null;
        student = course.getStudentByName("Coriander Alfsson");
        assertEquals(98, course.getAttendance(student));
    }

    @Test
    public void testGetStudentsByID1() {
        Student student = course.getStudentByID(901234504);
        assertEquals("Shevon Wise", student.getName());
    }

    @Test
    public void testGetTeam1() {
        Student student = course.getStudentByName("Genista Parrish");
        assertEquals("Team 2", course.getTeam(student));
    }

    @Test
    public void testGetTeam2() {
        Student student = course.getStudentByName("Freddie Catlay");
        assertEquals("Team 1", course.getTeam(student));
    }

    // New tests below

    @Test
    public void testAddAssignment() {
        course.addAssignment("Assignment: category partition");
        course.updateGrades(new Grades(DB));
        assertEquals(4, course.getNumAssignments());
        course.addAssignment("Assignment: white-box testing");
        course.updateGrades(new Grades(DB));
        assertEquals(5, course.getNumAssignments());
    }

    @Test
    public void testAddGradesForAssignment() {
        String assignmentName = "Assignment: category partition";
        Student student1 = new Student("Josepha Jube", 901234502);
        Student student2 = new Student("Christine Schaeffer", 901234508);
        Student student3 = new Student("Ernesta Anderson", 901234510);
        course.addAssignment(assignmentName);
        course.updateGrades(new Grades(DB));
        HashMap<Student, Integer> grades = new HashMap<Student, Integer>();
        grades.put(student1, 87);
        grades.put(student2, 94);
        grades.put(student3, 100);
        course.addGradesForAssignment(assignmentName, grades);
        course.updateGrades(new Grades(DB));
        assertEquals(90, course.getAverageAssignmentsGrade(student1));
        assertEquals(94, course.getAverageAssignmentsGrade(student2));
        assertEquals(93, course.getAverageAssignmentsGrade(student3));
    }

    @Test
    public void testGetAverageAssignmentsGrade() {
        // Rounded to the closest integer
        Student student = new Student("Ernesta Anderson", 901234510);
        assertEquals(90, course.getAverageAssignmentsGrade(student));
    }

    @Test
    public void testGetAverageProjectsGrade() {
        // Rounded to the closest integer
        Student student = new Student("Rastus Kight", 901234512);
        assertEquals(86, course.getAverageProjectsGrade(student));
    }

    @Test
    public void testAddIndividualContributions() {
        String projectName1 = "Project 2";
        Student student1 = new Student("Josepha Jube", 901234502);
        Student student2 = new Student("Grier Nehling", 901234503);
        HashMap<Student, Integer> contributions1 = new HashMap<Student, Integer>();
        contributions1.put(student1, 96);
        contributions1.put(student2, 87);
        course.addIndividualContributions(projectName1, contributions1);
        course.updateGrades(new Grades(DB));
        String projectName2 = "Project 3";
        HashMap<Student, Integer> contributions2 = new HashMap<Student, Integer>();
        contributions2.put(student1, 98);
        contributions2.put(student2, 100);
        course.addIndividualContributions(projectName2, contributions2);
        course.updateGrades(new Grades(DB));
        assertEquals(90, course.getAverageProjectsGrade(student1));
        assertEquals(84, course.getAverageProjectsGrade(student2));
    }
    
    @Test
    public void testFormulaUpdate1() {
    	course.setFormula("AVGA * .5 + AVGP * .5");
        Student student = new Student("Kym Hiles", 901234507);
        assertEquals(92, course.getOverallGrade(student));
    }

    @Test
    public void testFormulaUpdate2() {
    	course.setFormula("99.5");
        Student student = new Student("Genista Parrish", 901234509);
        assertEquals(100, course.getOverallGrade(student));
    }

    @Test(expected = GradeFormulaException.class)
    public void testFormulaUpdate3() {
    	course.setFormula("ATT * 02 + AVG * 0.5 + AVGP * 0.3");
        Student student = new Student("Genista Parrish", 901234509);
        assertEquals(100, course.getOverallGrade(student));
    }
}
