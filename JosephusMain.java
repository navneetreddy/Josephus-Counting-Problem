///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Josephus
// Files:            JosephusMain.java, CircularLinkedList.java, 
//					 CircularListADT.java, ListNode.java
// Semester:         CS367 Fall 2013
//
// Author:           Navneet Reddy
// CS Login:         navneet
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Jason Tiedt
// CS Login:         jtiedt
// Lecturer's Name:  Jim Skrentny
// Lab Section:      Lecture 1
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          N/A
//////////////////////////// 80 columns wide //////////////////////////////////

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Main class that simulates the Josephus counting problem 
 * using a circular doubly-linked chain of nodes.
 * 
 * Bugs: none known
 * 
 * @author Navneet Reddy
 * @author Jason Tiedt
 *
 */
public class JosephusMain {

	/**
	 * Main method adds people into the circle from a text file with a list of 
	 * names and simulates the Josephus counting problem through commands given
	 * by the user.
	 * 
	 * @param args the name of the file containing the initial 
	 * list of people in the circle.
	 */
    @SuppressWarnings("null")
	public static final void main(String[] args) 
    {
        //Checks whether exactly one command-line argument is given.
    	if (args.length != 1){
			System.out.println("Usage: java JosephusMain FileName");
			System.exit(-1);
		}
    	
    	//Name of the file given in the command line arguments.
    	String filename = args[0];
    	
    	//Creates a Circular Linked List object to store the names of the people.
    	CircularLinkedList<String> list = new CircularLinkedList<String>();
    	
    	try
    	{
    		//File from the command line arguments to read.
			File file = new File(filename);
			
			//Scanner to read the contents of the file
			Scanner scnr = new Scanner(file);
			
			//Checks if the file exists and is readable
			if (!file.isFile() || !file.canRead())
			{
				System.out.println("Error: Cannot access input file");
				System.exit(-1);
			}
			
			//Stores the next line in the scanner.
			String x;
			
			//Loads the data from the input file
			while(scnr.hasNextLine())
			{
				x = scnr.nextLine();
				list.add(x);
				list.setCurrentPosition(1);
			}
    	}
    	catch (FileNotFoundException e)
    	{
    		System.out.println("Error: Cannot access input file");
			System.exit(-1);
    	}
        
    	//Scanner to read user input.
        Scanner scan = new Scanner(System.in);

        //Main program loop.
        while (true) {
        	//Stores the user input.
            String line = scan.nextLine();
            if (line == null || line.length() == 0)
                continue;

            //Parses the user input and stores it in a array.
            String[] command = line.split(" ");

            if (command.length == 0)
                continue;

            if (command[0].equalsIgnoreCase("a")) {
            	
            	//Adds a new person to the circle.
            	list.add(command[1]);
            
            } else if (command[0].equalsIgnoreCase("p")) {
            	
            	//Prints the names if the people in the circle.
            	System.out.print(list.print(0));
            
            } else if (command[0].equalsIgnoreCase("r")) {
            	
            	//Stores the user inputed direction.
            	String direction = command[2];
            	//Stores the user inputed step size.
            	int stepSize = Integer.parseInt(command[1]);
            	//Stores the user inputed number of cycles.
            	int cycles = Integer.parseInt(command[3]);
            	
            	//Counter to track if the simulation has run through a cycle.
            	int circleTracker = 0;
            	//Counter to track the number of cycles the simulation has run.
            	int cycleCounter = 1;
            	//Temporary variable to store the list size.
            	int originalListSize = 0;
            	
            	//Error checks for user inputs.
            	if (stepSize <= 0) {
            		System.out.println("The step size is too small. " +
            				"It must be greater than 0.");
            	}
            	else if (!direction.equals("f") && !direction.equals("forward")
            		&& !direction.equals("b") && !direction.equals("backward")) {
            		System.out.println("The direction is invalid. " +
            				"It must be 'f', 'forward', 'b', or 'backward'.");
            	}
            	else if (cycles < 0) {
            		System.out.println("The number of cycles are too low. " +
            				"It can't be negative.");
            	}
            	else
            	{
            		if (cycles == 0)
            		{
            			//Run through cycles in the forward direction 
            			//until the list is empty.
            			if (direction.equals("f") || direction.equals("forward"))
            			{
            				while (!list.isEmpty())
            				{
            					System.out.println("Cycle Number " + 
            									cycleCounter + " Has Begun");
            					originalListSize = list.size();
            					
            					//Run through each cycle and eliminate the k-th
            					//person in the list.
            					while (circleTracker < originalListSize)
            					{
            						eliminationFor(stepSize,list);
            						circleTracker += stepSize;
            					}
            					
            					System.out.println("Cycle Number " + 
            									cycleCounter + " Has Ended");
            					cycleCounter++;
            					circleTracker = 0;
            				}
            			}
            			//Run through cycles in the backward direction 
            			//until the list is empty.
            			else
            			{
            				while (!list.isEmpty())
            				{
            					System.out.println("Cycle Number " + 
            									cycleCounter + " Has Begun");
            					originalListSize = list.size();
            					
            					//Run through each cycle and eliminate the k-th
            					//person in the list.
            					while (circleTracker < originalListSize)
            					{
            						eliminationBack(stepSize,list);
            						circleTracker += stepSize;
            					}
            					
            					System.out.println("Cycle Number " + 
            									cycleCounter + " Has Ended");
            					cycleCounter++;
            					circleTracker = 0;
            				}
            			}
            		}
            		else
            		{
            			//Run through the number of cycles specified by the user.
            			for (int i = 0; i < cycles; i++)
            			{
            				System.out.println("Cycle Number " + (i + 1) + 
            							" Has Begun");
            				
            				if (direction.equals("f") || 
            						direction.equals("forward"))
            				{
            					originalListSize = list.size();
            					
            					//Run through each cycle and eliminate the k-th
            					//person in the list.
            					while (circleTracker < originalListSize)
            					{
            						eliminationFor(stepSize,list);
            						circleTracker += stepSize;
            					}
            				}
            				else 
            				{
            					originalListSize = list.size();
            					
            					//Run through each cycle and eliminate the k-th
            					//person in the list.
            					while (circleTracker < originalListSize)
            					{
            						eliminationBack(stepSize,list);
            						circleTracker += stepSize;
            					}
            				}
            				
            				circleTracker = 0;
            				System.out.println("Cycle Number " + (i + 1) + 
            							" Has Ended");
            				
            				//Breaks the loop if everyone in the list
            				//is eliminated.
            				if (list.isEmpty())
            					break;
            			}
            		}

            		//Print how many people remain alive after each simulation.
            		if (list.size() == 1)
            			System.out.println("1 person remains alive");
            		else 
            			System.out.println(list.size() + 
            					" people still remain alive");
            	}
            	
            } else if (command[0].equalsIgnoreCase("x")) {
                System.out.println("exit");
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
    }
    
    /**
     * Eliminates a person from the circle in the forward direction.
     * 
     * @param stepSize step size at which to eliminate the person.
     * @param list list to eliminate the person from.
     */
    private static void eliminationFor(int stepSize, 
    										CircularLinkedList<String> list) {
    	//Stores the person eliminating someone else.
    	String eliminator = "";
    	//Stores the person being eliminated.
    	String eliminated = "";
    	
    	//Finds and prints who eliminated who and 
    	//removes the person being eliminated from the circle.
    	try {
    		eliminator = list.get(stepSize - 2);
			list.setCurrentPosition(stepSize - 1);
			eliminated = list.remove();
			
			System.out.println(eliminated + " has been eliminated by " + 
						eliminator);

		} catch (ElementNotFoundException e) {
			System.out.println("ElementNotFoundException");
		}
    }
    
    /**
     * Eliminates a person from the circle in the backward direction.
     * 
     * @param stepSize step size at which to eliminate the person.
     * @param list list to eliminate the person from.
     */
    private static void eliminationBack(int stepSize, 
    										CircularLinkedList<String> list) {
    	//Stores the person eliminating someone else.
    	String eliminator = "";
    	//Stores the person being eliminated.
    	String eliminated = "";
    	
    	//Finds and prints who eliminated who and 
    	//removes the person being eliminated from the circle.
    	try {
    		eliminator = list.get( - stepSize + 2);
    		list.setCurrentPosition( - stepSize + 1);
    		eliminated = list.remove();
    		list.setCurrentPosition(-1);

    		System.out.println(eliminated + " has been eliminated by " + 
    					eliminator);

    	} catch (ElementNotFoundException e) {
			System.out.println("ElementNotFoundException");
		}
    }
}
