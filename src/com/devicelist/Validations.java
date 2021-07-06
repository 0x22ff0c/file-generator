package com.devicelist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Validations{
	
	private String numberInput = "";
	public String inputValue = "";
	private boolean isDigit = false;
	private boolean isInRange = false;
	
	public void checkInputLength(){
		
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
			}
		}
	}
	
	public void checkIfInputIsDigit(String testData){
	
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
	
	public void checkIfInputIsInRange(){
		
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

	public void checkIfInputIsDigitAndInRange(String testData){
		
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