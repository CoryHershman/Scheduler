/*
 Programmer:Cory Hershman
 Class: Operating Systems
 Instructor: Mr. Kennedy
 Assignment #: P0001
 Due Date: 11/10/2017
 Last Update: 10/28/2017
 Related Files: SchedulerSimulation.java, Process.java, input.txt
 Description: The constructor of this class creates a scheduler to keep track of, and modify a list of processes.
 The list of processes in the scheduler must be entered or ordered in order of arrival time, as the methods run in 
 psuedo-real time, with the processes entering the scheduler in the order that they come in. The methods of this 
 class include: get, size, FCFS, SJF, RR, and toString.
 */

package edu.frostburg.cosc460.Hershman;

import java.util.ArrayList;

public class Scheduler extends ArrayList<Process> {
	//This ArrayList of processes is the scheduler for all intents and purposes within this class
	ArrayList<Process> scheduler = new ArrayList<Process>(); 	//This class will increase the utility of this ArrayList of processes

	Process curProcess;
	
	//Empty constructor. The scheduler is empty
	public Scheduler() {
		this.scheduler = null;
	}
	
	//Constructor for a scheduler
	//Requires: An array list of processes
	//Scheduler object starts the same as the input array list
	public Scheduler(ArrayList<Process> scheduler) {
		this.scheduler = scheduler;
	}
	
	//get method
	//Requires: index of requested process
	//Returns: Requested process
	public Process get(int index) {
		return scheduler.get(index);
	}
	
	//size method
	//Requires: Nothing
	//Returns: The number of elements in the Scheduler
	public int size() {
		return scheduler.size();
	}
	
	//add method
	//Requires: Process to be added
	//Returns: true if the Scheduler changed
	public boolean add(Process p) {
		return scheduler.add(p);
	}
	
	//remove method
	//Requires: Index of the process to be removed
	//Returns: The removed process
	public Process remove(int index) {
		return scheduler.remove(index);
	}
	
	//First Come First Serve
	//FCFS method
	//Requires: Nothing
	//Returns: Array list of strings with the schedule the processes will run
	//Example: [A, A, A, -, B, B] means process A runs for 3 time units, no process runs for 1 time unit, process B runs for 2 time units
	//Processes are executed in the order they arrive
	public ArrayList<String> FCFS() {
		ArrayList<String> schedule = new ArrayList<String>(); 					//This ArrayList will be used for printing gantt charts
		Process prevProcess;

		//Step through the processes, entering them into the schedule in order of arrival time
		for(int i = 0; i < scheduler.size(); i++) {
			curProcess = scheduler.get(i);               					//Current process to be entered into the schedule
			//First process is a special case
			if(i==0) {
				curProcess.setST(curProcess.getAT());      					//First process' starting time is equal to it's arrival time
				curProcess.setFT(curProcess.getST() + curProcess.getBT());  //First process' turnaround time is equal to it's starting time + burst time

				//Add a "-" to the schedule for each time unit before the first process has arrived
				for(int j = 0; j< curProcess.getST(); j++) {
					schedule.add("-");
				}
			}
			if(i > 0) {
				prevProcess = scheduler.get(i-1);     //Previous process that was last entered into schedule

				//If the arrival time of the next process is before the time that the previous process finished
				//The next process will be entered at the same time the last process ended
				if(curProcess.getAT() <= prevProcess.getFT())  {
					curProcess.setST(prevProcess.getFT());
				}
				//If the arrival time of the next process is after the time that the previous process finish
				//The next process will start at the time that it arrives at the scheduler
				else {
					curProcess.setST(curProcess.getAT());
				}
				//The next process' turnaround time is equal to it's starting time plus it's burst time
				curProcess.setFT(curProcess.getST() + curProcess.getBT());

				//add a "-" to the schedule for each time unit between processes running
				for(int j = prevProcess.getFT(); j < curProcess.getST(); j++) {
					schedule.add("-");
				}
			}
			//Add the process' name to the schedule for each time unit that the process runs
			for(int j = 0; j < curProcess.getBT(); j++){
				schedule.add(curProcess.getName());
			}
			curProcess.setTT(curProcess.getFT() - curProcess.getAT());	//Process' turnaround time is equal to it's finishing time - arrival time
			curProcess.setWT(curProcess.getTT() - curProcess.getBT());	//Process' waiting time is equal to it's turnaround time - burst time
		}

		return schedule;
	}

