package com.devicelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validations{
	
	private String numberInput = "";
	private String inputValue = "";
	private boolean isDigit = false;
	private boolean isInRange = false;
	private boolean doesHeaderExist = false;
	private boolean metMinimumInputLength = false;
	
	private static final Logger LOGGER = LogManager.getLogger(Validations.class);
	
	public String getInputValue(){
		return inputValue;
	}
	
	public void setInputValue(String inputValue){
		this.inputValue = inputValue;
	}
	
	public boolean getDoesHeaderExistResult(){
		return doesHeaderExist;
	}
	
	public void setDoesHeaderExistValue(boolean doesHeaderExist){
		this.doesHeaderExist = doesHeaderExist;
	}
	
	public boolean getMetMinInputLengthResult(){
		return metMinimumInputLength;
	}
	
	public void setMetMinInputLengthResult(boolean metMiniumumInputLength){
		this.metMinimumInputLength = metMiniumumInputLength;
	}
		
	protected boolean checkIfHeaderAlreadyExists() throws IndexOutOfBoundsException{
		
		doesHeaderExist = false;

		FileManager fileManager = new FileManager();
		
		fileManager.extractListFromFile();
			
		try {

			String headerName = fileManager.getFileContentList().get(0);
			
			if(headerName.length() > 1){
				
				doesHeaderExist = true;
				
				LOGGER.info("Header of the file: \"{}\"", headerName);
				
			}
			
		} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			
			LOGGER.warn("Header in the given file does not exist.");
			
		}

		return doesHeaderExist;
	}
	
	protected boolean doesInputLengthMeetMinRequiredLength(){
		
		metMinimumInputLength = false;
		
		if(inputValue.length() <= 1){
			
			LOGGER.error("Input is too short.");
			
		}else{
			
			metMinimumInputLength = true;
		
		}
		
		return metMinimumInputLength;
	}
	
	protected boolean checkIfInputIsDigit(String testData){
	
		numberInput = testData;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		if(!testData.matches("\\d+")){
	
			while(!isDigit){

				LOGGER.error("Input is not a digit.");
				
				LOGGER.info("Enter a number: ");
				
				try {
					numberInput = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(numberInput.matches("\\d+")){
					isDigit = true;
					setInputValue(numberInput);
				}
			}
		}else {
			isDigit = true;
			setInputValue(numberInput);
		}
		
		return isDigit;
	}
	
	protected boolean checkIfInputIsInRange(){
		
		FileManager fileManager = new FileManager();
		
		fileManager.extractListFromFile();
		
		List<String> extractedArrayList = fileManager.getFileContentList();
		
		int minimumListItemNumber = 1;
		
		int maximumListItemNumber = extractedArrayList.size() - 1;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			int rangeNumber = Integer.parseInt(getInputValue());
			
			if(rangeNumber == 0 || rangeNumber > maximumListItemNumber){
				
				while(!isInRange){
					
					LOGGER.error("Number should be between {} and {}", minimumListItemNumber, maximumListItemNumber);
				
					LOGGER.info("Enter a number between {} and {}", minimumListItemNumber, maximumListItemNumber);

					numberInput = reader.readLine();
					
					rangeNumber = Integer.parseInt(numberInput);
					
					if(rangeNumber != 0 && rangeNumber < maximumListItemNumber){
						
						isInRange = true;
						
						setInputValue(String.valueOf(rangeNumber));
					
					}
				}
			}
		
		} catch (NumberFormatException numberFormatException){
			checkIfInputIsDigit(numberInput);
			isDigit = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isInRange;
	}

	protected boolean checkIfInputIsDigitAndInRange(String testData){
		
		boolean isDigitAndInRange = false;
		
		numberInput = testData;
		
		if(!checkIfInputIsInRange()){
			
			while (!checkIfInputIsInRange()){
				
				checkIfInputIsInRange();
			}	
		}else {
			
			isDigitAndInRange = true;
		}
		
		return isDigitAndInRange;
	}
}