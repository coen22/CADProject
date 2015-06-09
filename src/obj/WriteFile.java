package obj;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class WriteFile 
{
	//creation of the gobal variable
	private String path;
	private boolean append = false;
	
	//creates the file path when creating the object and see if the user wants to amend or over write the file
	public WriteFile(String file_path, boolean append_value)
	{
		path = file_path;
		append = append_value;
	}
	
	//gives the file path when creating the object
	public WriteFile(String file_path)
	{
		path = file_path;
	}
	
	public void writeToFile(String textLine ) throws IOException 
	{
		FileWriter writer = new FileWriter( path , append);
		
		//This PrintWriter ensures that it can write something other then bytes
		PrintWriter print_line = new PrintWriter(writer);
		
		//handles the formatting of the text
		print_line.printf("%s" + "%n", textLine);
		print_line.close();
	}

    public void setAppend(boolean append) {
        this.append = append;
    }
        
}