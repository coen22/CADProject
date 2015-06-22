package obj;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Generic file writer
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class WriteFile {

    //creation of the gobal variable
    private String path;
    private boolean append = false;

    /**
     * Creates the file path when creating the object and see if the user wants to amend or over
     * write the file
     *
     * @param file_path The file path
     * @param append_value If it wants amend or not. True: amend; False: dont amend
     */
    public WriteFile(String file_path, boolean append_value) {
        path = file_path;
        append = append_value;
    }

    //gives the file path when creating the object
    /**
     * Creates a file writer and assumes you dont want to amend the file
     *
     * @param file_path The file path
     */
    public WriteFile(String file_path) {
        path = file_path;
    }

    /**
     * Function to write file
     *
     * @param textLine What to write in the file
     * @throws IOException
     */
    public void writeToFile(String textLine) throws IOException {
        FileWriter writer = new FileWriter(path, append);

        //This PrintWriter ensures that it can write something other then bytes
        PrintWriter print_line = new PrintWriter(writer);

        //handles the formatting of the text
        print_line.printf("%s" + "%n", textLine);
        print_line.close();
    }

    /**
     * Set if you want to amend or not
     *
     * @param append True = amend
     */
    public void setAppend(boolean append) {
        this.append = append;
    }

}
