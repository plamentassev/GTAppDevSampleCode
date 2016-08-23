package edu.gatech.seclass.project3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.io.File;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Course {
	
	private String url;
	File file = null;
	FileInputStream fis = null;
	XSSFWorkbook workBook = null;
	XSSFSheet spreadsheet = null;
	private String formula = "ATT * 0.2 + AVGA * 0.4 + AVGP * 0.4"; 
	
	public Course(String db) {
		try{
			url = db;
			file = new File(url);
			fis = new FileInputStream(file);
			workBook = new XSSFWorkbook(fis);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public String getFormula() {
		return formula;

	}
	
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public int getNumStudents(){
		XSSFSheet sheet = workBook.getSheet("Students");
		int numStudent = sheet.getLastRowNum();
		return numStudent;
	}
	public int getNumAssignments(){
		XSSFSheet sheet = workBook.getSheet("IndividualGrades");
		int numAssignments = sheet.getRow(0).getLastCellNum() - 1;
		return numAssignments;
	}
	
	public int getNumProjects() {
		XSSFSheet sheet = workBook.getSheet("IndividualContribs");
		int numProjects = sheet.getRow(0).getLastCellNum() - 1;
		return numProjects;
	}
	
	public HashSet<Student> getStudents() {
		Students students = new Students(url);
		HashSet<Student> studentSet = students.getAllStudents();
		return studentSet;
	}
	
	public Student getStudentByName(String name){
		Students students = new Students(url);
		Student result =  students.getStudentByName(name);
		return result;
	}
	
	public Student getStudentByID(int id){
		Students students = new Students(url);
		Student result =  students.getStudentByID(id);
		return result;
	}
	public int getAverageAssignmentsGrade(Student student) {
		Grades grades = new Grades(url);
		int result = grades.getAverageAssignmentsGrade(student);
		return result;
	}
	public int getAverageProjectsGrade(Student student) {
		Grades grades = new Grades(url);
		int result = grades.getAverageProjectsGrade(student);
		return result;
	}
	
	public void updateGrades(Grades grades1) {
		try{
			url = grades1.getStrDb();
			file = new File(url);
			fis = new FileInputStream(file);
			workBook = new XSSFWorkbook(fis);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	
	public void addAssignment(String add_assignment) {
		XSSFSheet sheet = workBook.getSheet("IndividualGrades");
		Row row = sheet.getRow(0);
		int lastCellNum = row.getLastCellNum();
		Cell theCell = row.createCell(lastCellNum);
		theCell.setCellValue(add_assignment);
		FileOutputStream out;
		try{
			out = new FileOutputStream(file);
			workBook.write(out);
			out.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	

	public void addGradesForAssignment(String assignmentName, HashMap<Student, Integer> grades) {
		
		XSSFSheet wsIndividualGrades = workBook.getSheet("IndividualGrades");
		FileOutputStream out;

		try
		{
			int cellNumber = 0;
			Set<Student> studentKeys = grades.keySet();
			Iterator<Student> it =studentKeys.iterator();
			while(it.hasNext()){
				Student student = it.next();
				int valueGrade = grades.get(student);

				for (Row row : wsIndividualGrades) {
					for (Cell cell : row) {
						if(cell.getCellType() == Cell.CELL_TYPE_STRING && 
								cell.getStringCellValue().trim().equalsIgnoreCase(assignmentName)){
							cellNumber = cell.getColumnIndex();
						}
						else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							if ((int)cell.getNumericCellValue() == student.getGtid()){
								cell = row.createCell(cellNumber);
								cell.setCellValue(valueGrade);
								break;
							}
						}
					}
				}
			}
			out = new FileOutputStream(file);
			workBook.write(out);
			out.close();
		} 	

		catch (Exception e) {
			e.printStackTrace();
		}      
	}
	
	public void addIndividualContributions(String projectName, HashMap<Student, Integer> contributions) {
		XSSFSheet individualContribsSheet = workBook.getSheet("IndividualContribs");
		FileOutputStream out;
		try
		{
			int cellNumber = 0;
			Set<Student> studentKeys = contributions.keySet();
			Iterator<Student> it =studentKeys.iterator();
			while(it.hasNext()){
				Student student = it.next();
				int gradeValue = contributions.get(student);
				for (Row row : individualContribsSheet) {
					for (Cell cell : row) {
						if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().equalsIgnoreCase(projectName)){
							cellNumber=cell.getColumnIndex();
						}
						else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							if ((int)cell.getNumericCellValue() == student.getGtid()){
								cell = row.createCell(cellNumber);
	            				cell.setCellValue(gradeValue);
	            				break;
							}
						}
					}
				}
			}
			out = new FileOutputStream(file);
			workBook.write(out);
			out.close();
		} 	

		catch (Exception e) {
			e.printStackTrace();
		}      
		
	}

	public void addStudent(Student student) {
		// TODO Auto-generated method stub
		
	}

	public void addProject(String string) {
		// TODO Auto-generated method stub
		
	}

	public void addGradesForProject(String projectName, Grades grades,
			HashMap<String, Integer> gradesTeam) {
		// TODO Auto-generated method stub
		
	}

	public int getAttendance(Student student) {
		return student.getAttendance();
	}

	public String getTeam(Student student) {
		return student.getTeam();
	}


	public int getOverallGrade(Student student) throws GradeFormulaException{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		Grades grades = new Grades(url);
		try{
			engine.put("ATT", student.getAttendance());
			engine.put("AVGA", grades.getAverageAssignmentsGrade(student));
			engine.put("AVGP", grades.getAverageProjectsGrade(student));
			return (int) Math.round((Double) engine.eval(formula));
		}catch(ScriptException e){
			throw new GradeFormulaException("Invalid formula");
		}
		
	}
	public String getEmail(Student student) {
		return student.getEmail();
	}
}
