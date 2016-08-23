package edu.gatech.seclass.project3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;



public class Students {
	private String url;
	File file = null;
	FileInputStream inputStream = null;
    XSSFWorkbook workBook = null;
	XSSFSheet spreadsheet = null;
	
	public Students(String db) {
		try{
			url = db;
			file = new File(url);
			inputStream = new FileInputStream(file);
			workBook = new XSSFWorkbook(inputStream);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public HashSet<Student> getAllStudents() {
		
		HashSet<Student> studentsSet = new HashSet<Student>();
		DataFormatter fmt = new DataFormatter();
		XSSFSheet sheet = workBook.getSheet("Students");
		
		for (int rowNumber = sheet.getFirstRowNum() + 1; rowNumber<=sheet.getLastRowNum(); rowNumber++) {
			Row row = sheet.getRow(rowNumber);
			if (row != null) {
				ArrayList<String> attrList = new ArrayList<String>();
				for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
					Cell cell = row.getCell(columnNumber);
					if (cell == null) {
						attrList.add("null");
					} 
					else {
						String cellStr = fmt.formatCellValue(cell);
						attrList.add(cellStr);
					}
				}
				String name = attrList.get(0);
				int id = Integer.parseInt(attrList.get(1));
				String email = attrList.get(2);
				
				Student student = new Student(name,id, email);
				studentsSet.add(student);
			}
		}
		return studentsSet;
	}
	
	public Student getStudentByName(String in_name) {
		Student returnedStudent = null;
		DataFormatter fmt = new DataFormatter();
		XSSFSheet sheet = workBook.getSheet("Students");

		for (int rowNumber=sheet.getFirstRowNum() + 1; rowNumber<=sheet.getLastRowNum(); rowNumber++) {
			Row row = sheet.getRow(rowNumber);
			if (row != null) {
				ArrayList<String> attrList = new ArrayList<String>();
				for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
					Cell cell = row.getCell(columnNumber);
					if (cell == null) {
						attrList.add("null");
					} 
					else {
						String cellStr = fmt.formatCellValue(cell);
						attrList.add(cellStr);
					}
				}

				String name = attrList.get(0);
				int id = Integer.parseInt(attrList.get(1));
				String email = attrList.get(2);
				
				if (name.compareTo(in_name) == 0) {
					returnedStudent = new Student(name, id, email);
					int attendance = getStudentAttendance(id);
					returnedStudent.setAttendance(attendance);
					
					String team = getStudentTeam(id);
					returnedStudent.setTeam(team);
				}
			}
		}
		return returnedStudent;
	}
	
	public int getStudentAttendance(int in_id) {
		int attendance = 0;
		DataFormatter fmt = new DataFormatter();
		XSSFSheet sheet = workBook.getSheet("Attendance");

		for (int rowNumber = sheet.getFirstRowNum() + 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
			Row row = sheet.getRow(rowNumber);
			if (row != null) {
				ArrayList<String> attrList = new ArrayList<String>();
				for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
					Cell cell = row.getCell(columnNumber);
					if (cell == null) {
						attrList.add("null");
					} 
					else {
						String cellStr;
						if (columnNumber == 1)
							cellStr = String.valueOf(cell.getNumericCellValue());
						else
							cellStr = fmt.formatCellValue(cell);
						attrList.add(cellStr);
					}
				}
				int id = Integer.parseInt(attrList.get(0));
				if (in_id == id) {
					//a bit of really bad code
					attendance = Integer.parseInt(attrList.get(1).substring(0,attrList.get(1).trim().length()-2));
				}
			}
		}
		return attendance;	
	}
	
	public String getStudentTeam(int id) {
		XSSFSheet sheet = workBook.getSheet("Teams");
		DataFormatter fmt = new DataFormatter();
		String teamName ="";
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					String cellStr = fmt.formatCellValue(cell);
					if (cellStr.equalsIgnoreCase(id+ "")) {
						teamName  = row.getCell(0).getStringCellValue().trim();
						break;
					}
				}
			}
		}
		return teamName;	
	}
	
	public Student getStudentByID(int in_id) {
		Student returnedStudent = null;
		DataFormatter fmt = new DataFormatter();
		XSSFSheet sheet = workBook.getSheet("Students"); 

		for (int rowNumber = sheet.getFirstRowNum() + 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
			Row row = sheet.getRow(rowNumber);
			if (row != null) {
				ArrayList<String> attrList = new ArrayList<String>();
				for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
					Cell cell = row.getCell(columnNumber);
					if (cell == null) {
						attrList.add("null");
					} 
					else {
						String cellStr = fmt.formatCellValue(cell);
						attrList.add(cellStr);
					}
				}

				String name = attrList.get(0);
				int id = Integer.parseInt(attrList.get(1));
				String email = attrList.get(2);

				if (in_id == id) {
					returnedStudent = new Student(name,id, email);
				}
			}
		}
		return returnedStudent;
	}
}
