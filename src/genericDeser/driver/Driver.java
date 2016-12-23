package genericDeser.driver;

import genericDeser.util.Logger;
import genericDeser.util.PopulateObjects;

public class Driver {
    public static void main(String[] args)
    {
        String fileName = args[0];
        int DEBUG_VALUE = Integer.parseInt(args[1]);
        
        //set the debug level
        //0 - Display total/unique # of objects
        //1 - Every time a unique object gets created
        //2 - Every time a constructor gets called
        Logger.setDebugValue(DEBUG_VALUE);
        
        //read the contents of the file and calculate
        //all the statistics
        PopulateObjects x = new PopulateObjects(fileName);
        
        //DISPLAY EVERYTHING TO THE STDOUT
        if(Logger.getDebugValue() == 0)
        {
            System.out.println("Number of unique First objects: "+x.getFirstUnique());
            System.out.println("Total Number of First objects: "+x.getAllFirst());
            System.out.println("Number of unique Second objects: "+x.getUniqueSecond());
            System.out.println("Total Number of Second objects: "+x.getAllSecond());
        }

    }
}