	//Shortest Job First
	//SJF method
	//Requires: Nothing
	//Returns: Array list of strings with the schedule the processes will run
	//Example: [A, A, A, -, B, B] means process A runs for 3 time units, no process runs for 1 time unit, process B runs for 2 time units
	//Shortest processes are executed first, pre-emptively checking if there are any newly arrived processes that are the shortest
	public ArrayList<String> SJF() {
		ArrayList<String> schedule = new ArrayList<String>(); 			//This ArrayList will be used for printing gantt charts
		Process nextProcess;											//nextProcess will be used to determine the shortest remaining time
		int time = 0;
		
		//Set the initial remaining time of all processes to the same as their burst time
		for(int j = 0; j < scheduler.size(); j++) {
			curProcess = scheduler.get(j);
			curProcess.setRT(curProcess.getBT());
		}
		//No processes running before the first process' arrival time
		curProcess = scheduler.get(0);
		for(int j = 0; j < curProcess.getAT(); j++) {
			schedule.add("-");
			time++;
		}
		
		nextProcess = scheduler.get(0);
		boolean finished = false;
		
		while(!finished) {
			//if the next process is finished processing, check if there are any other processes left to run
			if(nextProcess.getRT() == 0) {
				nextProcess.setFT(time);
				for(int j = 0; j < scheduler.size(); j++) {
					curProcess = scheduler.get(j);
					//if a process is found that still hasn't finished, continue processing
					if(curProcess.getRT() > 0) {
						nextProcess = curProcess;
						break;
					}
					//if all processes have RT == 0, the method is finished
					if(j == scheduler.size() - 1)
						finished = true;
				}
			}
			//If there are no processes to run currently, add "-" to represent that nothing is running
			while(nextProcess.getAT() > time) {
				time++;
				schedule.add("-");
			}
			
			//Look for the current process with the lowest remaining time
			for(int j = 0; j < scheduler.size(); j++) {
				curProcess = scheduler.get(j);
				//If the current process' arrival time is less than the time, and it's remaining time is less than the nextProcess, it becomes the nextProcess
				if(curProcess.getAT() <= time) {
					if(curProcess.getRT() < nextProcess.getRT() && curProcess.getRT() > 0)
						nextProcess = curProcess;
				}
				else {break;}	//If the current process' arrival time is greater than the time, stop checking as the rest of the processes are later
			}
			//Increment the time, and decrement the next process' remaining time
			if(!finished) {
				time++;
				nextProcess.setRT(nextProcess.getRT() - 1);
				schedule.add(nextProcess.getName());
			}
		}
		//Set the turnaround times and waiting times of all the processes
		for(int j = 0; j < scheduler.size(); j++) {
			curProcess = scheduler.get(j);
			curProcess.setTT(curProcess.getFT() - curProcess.getAT());	//Turnaround time = finishing time - arrival time
			curProcess.setWT(curProcess.getTT() - curProcess.getBT());	//Waiting time = turnaround time - burst time
		}
		
		return schedule;
	}

	//Round Robin
	//RR method
	//Requires: The quantum interval (an int)
	//Returns: Array list of strings with the schedule the processes will run
	//Example: [A, A, A, -, B, B] means process A runs for 3 time units, no process runs for 1 time unit, process B runs for 2 time units
	//Processes are executed in turns. First process runs for q time units, second process runs for q time units, etc.
	public ArrayList<String> RR(int q) {
		ArrayList<String> schedule = new ArrayList<String>(); 					//This ArrayList will be used for printing gantt charts
		int time = 0;
		int i = 0;

		//Set the initial remaining time of all process to the same as their burst time
		for(int j = 0; j < scheduler.size(); j++) {
			curProcess = scheduler.get(j);
			curProcess.setRT(curProcess.getBT());
		}

		curProcess = scheduler.get(i);
		//No process runs before the arrival time of the first process
		for(int j = 0; j < curProcess.getAT(); j++) {
			schedule.add("-");
			time++;
		}
		
		//Outer while loop runs until all the processes are finished
		boolean finished = false;
		while(!finished) {
			//Inner while loop runs until there are no more processes to run at the current time
			//Then waits for more processes if there are any
			boolean isWaiting = false;
			while(!isWaiting) {
				//Runs the current process quantum times, unless the process finishes
				for(int j = 0; j < q; j++) {
					//If the process is not finished yet, run it for 1 time unit. Time increments, remaining time decrements.
					if(curProcess.getRT() > 0) {
						curProcess.setRT(curProcess.getRT() - 1);
						schedule.add(curProcess.getName());
						time++;
						//If the process finishes, set its finishing time to the current time
						if(curProcess.getRT() == 0)
							curProcess.setFT(time);
					}
				}
				//Check for the next process with an arrival time before the current time and is not finished running
				for(int j = 0; j < scheduler.size(); j++) {
					i++;
					i = i % scheduler.size();
					curProcess = scheduler.get(i);
					//If a process is found that meets the condition, keep it as the current process to run
					if(curProcess.getAT() <= time && curProcess.getRT() > 0) {
						break;
					}
					//If all processes have either finished, or have not arrived yet, break out of the inner while loop
					if(j == scheduler.size() - 1)
						isWaiting = true;
				}
			}
			//Look for the next schedule to arrive that has not finished yet
			for(int j = 0; j < scheduler.size(); j++) {
				//If a schedule is found that is not finished, skip ahead to the start of that process, and break out of for loop to continue
				if(scheduler.get(j).getRT() > 0) {
					while(time < scheduler.get(j).getAT()) {
						schedule.add("-");
						time++;
					}
					break;
				}
				//If there are no processes left to run, break out of the outer while loop, the schedule is finished
				if(j == scheduler.size()-1) {
					finished = true;
				}
			}
		}
		//Set the turnaround times and waiting times of all the processes
		for(int j = 0; j < scheduler.size(); j++) {
			curProcess = scheduler.get(j);
			curProcess.setTT(curProcess.getFT() - curProcess.getAT());	//Turnaround time = finishing time - arrival time
			curProcess.setWT(curProcess.getTT() - curProcess.getBT());	//Waiting time = turnaround time - burst time
		}
		return schedule;
	}
	
	//toString method
	//Requires: Nothing
	//Returns: The scheduler as a string, showing the order of execution of the processes
	public String toString() {
		return scheduler.toString();
	}
}
