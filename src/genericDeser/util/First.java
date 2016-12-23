package genericDeser.util;

public class First {
    
    //intance variables
    int IntValue = 0;
    float FloatValue = 0;
    short ShortValue = 0;
    String StringValue = "";
    
    //empty constructor
    public First()
    {
        //Message for the Logger
        Logger.writeOutput("First constructor called ...", 2);
    }
    
    //*** ACESSOR METHODS ***//
    public void setIntValue(int x)
    {
        IntValue = x;
    }
    public void setFloatValue(float x)
    {
        FloatValue = x;
    }
    public void setShortValue(short x)
    {
        ShortValue = x;
    }
    public void setStringValue(String x)
    {
        StringValue = x;
    }
    
    //*** MUTATOR METHODS ***//
    public int getIntValue()
    {
        return IntValue;
    }
    public float getFloatValue()
    {
        return FloatValue;
    }
    public short getShortValue()
    {
        return ShortValue;
    }
    public String getStringValue()
    {
        return StringValue;
    }
    
    //*** OVERRIDDEN METHODS ***//
    @Override
    public boolean equals(Object obj)
    {
        First input = (First)obj;
        if(input.getIntValue() == getIntValue() &&
           input.getFloatValue() == getFloatValue() &&
           input.getShortValue() == getShortValue() &&
           input.getStringValue().equals(getStringValue()))
        {
            return true;
        }
        else 
            return false;
    }
    @Override
    public int hashCode()
    {
        return (int) (31*IntValue+FloatValue+ShortValue+StringValue.hashCode());
    }
}
