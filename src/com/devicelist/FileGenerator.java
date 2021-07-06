package com.devicelist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {

	private final String userDir = System.getProperty("user.dir");
	private final String fileSeparator = System.getProperty("file.separator");
	private final String listFileName = "list.txt";
	private final String listTxtFileLocation = userDir + fileSeparator + listFileName;
	
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

	public void addHeader(String headerName){;

		try {
			
			FileWriter writer = new FileWriter(listTxtFileLocation);
			
			writer.write(headerName);
			
			writer.close();
			
		} catch (IOException ioException){
			ioException.printStackTrace();
		}
		
	}
	
	
}
