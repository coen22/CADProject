package obj;

import java.io.*;


public class ReadFile 
{
	private String path;
	
	//passes the file path to path (to be used again)
	public ReadFile(String file_path)
	{
		path = file_path;
	}
	
	 private int readLines() throws IOException
	{
		//creates a file reader and a buffer reader
		FileReader reader = new FileReader(path);
		BufferedReader textReader = new BufferedReader(reader);
		
		//initializes the variables that is going to be used in the while loop 
		int numberOfLines = 0;
		
		//keeps counting as long as the string is not a null
		while(textReader.readLine() != null)
			numberOfLines ++;
		
		//closes and flushes part of memory		
		textReader.close();
		
		//returns the number of lines in the text file
		return numberOfLines;
		
	}
	
	//opens the file and reads the text and inputs into an array
	public String[] OpenFile() throws IOException 
	{
		//creates a file reader and a buffer reader
		FileReader reader = new FileReader(path);
		BufferedReader textReader = new BufferedReader(reader);
		
		//calls readLines to see the amount of lines in the text file
		int numberOfLines = readLines( );
		
		//creates the array with inputted number of items
		String[] textData = new String[numberOfLines];
				
		//simple loop to read and input the string into the array
		for(int i=0; i < numberOfLines; i++)
			textData[i] = textReader.readLine();
		
		//closes and flushes part of memory
		textReader.close( );
		
		//returns the array
		return textData;
	}
	
	/*
	To test the code
	
	public static void main(String[] args) throws IOException
	{
		String file_name = "C:/Users/Kareem/Google Drive/Uni/CS 1/Projects/RSA/test.txt";
		
		try
		{
			ReadFile file = new ReadFile(file_name);
			String[] arrayLines = file.OpenFile();
			
			int i;
			for ( i=0; i < arrayLines.length; i++ ) 
			{
				System.out.println( arrayLines[ i ] ) ;
			}
			
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	*/
}