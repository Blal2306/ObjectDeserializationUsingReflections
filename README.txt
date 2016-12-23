CS542 Design Patterns
Fall 2016
PROJECT 5 README FILE

PURPOSE:

	To de-serialize object from file using java reflections. And calculate
	the total number of objects as well as total number of unique objects.
	
PERCENT COMPLETE:

	100 %

To CLEAN:
	
	cd genericDeser
	ant clean

TO COMPILE: 

	cd genericDeser
	ant all

TO RUN: 
	
	cd genericDeser

	ant run -Darg0=inputFileName.txt -Darg1=DEBUG_VALUE
	
		-inputFileName = contains serialized data to be read
		-DEBUG_VALUE = 0 : will display total number of objects for First
						   and Second as well as total number of unique
						   objects of First and Second
		-DEBUG_VALUE = 1 : everytime a new First or Second object gets
						   created.
		-DEBUG_VALUE = 2 : everytime a constructor is called

TO MAKE A TARBALL:

	cd genericDeser
	ant tarzip

CHOICE OF DATA STRUCTURE:
	
	The data structure used is TreeMap, because it gives O(log(n))
	performance when looking up as well as insertion in the worst
	case. The keys are the hashCode calculated over the object, thus,
	it makes it easy to detect duplicate objects.
	
TO MAKE A TARBALL:

	ant -buildfile src/build.xml tarzip
	
TO UNTAR:
	
	tar -xvf blal_zafar_assign5.tar.gz 


