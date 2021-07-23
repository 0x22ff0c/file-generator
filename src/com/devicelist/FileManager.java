package com.devicelist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
	private String headerName = "";
	private int itemNumber = 0;

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
				
				LOGGER.info("Creating file: {}...", FILE_NAME);

				if(listFile.createNewFile()){
					
					LOGGER.info("Created file: {}", FILE_NAME);
					
					LOGGER.info("File location: {}", FILE_LOCATION);
					
				}
			}else {
				
				LOGGER.info("File location: {}", FILE_LOCATION); 	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		
		LOGGER.info("File content of: {}", FILE_NAME);
		
		int itemNum = 1;
		
		for(String listItem : fileContentList){
			
			if(!listItem.matches(headerName)){
				
				LOGGER.info("Item number: {} | Value: {}", itemNum, listItem);
		
				itemNum++;
			}			
		}
	}

	public void addHeader(String headerName){
	
		this.headerName = headerName;
		
		extractListFromFile();
		
		checkIfHeaderAlreadyExists();
		
		if(!getDoesHeaderExistResult()){
			
			try(FileWriter writer = new FileWriter(FILE_LOCATION)){
				
				writer.write(this.headerName);
				
				LOGGER.info("Added header to file: {} | Header name: \"{}\"", FILE_NAME, this.headerName);
			
			} catch (IOException ioException){
				
				ioException.printStackTrace();
			}		
		}
	}
	
	public void askUserIfItWantsToAddItemsOnTheList(){
		
		String decision = "";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		LOGGER.info("Do you want to add items on the list? ");

		try {
			decision = reader.readLine().toUpperCase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(decision.equals("Y") || decision.contains("YES")){
			
			askUserHowManyItemsToAdd();
		}	
	}
	
	private void askUserHowManyItemsToAdd(){
		
		String numberOfITems = "";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		LOGGER.info("How many items do you wish to add? ");
	
		try {
			numberOfITems = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(checkIfInputIsDigit(numberOfITems)){
			
			this.numberOfItems = Integer.parseInt(getInputValue());
		
		}
	}

	public void addItemsOnTheList(){
		
		if(numberOfItems != 0){
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			for(int iteration = 1; iteration <= numberOfItems; iteration++){
				
				LOGGER.info("Input a value: ");
				
				try {
						
					setInputValue(reader.readLine());

					while(!doesInputLengthMeetMinRequiredLength()){
						
						LOGGER.info("Input another value: ");
						
						setInputValue(reader.readLine());
						
					}
					
					addValueToTheList();
					
				} catch (IOException e){
					
					e.printStackTrace();
					
				}
			}
		}
	}
	
	private void addValueToTheList(){
		
		try (FileWriter writer = new FileWriter(FILE_LOCATION, true)){
			
			writer.write(System.lineSeparator());
			
			writer.write(getInputValue());
			
		} catch (IOException ioexception){
			
			ioexception.printStackTrace();
		}		
	}

	public void askUserAnInputForItemNumber(){

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		LOGGER.info("Enter the item number you wish appium to run on: ");
		
		try {
		
			setInputValue(reader.readLine());
			
			checkIfInputIsDigit(getInputValue());
			
			checkIfInputIsInRange();

		} catch (IOException e) {
			
			e.printStackTrace();
		
		}	
	}
	
	public void printSelectedItemOnTheList(){
		
		itemNumber = Integer.parseInt(getInputValue());
		
		LOGGER.info("Appium will connect to this device id: {}", getFileContentList().get(itemNumber));
		
	}
	
	public void markSelectedItemOnTheList(){
		
		String inputString = "";
		
		String itemValue = getFileContentList().get(itemNumber);
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_LOCATION))){
			
			StringBuilder stringBuffer = new StringBuilder();
			
			String line;
			
			while((line = reader.readLine()) != null){
				stringBuffer.append(line.trim());
				stringBuffer.append(System.lineSeparator());
			}
			
			inputString = stringBuffer.toString();

			if(inputString.contains("[run]")){
				inputString = inputString.replace("[run] ", "");				
			}
			
			inputString = inputString.replace(itemValue, "[run] " + itemValue);
			
			//Remove extra line separator obtained when we add extracted lines to stringBuffer variable.
			inputString = inputString.substring(0, inputString.length() - 1);
			
		} catch (IOException e){
			
			e.printStackTrace();
		}	
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(new File(FILE_LOCATION))){
			
			fileOutputStream.write(inputString.getBytes());	
			
		} catch (IOException e){
			
			e.printStackTrace();
		}
	}
}