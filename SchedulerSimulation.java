/*
 Programmer:Cory Hershman
 Class: Operating Systems
 Instructor: Mr. Kennedy
 Assignment #: P0001
 Due Date: 11/10/2017
 Last Update: 10/28/2017
 Related Files: Scheduler.java, Process.java, input.txt
 Description: This class's main method brings in a list of processes from an input file and creates schedules based on
 the algorithms of: First Come First Serve, Round Robin, and Shortest Job First (Pre-emptive). The list of processes in the input
 must be ordered in order of arrival time, as the algorithms run in psuedo-real time, with the processes entering the scheduler in 
 the order that they come in. The static methods of this class include: toGanttChart, and createTable.
 */

package edu.frostburg.cosc460.Hershman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class SchedulerSimulation {
	
	public static void main(String[] args) {

		ArrayList<Process> processList = new ArrayList<Process>();	//processList will hold all the processes from the input file
		ArrayList<String> schedule = new ArrayList<String>();		//schedule will hold the order of execution of processes as defined by a scheduling algorithm
		

		try {
			//Input file needs to have a table of processes. Lines that begin with # are skipped.
			//The processes need to be sorted in order of Arrival Time
			Scanner scan = new Scanner(new File("input1.txt"));

			//While loop fills in the processList with processes from the input table
			//Lines that start with # are ignored
			while(scan.hasNextLine()) {
				String name = scan.next();
				if(name.startsWith("#")) { 	//If the line starts with #, ignore the line
					scan.nextLine();
					continue;
				}
				//Create a new process and enter the first 3 inputs of the line as it's name, arrival time, and burst time
				Process proc = new Process();
				proc.setName(name);
				proc.setAT(scan.nextInt());
				proc.setBT(scan.nextInt());
				processList.add(proc);		//Add the process to the processList
			}
			scan.close();

			Scheduler scheduler = new Scheduler(processList);	//Scheduler object is created from the processList
			//Use Round Robin Algorithm
			schedule = scheduler.RR(2);
			System.out.println("\nRound Robin\n");
			
			//Print out table of processes
			createTable(scheduler);
			toGanttChart(schedule);
			System.out.println();
			
			//Use First Come First Serve Algorithm
			schedule = scheduler.FCFS();
			System.out.println("\nFirst Come First Serve\n");
			
			//Print out table of processes
			createTable(scheduler);
			toGanttChart(schedule);
			System.out.println();
			
			//Use Shortest Job First Algorithm
			schedule = scheduler.SJF();
			System.out.println("\nShortest Job First (Pre-emptive)\n");
			
			//Print out table of processes
			createTable(scheduler);
			toGanttChart(schedule);
			System.out.println();

		} catch(FileNotFoundException e) {
			System.out.println("File was not found");
			e.printStackTrace();
		}
	}
	
	//createTable method
	//Requires: Scheduler object
	//Returns: Printout of a table of the processes' attributes
	public static void createTable(Scheduler scheduler) {
		Process curProcess;
		System.out.println("ID AT BT WT TT");
		for(int i = 0; i < scheduler.size(); i++) {
			curProcess = scheduler.get(i);
			System.out.printf("%2s%3s%3s%3s%3s%n", curProcess.getName() , curProcess.getAT() , curProcess.getBT() , curProcess.getWT() , curProcess.getTT());

		}
	}
	
	//toGanttChart method
	//Requires: Schedule of processes' runtime
	//Returns: Printout of a gantt chart of the input schedule
	public static void toGanttChart(ArrayList<String> schedule) {
		//String s = schedule.get(0);
		//System.out.printf("%s", s);

		int runtime = 0;	//count keeps track of the runtime of a process
		String s;			//s keeps track of the current process
		
		//This section creates the top border of the gantt chart
		String topBorder = "__";
		String topDivider = "..";
		//step through the schedule
		for(int i = 0; i < schedule.size(); i++) {
			s = schedule.get(i);
			runtime++;
			
			//If it's the end of the schedule, print the end of the border and finish
			if(i == schedule.size() - 1) {
				System.out.print(topDivider);
				for(int j = 0; j < runtime ; j++) {
					System.out.print(topBorder);
				}
				
				runtime = 0;
			}
			//if the next scheduled process is different than the current one, the runtime has been found
			//print the top border accordingly
			else if(s != schedule.get(i+1)) {
				System.out.print(topDivider);
				for(int j = 0; j < runtime ; j++) {
					System.out.print(topBorder);
				}
				
				runtime = 0;
			}
		}
		System.out.print(topDivider+ "\n");
		
		//next section creates the middle of the gantt chart
		
		String noRunning = "//";
		String running = "  ";
		
		for(int i = 0; i < schedule.size(); i++) {
			s = schedule.get(i);
			runtime++;
			
			//If it's the end of the schedule, print the end of the border and finish
			if(i == schedule.size() - 1) {
				System.out.printf("%2s", s);
				for(int j = 0; j < runtime ; j++) {
					if( s == "-") {
						System.out.print(noRunning);
					}
					else {
						System.out.print(running);
					}
				}
				
				runtime = 0;
			}
			//if the next scheduled process is different than the current one, the runtime has been found
			//print the top border accordingly
			else if(s != schedule.get(i+1)) {
				System.out.printf("%2s", s);
				for(int j = 0; j < runtime ; j++) {
					if( s == "-") {
						System.out.print(noRunning);
					}
					else {
						System.out.print(running);
					}
				}
				
				runtime = 0;
			}
		}
		System.out.printf("%2s%n", "|");
		
		System.out.printf("%2s", 0);
		
		//Next section creates the bottom border
		String bottomBorder = "--";
		for(int i = 0; i < schedule.size(); i++) {
			s = schedule.get(i);
			runtime++;
			
			//If it's the end of the schedule, print the end of the border and finish
			if(i == schedule.size() - 1) {
				
				for(int j = 0; j < runtime ; j++) {
					System.out.print(bottomBorder);
				}
				System.out.printf("%2s", i+1);
				runtime = 0;
			}
			//if the next scheduled process is different than the current one, the runtime has been found
			//print the top border accordingly
			else if(s != schedule.get(i+1)) {
				
				for(int j = 0; j < runtime ; j++) {
					System.out.print(bottomBorder);
				}
				System.out.printf("%2s", i+1);
				runtime = 0;
			}
		}
		
	}
	
}
