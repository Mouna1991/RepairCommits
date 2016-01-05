package test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class RepairMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		String input = null;
		System.out.println("Input the path of the test case"); 
		try{
			BufferedReader br = 
	                      new BufferedReader(new InputStreamReader(System.in));
				
			input=br.readLine(); 
				
			if(input!=null){
				System.out.println(input);
				
			}
			
			String s =""; 
			Charset charset = Charset.forName("US-ASCII");
			Path file=Paths.get(input); 
			try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			    String line = null;
				FileWriter fw = new FileWriter("test.java");

			    while ((line = reader.readLine()) != null) {
			       
					fw.write(line);
					fw.write("\n");
					
			      
			    }
			    fw.close();
			  

			  
			} catch (IOException x) {
			    System.err.format("IOException: %s%n", x);
			}
			
				
		}catch(IOException io){
			io.printStackTrace();
		}	
		
		
	          
		File source = new File(input);
		File dest = new File("/Users/mouna/Documents/Research/test/src/test/test.java");
		 
		String path="/Users/mouna/Documents/Research/test/src/test/"; 
		
		try {
			Files.copy(source.toPath(),(new File(path + dest.getName())).toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//path is /Users/mouna/Desktop/test.java

		 Result result = JUnitCore.runClasses(test.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	      if(result.wasSuccessful()){
	    	  System.out.println("******************************");
	 	      System.out.println("Test case passed for Version 0");
	 	      System.out.println("******************************");
	      }
		
	    
	      System.out.println("Enter the URLS of the next commits/versions and write END when you're done"); 
	      
	      /*
	       *    http://cse.unl.edu/~mouna/WebApps/AddressBook/addressbookv3.3.1/
		   *	http://cse.unl.edu/~mouna/WebApps/AddressBook/addressbookv3.3.2/
		   *	http://cse.unl.edu/~mouna/WebApps/AddressBook/addressbookv3.3.3/
		   *	http://cse.unl.edu/~mouna/WebApps/AddressBook/addressbookv3.3.4/
		   *	http://cse.unl.edu/~mouna/WebApps/AddressBook/addressbookv3.3.5/
	       * 
	       * 
	       */
	     int number=0; 
	         try {
	        	 Scanner scanner = new Scanner(System.in);
	        	 BufferedWriter out = new BufferedWriter(new FileWriter("commits.txt"));
	            
	             while(scanner.hasNext()){
	            	
	            	 String s = scanner.next(); 
	            	 if(s.equals("END")){
	            		 break; 
	            	 }
	            	 number++; 
	            	 System.out.println(s);
	            	 out.write(s); 
	            	 out.write("\n"); 
	             }
	             out.close();
	           
	            
	         }
	         catch (IOException e)
	         {
	             System.out.println("Exception ");       
	         }

	         System.out.println("******************************");
	 	     System.out.println("You entered "+number+ " versions");
	 	     System.out.println("******************************");
	 	     System.out.println("STARTING THE AUTOMATIC REPAIR PROCESS");
	 	     System.out.println("******************************");
	    	  
	 	     int counter=1; 
	 	   
	 	     while(counter<=number){
	 	    	 source=dest; 
   	 	     String new_file="/Users/mouna/Documents/Research/test/src/test/test"+counter+".java"; 
	 	    	 dest = new File(new_file); 
	 	 	    try {
	 				Files.copy(source.toPath(),(new File(path + dest.getName())).toPath(),StandardCopyOption.REPLACE_EXISTING);
	 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 	    	 
	 	 	
	 	 	  String LineIwant=GetCommitURL( counter); 
	 	 	//  System.out.println("LINE:"+counter+" "+LineIwant); 
	 	 	  
	 	 	  
	 	 	updateLineClass( new_file,  counter); 	
	 	 	UpdateCommitURL( LineIwant,  counter,  new_file);  
	 	 	    
	 	 	    
	 	     counter++; 
	 	    	 
	 	    	 
	 	    	 
	 	    	 
	 	     }
	 	     
	}
	
	/***************************************************************************/

	private static void UpdateCommitURL(String LineIwant, int count, String new_file) throws IOException {
	    BufferedReader file = new BufferedReader(new FileReader(new_file));
	    String line;
	    String input = "";

	    while ((line = file.readLine()) != null)
	    {
	    	 input += line + "\n";
	    	 if (line.contains("driver.get")){
	    		 String new_line= "driver.get("+"\""+LineIwant+"\""+");"; 
	    		 input = input.replace(line, new_line );
	    	 }
	    	 
	    	 
	    }
	       

	    
	    

	    FileOutputStream os = new FileOutputStream(new_file);
	    os.write(input.getBytes());

	    file.close();
	    os.close();
	} 
	
	
	
	
	/***************************************************************************/
	private static String GetCommitURL(int count) throws IOException{
		String lineIWant = null; 
		FileInputStream fs= new FileInputStream("commits.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 1; i <= count; i++){
			 lineIWant = br.readLine();
			 if(i==count){
				  
				  return lineIWant; 
			 }
		}
		
		return lineIWant; 
	}
	
	/***************************************************************************/

	
	
	
	
	
	
	
	
	private static void updateLineClass(String file_name, int counter) throws IOException {
	    BufferedReader file = new BufferedReader(new FileReader(file_name));
	    String line;
	    String input = "";

	    while ((line = file.readLine()) != null)
	    {
	    	 input += line + "\n";
	    	 if (line.contains("public class")){
	    		 String new_line= "public class test"+counter+" {" ; 
	    		 input = input.replace(line, new_line );
	    	 }
	    	 
	    	 
	    }
	       

	    
	    

	    FileOutputStream os = new FileOutputStream(file_name);
	    os.write(input.getBytes());

	    file.close();
	    os.close();
	} 
	}


