/*
 Programmer:Cory Hershman
 Class: Operating Systems
 Instructor: Mr. Kennedy
 Assignment #: P0001
 Due Date: 11/10/2017
 Last Update: 10/28/2017
 Related Files: SchedulerSimulation.java, Scheduler.java, input.txt
 Description: The constructor of this class creates a Process to run and be scheduled together.
 The methods of this class include: getName, getAT, getBT, getWT, getTT, getRT, getST, get FT, 
 setName, setAT, setBT, setWT, setTT, setRT, setST, and set FT
 */

package edu.frostburg.cosc460.Hershman;

public class Process {
	
	//private variables
	//variables must be accessed or changed with get and set methods
	private String name;
	private int aT;	 //Arrival Time
	private int bT;  //Burst Time
	private int wT;  //Wait Time
	private int tT;  //TurnaroundTime
	private int rT;  //Remaining Time
	private int sT;  //Starting Time
	private int fT;  //Finishing Time
	
	//Empty constructor. Nothing is initialized
	public Process() {
		
	}
	
	//Constructor for a process
	//Requires: name of the process, arrival time, burst time
	//All other variables must initialized by user using set methods
	public Process(String name, int aT, int bT) {
		this.name = name;
		this.aT = aT;
		this.bT = bT;
	}
	
	//getName method
	//Requires: Nothing
	//Returns: name of the process
	public String getName() {
		return name;
	}
	
	//getAT method
	//Requires: Nothing
	//Returns: arrival time of the process
	public int getAT() {
		return aT;
	}
	
	//getBT method
	//Requires: Nothing
	//Returns: burst time of the process
	public int getBT() {
		return bT;
	}
	
	//getWT method
	//Requires: Nothing
	//Returns: wait time of the process
	public int getWT() {
		return wT;
	}
	
	//getTT method
	//Requires: Nothing
	//Returns: turnaround time of the process
	public int getTT() {
		return tT;
	}
	
	//getRT method
	//Requires: Nothing
	//Returns: remaining time of the process
	public int getRT() {
		return rT;
	}
	
	//getST method
	//Requires: Nothing
	//Returns: starting time of the process
	public int getST() {
		return sT;
	}
	
	//getFT method
	//Requires: Nothing
	//Returns: finishing time of the process
	public int getFT() {
		return fT;
	}
	
	//setName method
	//Requires: New name for the process
	//Returns: Nothing
	//Overwrites the old name of the process with a new name
	public void setName(String name) {
		this.name = name;
	}
	
	//setAT method
	//Requires: New arrival time for the process
	//Returns: Nothing
	//Overwrites the old arrival time of the process with a new arrival time (Normally should only be called once)
	public void setAT(int aT) {
		this.aT = aT;
	}
	
	//setBT method
	//Requires: New burst time for the process
	//Returns: Nothing
	//Overwrites the old burst time of the process with a new burst time (Normally should only be called once)
	public void setBT(int bT) {
		this.bT = bT;
	}
	
	//setWT method
	//Requires: New waiting time for the process
	//Returns: Nothing
	//Overwrites the old waiting time of the process with a new waiting time
	public void setWT(int wT) {
		this.wT = wT;
	}
	
	//setTT method
	//Requires: New turnaround time for the process
	//Returns: Nothing
	//Overwrites the old turnaround time of the process with a new turnaround time
	public void setTT(int tT) {
		this.tT = tT;
	}
	
	//setRT method
	//Requires: New remaining time for the process
	//Returns: Nothing
	//Overwrites the old remaining time of the process with a new remaining time
	public void setRT(int rT) {
		this.rT = rT;
	}
	
	//setST method
	//Requires: New starting time for the process
	//Returns: Nothing
	//Overwrites the old starting time of the process with a new starting time
	public void setST(int sT) {
		this.sT = sT;
	}
	
	//setFT method
	//Requires: New finishing time for the process
	//Returns: Nothing
	//Overwrites the old finishing time of the process with a new finishing time
	public void setFT(int fT) {
		this.fT = fT;
	}
	
	//toString method
	//Requires: Nothing
	//Returns: Name of the process
	//When a process is printed to output, the name of the process is the output
	public String toString() {
		return getName();
	}
}