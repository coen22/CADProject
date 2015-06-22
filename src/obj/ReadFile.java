package obj;

import java.io.*;

/**
 * A generic file reader
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class ReadFile {

    private String path;

    /**
     * Construct the object based on the file path
     *
     * @param file_path The file path
     */
    public ReadFile(String file_path) {
        path = file_path;
    }

    /**
     * Gets the number of lines in the file
     *
     * @return Gets the line number
     * @throws IOException
     */
    private int readLines() throws IOException {
        //creates a file reader and a buffer reader
        FileReader reader = new FileReader(path);
        BufferedReader textReader = new BufferedReader(reader);

        //initializes the variables that is going to be used in the while loop 
        int numberOfLines = 0;

        //keeps counting as long as the string is not a null
        while (textReader.readLine() != null) {
            numberOfLines++;
        }

        //closes and flushes part of memory		
        textReader.close();

        //returns the number of lines in the text file
        return numberOfLines;

    }

    /**
     * Reads the file and stores the content as an array
     *
     * @return The reads the file and stores as an array with each line being a single row of the
     * array
     * @throws IOException
     */
    public String[] OpenFile() throws IOException {
        //creates a file reader and a buffer reader
        FileReader reader = new FileReader(path);
        BufferedReader textReader = new BufferedReader(reader);

        //calls readLines to see the amount of lines in the text file
        int numberOfLines = readLines();

        //creates the array with inputted number of items
        String[] textData = new String[numberOfLines];

        //simple loop to read and input the string into the array
        for (int i = 0; i < numberOfLines; i++) {
            textData[i] = textReader.readLine();
        }

        //closes and flushes part of memory
        textReader.close();

        //returns the array
        return textData;
    }
}
