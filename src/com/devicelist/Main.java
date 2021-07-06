package com.devicelist;

public class Main {

	public static void main(String[] args) {
		
		FileGenerator fileGenerator = new FileGenerator();
		
		fileGenerator.generateFile();
		
		fileGenerator.addHeader("Sample text header");
		
		Validations validate = new Validations();
		
		validate.checkIfInputIsDigitAndInRange("a");
	}
}
