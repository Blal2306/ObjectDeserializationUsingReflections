package genericDeser.util;

import genericDeser.fileOperations.FileProcessor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.lang.reflect.*;

public class PopulateObjects 
{
    //type converstion
    HashMap<String, Class> types = new HashMap<String, Class>();
    
    //to store all instances of the class
    int allFirst = 0;
    int uniqueFirst = 0;
    int allSecond = 0;
    int uniqueSecond = 0;
    
    //to count unique instances
    //key = hashCode, value = count;
    Map<Integer, LinkedList<First>> firstUnique = null;
    Map<Integer, LinkedList<Second>> secondUnique = null;
    
    //File Processor
    FileProcessor fileProcessor;
    
    //constructor
    public PopulateObjects(String fileName)
    {
        //message for the Logger
        Logger.writeOutput("Populate Object constructor called ...", 2);
        
        //initialize everything
        initTypesMap();
        fileProcessor = new FileProcessor(fileName);
        firstUnique = new TreeMap<Integer, LinkedList<First>>();
        secondUnique = new TreeMap<Integer, LinkedList<Second>>();
        
        //De-serialize all the object from the file
        try {
            deserObjects();
        } catch (Exception ex) {
            System.out.println("Problem occured deserObjects, terminating ...");
            System.exit(0);
        }
        
    }
    public void initTypesMap()
    {
        
        types.put("int", Integer.TYPE);
        types.put("float", Float.TYPE);
        types.put("short", Short.TYPE);
        try {
            types.put("String", Class.forName("java.lang.String"));
        } catch (ClassNotFoundException ex) {
            System.out.println("java.lang.String now found ...");
            System.exit(0);
        }
        types.put("double", Double.TYPE);
        types.put("boolean", Boolean.TYPE);
    }
    //convert the given value to the correct type to be
    //used as the parameter when calling
    public Object[] getCastedValue(String type, String value)
    {
        Object[] out = new Object[1];
        if(type.equals("int"))
        {
            out[0] = Integer.parseInt(value);
        }
        else if(type.equals("float"))
        {
            out[0] = Float.parseFloat(value);
        }
        else if(type.equals("short"))
        {
            out[0] = Short.parseShort(value);
        }
        else if(type.equals("String"))
        {
            out[0] = value;
        }
        else if(type.equals("double"))
        {
            out[0] = Double.parseDouble(value);
        }
        else if(type.equals("boolean"))
        {
            boolean x = true;
            
            if(value.equals("true"))
            {
                x = true;
            }
            else if(value.equals("false"))
            {
                x = false;
            }
            
            out[0] = x;
        }
        return out;
    }
    public void print(First x)
    {
        System.out.println("FIRST : ");
        System.out.println("\tINT VALUE : "+x.getIntValue());
        System.out.println("\tFLOAT VALUE : "+x.getFloatValue());
        System.out.println("\tSHORT VALUE : "+x.getShortValue());
        System.out.println("\tSTRING VALUE : "+x.getStringValue()+"\n");
    }
    public void print(Second x)
    {
        System.out.println("SECOND : ");
        System.out.println("\tINT VALUE : "+x.getIntValue());
        System.out.println("\tDOUBLE VALUE : "+x.getDoubleValue());
        System.out.println("\tBOOLEAN VALUE : "+x.getBooleanValue()+"\n");
    }
    public void deserObjects() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        String line = null;
        LinkedList<String> fileContents = new LinkedList<String>();
        
        //store all the contents in the linked list
        while((line = fileProcessor.getLine()) != null)
        {
            fileContents.add(line); 
        }
        
