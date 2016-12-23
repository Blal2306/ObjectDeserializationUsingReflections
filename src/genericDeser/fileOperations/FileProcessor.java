package genericDeser.fileOperations;

import genericDeser.util.Logger;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor
{
    FileReader fileReader;
    BufferedReader bufferedReader;
    
    //Argument: File Name
    public FileProcessor(String fileName)
    { 
        //message for the logger
        Logger.writeOutput("File Processor constructor called ...", 2);
        
        try
        {
            fileReader= new FileReader(fileName);
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open file '" + fileName + "'"); 
        }
        bufferedReader = new BufferedReader(fileReader);
    }
    /**
     * @return returns a line of text read from the input file
     */
    public synchronized String getLine()
    {
        String out = null;
        try
        {
            out = bufferedReader.readLine();
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file..."); 
        }
        return out;
    }
}

