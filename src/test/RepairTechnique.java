package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class RepairTechnique {

	public File repair(String new_file, String old_file){
		System.out.println("old file:"+old_file); 
		System.out.println("new file:"+new_file); 

		
		File file = new File(new_file); 
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(new_file), "utf-8"));
		    writer.write("Something");
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {/*ignore*/}
		}
		return file; 
	}
}
