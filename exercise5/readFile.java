import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class readFile {
	/*****************************
	 * 		Variables
	 ****************************/
	//change path here
	private static String inFileName = "src/SpamInstances.txt";
	private static Scanner inFile = null;
	private static ArrayList<String> spam = new ArrayList<String>();
	private static ArrayList<String> nonspam = new ArrayList<String>();
	
	
	/*******************************************
	 * 	findFile
	 * 		This method is used to find and set the file containing
	 * 		the data.
	 * 
	 *******************************************/
	public static void findFile(){
	
		try{
			inFile = new Scanner(new File(inFileName));
			//System.out.println("Data retrieved.");
		}
		catch(FileNotFoundException e){
			System.out.println("File " + inFileName + " not found.");
			System.exit(0);
		}
	}
	
	public static void initializeData(){
		findFile();
		String[] stringData;
	
		//-----------------------
		//	Extracting the data 
		//	from the text file
		//-----------------------
		
		String line = inFile.nextLine();
		stringData = line.split(" ");
		
		while(inFile.hasNext()){
			line = inFile.nextLine();
			stringData = line.split(" ");

			int spamCheck = Integer.parseInt(stringData[1]); //stores int in column 2
			line = stringData[2]; //stores the string of features 
			
			if(spamCheck == 1)
				spam.add(line);
			else
				nonspam.add(line);
		}
	}
	
	public static ArrayList<String> getSpam(){
		return spam;
	}
	
	public static ArrayList<String> getNonSpam(){
		return nonspam;
	}
	
}