        while(!fileContents.isEmpty())
        {
            String t1 = fileContents.removeFirst();
            
            //*** OBJECT OF THE FIRST TYPE ***//
            if(t1.equals("fqn:genericDeser.util.First"))
            {
                //get the name of the class
                String[] temp = t1.split(":");
                
                //create the class
                Class reflectClass = Class.forName(temp[1].trim());
                
                //create the object of that class
                First obj = (First) reflectClass.newInstance();
                
                while(!fileContents.isEmpty() && !(t1 = fileContents.removeFirst()).startsWith("fqn"))
                {
                    String[] values = t1.split(",");
                    
                    //type = int
                    String[] t2 = (values[0].trim()).split("=");
                    
                    //*** GET THE PARAMETER TYPE ***
                    Class[] argsType = new Class[1];
                    argsType[0] = types.get(t2[1]);
                    
                    //*** GET THE FUNCTION NAME ***
                    String[] t3 = (values[1].trim()).split("=");
                    Method method = reflectClass.getDeclaredMethod("set"+t3[1], argsType);
                    
                    //*** GET THE VALUE ***
                    String[] t4 = (values[2].trim()).split("=");
                    Object[] argsValue = getCastedValue(t2[1].trim(),t4[1].trim());
                    
                    //*** MAKE THE CALL ***//
                    method.invoke(obj, argsValue);
                }
                
                //++++ PROCESSING DONE HERE +++
                int key = obj.hashCode();
                
                //check if the object already exists
                //only need to update total count
                if(firstUnique.containsKey(key))
                {
                    //get the linked list of all the
                    //matching objects and check
                    //if the current object equals
                    //any of them
                    LinkedList<First> ll = firstUnique.get(key);
                    
                    boolean found = false;
                    for(First prev: ll)
                    {
                        //see if matching object
                        //exists
                        if(prev.equals(obj))
                        {
                            found = true;
                        }
                    }
                    if(found)
                    {
                        allFirst++;
                    }
                    //it is a unique object that happens to have
                    //the same has code as the other object
                    else
                    {
                        ll.add(obj);
                        uniqueFirst++;
                        allFirst++;
                    }
                }
                //it is an entirely new object with unique hashCode
                else
                {
                    //message for debugger
                    Logger.writeOutput("New First Object created ...", 1);
                    
                    LinkedList<First> newLL = new LinkedList<First>();
                    newLL.add(obj);
                    firstUnique.put(key, newLL);
                    allFirst++;
                    uniqueFirst++;
                }
                //+++++++++++++++++++++++++++++
                
                //insert the last instruction read into
                //the file Contents
                fileContents.addFirst(t1);
            }
            
            // **** OBJECT OF THE SECOND TYPE ***//
            else if(t1.equals("fqn:genericDeser.util.Second"))
            {
                //get the name of the class
                String[] temp = t1.split(":");
                
                //create the reflect class
                Class reflectClass = Class.forName(temp[1].trim());
                
                //create the object
                Second obj = (Second) reflectClass.newInstance();
                
                while(!fileContents.isEmpty() && !(t1 = fileContents.removeFirst()).startsWith("fqn"))
                {
                    String[] values = t1.split(",");
                    
                    //type = int
                    String[] t2 = (values[0].trim()).split("=");
                    
                    //GET THE PARAMETER TYPE
                    Class[] argsType = new Class[1];
                    argsType[0] = types.get(t2[1]);
                    
                    //GET THE FUNCTION NAME
                    String[] t3 = (values[1].trim()).split("=");
                    Method method = reflectClass.getDeclaredMethod("set"+t3[1], argsType);
                    
                    //GET THE VALUE
                    String[] t4 = (values[2].trim()).split("=");
                    Object[] argsValue = getCastedValue(t2[1].trim(), t4[1].trim());
                    
                    //MAKE THE CALL
                    method.invoke(obj, argsValue);
                }
                
                //++++ PROCESSING DONE HERE +++
                int key = obj.hashCode();
                
                //check if the object already exists
                //only need to update total count
                if(secondUnique.containsKey(key))
                {
                    //get the linked list of all the
                    //matching objects and check
                    //if the current object equals
                    //any of them
                    LinkedList<Second> ll = secondUnique.get(key);
                    
                    boolean found = false;
                    for(Second prev: ll)
                    {
                        //see if matching object
                        //exists
                        if(prev.equals(obj))
                        {
                            found = true;
                        }
                    }
                    //it is not a unique object
                    if(found)
                    {
                        allSecond++;
                    }
                    //it is a unique object that happens to have
                    //the same has code as the other object
                    else
                    {
                        ll.add(obj);
                        uniqueSecond++;
                        allSecond++;
                    }
                }
                //it is an entirely new object with unique hashCode
                else
                {
                    //message for debugger
                    Logger.writeOutput("New First Object created ...", 1);
                    
                    LinkedList<Second> newLL = new LinkedList<Second>();
                    newLL.add(obj);
                    secondUnique.put(key, newLL);
                    allSecond++;
                    uniqueSecond++;
                }
                //+++++++++++++++++++++++++++++
                
                //insert the last instruction read into
                //the file Contents
                fileContents.addFirst(t1);
            }
        }
    }
    public int getFirstUnique()
    {
        return uniqueFirst;
    }
    public int getAllFirst()
    {
        return allFirst;
    }
    public int getUniqueSecond()
    {
        return uniqueSecond;
    }
    public int getAllSecond()
    {
        return allSecond;
    }
    
}
