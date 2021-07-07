package com.devicelist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileManager extends Validations{

	private final String userDir = System.getProperty("user.dir");
	private final String fileSeparator = System.getProperty("file.separator");
	private final String listFileName = "list.txt";
	private final String listTxtFileLocation = userDir + fileSeparator + listFileName;
	public List<String> fileContentList = null;
	private int numberOfItems = 0;

	public void generateFile(){
		
		File listFile = new File(listTxtFileLocation);
		
		try {
			
			if(!listFile.exists()){
				
				System.out.println(String.format("Creating file: %s...", listFileName));
				
				if(listFile.createNewFile()){
					
					System.out.println(String.format("Created file: %s.", listFileName));
					
					System.out.println(String.format("File location: %s", listTxtFileLocation));
				}
				
			}else{
				
				System.out.println(String.format("File location: %s", listTxtFileLocation));
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractListFromFile(){
		try {
			fileContentList = Files.readAllLines(Paths.get(listTxtFileLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printTheList(){
		
		extractListFromFile();
		
		System.out.println(String.format("File content of: %s", listFileName));
		
		for(String listItem : fileContentList){
			System.out.println(listItem);
		}
	}

	public void addHeader(String headerName){;
	
		Validations validations = new Validations();
		
		extractListFromFile();
		
		validations.checkIfHeaderAlreadyExists();
		
		if(validations.doesHeaderExist == false){
			try {
				
				FileWriter writer = new FileWriter(listTxtFileLocation);
				
				writer.write(headerName);
				
				writer.close();
				
				System.out.println(String.format("Added header to file: %s | Header name value: %s", listFileName, headerName));
				
			} catch (IOException ioException){
				ioException.printStackTrace();
			}			
		}
	}
	
	public void promptToInputAValue(){
		
		String input = "";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Do you want to input a value? ");

		try {
			input = reader.readLine().toUpperCase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(input.equals("Y") || input.contains("YES")){
			
			System.out.print("How many items do you want to input? ");
			
			try {
				numberOfItems = Integer.parseInt(reader.readLine());
				inputValue();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
				
		}	
		
		printTheList();
	}
	
	public void inputValue(){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Validations validation = new Validations();
		
		for(int iteration = 1; iteration <= numberOfItems; iteration++){
			
			System.out.print("Input a value: ");
			
			try {
				
				validation.inputValue = reader.readLine();
				
				if(validation.checkInputLength() == true || validation.metMinimumInputLength == true){
					
					try {
						
						FileWriter writer = new FileWriter(listTxtFileLocation, true);
						
						writer.write(System.lineSeparator());
						
						writer.write(validation.inputValue);
						
						writer.close();
						
					} catch (IOException ioexception){
						ioexception.printStackTrace();
					}
					
				}
				
			} catch (IOException e){
				
				e.printStackTrace();
				
			}
		}
		
	}
}