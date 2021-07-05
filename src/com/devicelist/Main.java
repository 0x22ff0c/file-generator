package com.devicelist;

public class Main {

	public static void main(String[] args) {
		
		FileGenerator fileGenerator = new FileGenerator();
		
		fileGenerator.generateFile();
		
		Validations validate = new Validations();
		
		validate.checkIfInputIsDigitAndInRange("a");
	}
}
