package genericDeser.util;

public class Second {
    
    //instance variables
    int IntValue = 0;
    double DoubleValue = 0.0;
    boolean BooleanValue = true;
    
    //empty constructor
    public Second()
    {
        //Message for the Logger
        Logger.writeOutput("Second constructor called ...", 2);
    }
    
    //*** ACESSOR METHODS ***//
    public void setIntValue(int x)
    {
        IntValue = x;
    }
    public void setDoubleValue(double x)
    {
        DoubleValue = x;
    }
    public void setBooleanValue(boolean x)
    {
        BooleanValue = x;
    }
    
    //*** MUTATOR METHODS ***//
    public int getIntValue()
    {
        return IntValue;
    }
    public double getDoubleValue()
    {
        return DoubleValue;
    }
    public boolean getBooleanValue()
    {
        return BooleanValue;
    }
    
    //*** OVERRIDDEN METHODS ***//
    @Override
    public boolean equals(Object obj)
    {
        Second input = (Second)obj;
        if(input.getIntValue() == getIntValue() &&
           input.getDoubleValue() == getDoubleValue() &&
           input.getBooleanValue() == getBooleanValue())
        {
            return true;
        }
        else 
            return false;
    }
    @Override
    public int hashCode()
    {
        return (int) (31*IntValue + DoubleValue + (BooleanValue ? 1 : 0));
    }
    
}
