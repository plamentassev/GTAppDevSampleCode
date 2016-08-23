package edu.gatech.seclass.project3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Grades {
	private String url;
	File file = null;
	FileInputStream inputStream = null;
    XSSFWorkbook workBook = null;
	XSSFSheet spreadsheet = null;
	String strDb = null;

	public Grades(String db) {
		try{
			url = db;
			file = new File(url);
			inputStream = new FileInputStream(file);
			workBook = new XSSFWorkbook(inputStream);
			strDb = db;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public String getStrDb() {
		return strDb;
	}

	public void setStrDb(String test) {
		this.strDb = test;
	}
	
	public int getAverageAssignmentsGrade(Student student1) {
		double average=0.0;
		double sum=0.0;
		XSSFSheet wsIndividualGrades = workBook.getSheet("IndividualGrades");
		try
		{
			int counter=0;
			Row rowNum = wsIndividualGrades.getRow(0);
			int cellsTotal = rowNum.getLastCellNum();
			for (Row row : wsIndividualGrades) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if ((int)cell.getNumericCellValue() == student1.getGtid()) {
							for(int i = 1; i < cellsTotal; i++){
								Cell cell2 = row.getCell(i);
								if(cell2!=null){
									cell2.setCellType(Cell.CELL_TYPE_STRING);
									if(!cell2.getStringCellValue().trim().equals("")){
										sum = sum +Double.parseDouble(cell2.getStringCellValue());
										counter++;
									}
								}
							}
						}
					}
				}
			}
			average = sum/counter;

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return (int)Math.round(average);
	}
	
	public int getAverageProjectsGrade(Student student) {
		double average=0, sum=0;		
		XSSFSheet IndividualContribsSheet = workBook.getSheet("IndividualContribs");
		XSSFSheet TeamsSheet = workBook.getSheet("Teams");
		XSSFSheet TeamGradesSheet = workBook.getSheet("TeamGrades");

		try
		{
			
			Row rowNum = IndividualContribsSheet.getRow(0);
			int totalCells = rowNum.getLastCellNum();
			int counter=0;
			double[] projectGrades = new double[totalCells-1];
			double[] teamGrades = new double[totalCells-1];
			for (Row row : IndividualContribsSheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if ((int)cell.getNumericCellValue() == student.getGtid()) {
							for(int i=1;i<totalCells;i++){
								Cell cell2 = row.getCell(i);							
								if(null !=cell2 ){
									cell2.setCellType(Cell.CELL_TYPE_STRING);
									if(!cell2.getStringCellValue().trim().equals("")){									
										projectGrades[i-1] = Double.parseDouble(cell2.getStringCellValue());
										counter++;
									}
								}
							}
							break;
						}
					}
				}
			}
			
			rowNum = TeamsSheet.getRow(0);
			String teamName ="";
			for (Row row : TeamsSheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if ((int)cell.getNumericCellValue() == student.getGtid()) {
							teamName  = row.getCell(0).getStringCellValue().trim();
							break;
						}
					}
				}
			}

			 rowNum = TeamGradesSheet.getRow(0);
			 totalCells = rowNum.getLastCellNum();	
			for (Row row : TeamGradesSheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if (cell.getStringCellValue().trim().equalsIgnoreCase(teamName)) {
							for(int i = 1; i < totalCells; i++){
								Cell cell2 = row.getCell(i);
								cell2.setCellType(Cell.CELL_TYPE_STRING);
								teamGrades[i-1] = Double.parseDouble(cell2.getStringCellValue());
							}
							break;
						}
					}
				}
			}
			
			for(int i=0;i<counter;i++){
				sum=sum+((projectGrades[i] * teamGrades[i])/100);
			}
			
			average=sum/counter;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return (int) Math.round(average);
	}
}
