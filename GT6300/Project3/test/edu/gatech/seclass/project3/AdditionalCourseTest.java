package edu.gatech.seclass.project3;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.assertEquals;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdditionalCourseTest {
	
    Students students = null;
    Grades grades = null;
    Course course = null;
    static final String DB = "DB/CourseDatabase6300.xlsx";
    static final String DB_GOLDEN = "DB/CourseDatabase6300-golden.xlsx";

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
        grades = null;
        course = null;
    }
    
    @Test
    public void testAddStudent(){
    	Student student = new Student("Plamen Tassev", 9093456, course);
    	course.addStudent(student);
    	assertEquals(student, course.getStudentByName("Plamen Tassev"));
    	
    	Student student2 = new Student("Ivan Kolev", 9096789, course);
    	course.addStudent(student2);
    	assertEquals(student2, course.getStudentByID(9096789));
    }
    
    @Test
    public void testAddProject(){
        course.addProject("Project 4");
        course = new Course(DB);
        assertEquals(4, course.getNumProjects());
        course.addProject("Project 5");
        course = new Course(DB);
        assertEquals(5, course.getNumProjects());
    }
    
    @Test
    public void testAddGradesForProject(){
        String projectName = "Project 6";
        Student student = new Student("Coriander Alfsson", 901234516, course);
        HashMap<Student, Integer> gradesIndividual = new HashMap<Student, Integer>();
        HashMap<String, Integer> gradesTeam = new HashMap<String, Integer>();
        course.addProject(projectName);
        course = new Course(DB);
        course.addIndividualContributions(projectName, gradesIndividual);
        gradesIndividual.put(student, 100);
        gradesTeam.put("Team 3", 99);
        course.addGradesForProject(projectName, grades, gradesTeam);
        course = new Course(DB);
        
        assertEquals(98, course.getAverageProjectsGrade(student), 1);
    }

}