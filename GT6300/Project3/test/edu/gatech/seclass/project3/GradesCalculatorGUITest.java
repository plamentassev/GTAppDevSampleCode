package edu.gatech.seclass.project3;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.*;

import java.awt.TextArea;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GradesCalculatorGUITest {

    GradesCalculatorGUI gui = null;
    static final String DB = "DB" + File.separator + "CourseDatabase6300.xlsx";
    static final String DB_GOLDEN = "DB" + File.separator + "CourseDatabase6300-golden.xlsx";
    JComboBox<String> studentsComboBox = null;
    JTextField formulaField = null;
    JEditorPane studentInfoArea = null;
    JPanel formulaPanel = null;
    JButton updateFormulaButton = null;

    @Before
    public void setUp() throws Exception {
        FileSystem fs = FileSystems.getDefault();
        Path gradesdbfilegolden = fs.getPath(DB_GOLDEN);
        Path gradesdbfile = fs.getPath(DB);
        Files.copy(gradesdbfilegolden, gradesdbfile, REPLACE_EXISTING);
        gui = new GradesCalculatorGUI(DB);
        // Getting the GUI elements
        Field field = null;
        field = gui.getClass().getDeclaredField("studentsComboBox");
        field.setAccessible(true);
        studentsComboBox = (JComboBox<String>) field.get(gui);
        field = gui.getClass().getDeclaredField("studentInfoArea");
        field.setAccessible(true);
        studentInfoArea = (JEditorPane) field.get(gui);
        field = gui.getClass().getDeclaredField("formulaField");
        field.setAccessible(true);
        formulaField = (JTextField) field.get(gui);
        field = gui.getClass().getDeclaredField("formulaPanel");
        field.setAccessible(true);
        formulaPanel = (JPanel) field.get(gui);
        field = gui.getClass().getDeclaredField("updateFormulaButton");
        field.setAccessible(true);
        updateFormulaButton = (JButton) field.get(gui);
    }

    @After
    public void tearDown() throws Exception {
        gui = null;
    }

    @Test
    public void testStudentsInfoGUI() throws InterruptedException {
        studentsComboBox.setSelectedItem("Rastus Kight");
        Thread.sleep(500);
        String output = studentInfoArea.getText().trim();
        String expectedOutput = "Name: Rastus Kight\nEmail: rk@gatech.edu\nGTID: 901234512\n\nAttendance: 97\nAverage assignments grade: 80\nAverage projects grade: 86\n\nOverall Grade: 86";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testFormulaUpdateGUI1() throws InterruptedException {
        formulaField.setText("ATT * 0.2 + AVGA * 0.3 + AVGP * 0.5");
        updateFormulaButton.doClick();
        studentsComboBox.setSelectedItem("Freddie Catlay");
        studentsComboBox.revalidate();
        studentsComboBox.repaint();
        String output = studentInfoArea.getText().trim();
        String expectedOutput = "Name: Freddie Catlay\nEmail: fc@gatech.edu\nGTID: 901234501\n\nAttendance: 93\nAverage assignments grade: 90\nAverage projects grade: 81\n\nOverall Grade: 86";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testFormulaUpdateGUI2() throws InterruptedException {
        formulaField.setText("AVGP * 1");
        updateFormulaButton.doClick();
        studentsComboBox.setSelectedItem("Laraine Smith");
        studentsComboBox.revalidate();
        studentsComboBox.repaint();
        String output = studentInfoArea.getText().trim();
        String expectedOutput = "Name: Laraine Smith\nEmail: ls@gatech.edu\nGTID: 901234505\n\nAttendance: 100\nAverage assignments grade: 100\nAverage projects grade: 74\n\nOverall Grade: 74";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testFormulaUpdateGUI3() throws InterruptedException {
        formulaField.setText("AVGA * 0.6 + AVGP * 0.4");
        updateFormulaButton.doClick();
        studentsComboBox.setSelectedItem("Caileigh Raybould");
        studentsComboBox.revalidate();
        studentsComboBox.repaint();
        String output = studentInfoArea.getText().trim();
        String expectedOutput = "Name: Caileigh Raybould\nEmail: cr@gatech.edu\nGTID: 901234506\n\nAttendance: 83\nAverage assignments grade: 98\nAverage projects grade: 71\n\nOverall Grade: 87";
        assertEquals(expectedOutput, output);
    }
}
