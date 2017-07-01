Shanan Almario
CSCE 415
Assignment 2

IDE: Eclipse Neon

Classes:
	classifier.java
	readFile.java

Inputs:
	No inputs. The file locations are already hardcoded into 
	the program.Before running, please be sure to change file 
	locations.
	
Outputs:
	Prints data on console
	Iteration #
	Training %  Classification %
	Precision
	Accuracy
	Recall
	FP Rate
	TP Rate

Class Descriptions:
	readFile.java	
		Checks to find file and then will take in the values. 
		Due to the nature of the file, it organizes the spam and non-spam in seperate arraylists
		which gets passed to the classifier class. 
		
	classifier.java
		Will take the spam and nonspam arraylists from the readFile.java and will partition them 
		as indicated in the main method.
		
		train() will calculate the probabilities for all 334 features using the training set.
		classify() will reference those probabilities for the classification set where all the 
		data will be calculated.
		
Program Notes:
- File locations will need to be changed to compile and run

Change File Locations:
Class:		readFile.java
Variable:	inFileName
