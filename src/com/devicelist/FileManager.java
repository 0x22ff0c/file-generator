package com.devicelist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileManager extends Validations{

	private static final String CURRENT_USER_DIRECTORY = System.getProperty("user.dir");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String FILE_NAME = "list.txt";
	private static final String FILE_LOCATION = CURRENT_USER_DIRECTORY + FILE_SEPARATOR + FILE_NAME;
	private List<String> fileContentList = null;
	private int numberOfItems = 0;

	private static final Logger LOGGER = LogManager.getLogger(FileManager.class);
	
	public List<String> getFileContentList(){
		return fileContentList;
	}
	
	public void setFileContentList(List<String> fileContentList){
		this.fileContentList = fileContentList;
	}		
	
	public void generateFile(){
		
		File listFile = new File(FILE_LOCATION);
		
		try {
			
			if(!listFile.exists()){
				
				LOGGER.debug(String.format("Creating file: %s..,", FILE_NAME));
				
				if(listFile.createNewFile()){
					
					LOGGER.debug(String.format("Created file: %s", FILE_NAME));
					
					LOGGER.debug(String.format("File location: %s", FILE_LOCATION));
					
				}

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		LOGGER.debug(String.format("File location: %s", FILE_LOCATION)); 	
	}
	
	public void extractListFromFile(){
		try {
			fileContentList = Files.readAllLines(Paths.get(FILE_LOCATION));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void printTheList(){
		
		extractListFromFile();
		
		System.out.println(String.format("File content of: %s", FILE_NAME));
		
		for(String listItem : fileContentList){
			System.out.println(listItem);
		}
	}

	public void addHeader(String headerName){
	
		Validations validations = new Validations();
		
		extractListFromFile();
		
		validations.checkIfHeaderAlreadyExists();
		
		if(validations.getDoesHeaderExistResult() == false){
			
			try(FileWriter writer = new FileWriter(FILE_LOCATION)){
				
				writer.write(headerName);
				
				System.out.println(String.format("Added header to file: %s | Header name value: %s", FILE_NAME, headerName));
				
			} catch (IOException ioException){
				
				ioException.printStackTrace();
			}		
		}
	}
	
	public void askUserAnInput(){
		
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
				
				validation.setInputValue(reader.readLine());
				
				if(validation.checkInputLength() == true || validation.getMetMinInputLengthResult() == true){
					
					try (FileWriter writer = new FileWriter(FILE_LOCATION, true)){
						
						writer.write(System.lineSeparator());
						
						writer.write(validation.getInputValue());
						
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