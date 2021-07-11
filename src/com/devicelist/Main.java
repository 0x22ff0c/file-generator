package com.devicelist;

public class Main {

	public static void main(String[] args) {
		
		FileManager fileManager = new FileManager();
		
		fileManager.generateFile();
		
		fileManager.addHeader("Device List:");

		fileManager.askUserAnInput();
	}
}
