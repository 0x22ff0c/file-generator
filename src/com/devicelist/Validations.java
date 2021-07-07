package com.devicelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Validations{
	
	private String numberInput = "";
	public String inputValue = "";
	private boolean isDigit = false;
	private boolean isInRange = false;
	public boolean doesHeaderExist = false;
	public boolean decision = false;
	public boolean metMinimumInputLength = false;
	
	protected boolean checkIfHeaderAlreadyExists() throws IndexOutOfBoundsException{
		
		doesHeaderExist = false;
		
		FileManager fileManager = new FileManager();
		
		fileManager.extractListFromFile();
			
		try {

			String headerName = fileManager.fileContentList.get(0);
			
			if(headerName.length() > 1){
				doesHeaderExist = true;
				System.out.println(String.format("Header in the given text file: %s", headerName));
			}
			
		} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
			System.out.println("Warning: Header in the given text file does not exist.");
		}

		return doesHeaderExist;
	}
	
	protected boolean checkInputLength(){
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String decision = "";
		
		if(inputValue.length() <= 1){
			
			System.out.println("Warning: Input may be too short.");
			
			while(!decision.equals("Y") || !decision.equals("YES") || !decision.contains("YES")){
				System.out.print("Do you want to proceed?");
				
				try {
					decision = reader.readLine().toUpperCase();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(decision.equals("Y") || decision.equals("YES") || !decision.contains("YES")){
					this.decision = true;
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
	
			while(isDigit == false){

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
		
		while (isDigit == false && isInRange == false){
			
			if(isDigit == false){
				checkIfInputIsDigit(numberInput);
			}
			
			if(isInRange == false){
				checkIfInputIsInRange();
			}
		}	
	}
}