package com.devicelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validations{
	
	private String numberInput = "";
	private String inputValue = "";
	private boolean isDigit = false;
	private boolean isInRange = false;
	private boolean doesHeaderExist = false;
	private boolean decision = false;
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
	
	protected boolean checkInputLength(){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String userDecision = "";
		
		if(inputValue.length() <= 1){
			
			System.out.println("Warning: Input may be too short.");
			
			while(!userDecision.equals("Y") || !userDecision.equals("YES") || !userDecision.contains("YES")){
				System.out.print("Do you want to proceed?");
				
				try {
					userDecision = reader.readLine().toUpperCase();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(userDecision.equals("Y") || userDecision.equals("YES") || !userDecision.contains("YES")){
					decision = true;
					break;
				}	
			}
			
		}else{
			metMinimumInputLength = true;
		}
		
		return this.decision;
	}
	
	protected void checkIfInputIsDigit(String testData){
	
		numberInput = testData;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		if(!testData.matches("\\d+")){
	
			while(isDigit != true){

				System.out.println("Error: Input is not a digit.");
				
				System.out.print("Enter a number: ");
				
				try {
					numberInput = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(numberInput.matches("\\d+")){
					isDigit = true;
				}
			}
			
		}
	}
	
	protected void checkIfInputIsInRange(){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			int rangeNumber = Integer.parseInt(numberInput);
			
			if(rangeNumber == 0 || rangeNumber > 10){
				
				while(isInRange == false){
					System.out.println("Error: Number should be between 1 and 9.");
				
					System.out.print("Enter a number between 1 and 9: ");
					
					numberInput = reader.readLine();
					
					rangeNumber = Integer.parseInt(numberInput);
					
					if(rangeNumber != 0 && rangeNumber < 10){
						isInRange = true;
					}
				}
			}
		
		} catch (NumberFormatException numberFormatException){
			checkIfInputIsDigit(numberInput);
			isDigit = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void checkIfInputIsDigitAndInRange(String testData){
		
		numberInput = testData;
		
		while (!isDigit && !isInRange){
			
			if(!isDigit){
				checkIfInputIsDigit(numberInput);
			}
			
			if(!isInRange){
				checkIfInputIsInRange();
			}
		}	
	}
}