package genericDeser.util;

public class Logger {
    
    private static int debugLevel;
    
    public static void setDebugValue(int levelIn)
    {
        debugLevel = levelIn;
    }
    /**
     * @return the current debug value set
     */
    public static int getDebugValue()
    {
        return debugLevel;
    }
    public static void writeOutput(String message, int levelIn)
    {
        if(levelIn == debugLevel)
        {
            System.out.println(message);
        }
    }
    /**
     * @return String representation of the current debug value
     */
    public String toString()
    {
        return "Debug Level is " +debugLevel;
    }
}
