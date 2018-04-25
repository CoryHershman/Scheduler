Scheduler program
Last Updated: 11/7/2017
---------------
Source code:
	Process.java
	Scheduler.java
	SchedulerSimulation.java
---------------
"SchedulerSimulation.java" is the main class. This project creates a schedule by using scheduling 
algorithms on a list of processes. The list of processes is brought in from a text file. Each process
in the list becomes a process object, and those processes are assembled into a Scheduler. The scheduler
then applies a scheduling algorithm; either first come first serve, shortest job first (pre-emptive), or
round robin. The resulting schedule is then sent to the SchedulerSimulation which creates a table and a
gantt chart with the information.
---------------
Package Path:Scheduler\src\edu\frostburg\cosc460\Hershman
---------------
Test case files:
	input1.txt
	input2.txt
	input3.txt
	input4.txt
	input5.txt
	input6.txt
	input7.txt

"input1.txt" is the default test case. To test the other cases, go to line 33 of "SchedulerSimulation.java"
and change the input file. "input8.txt" is an empty text file so that any test case can be entered in that file
and tested. Processes must be entered in order of arrival time, or else the project will not work right.
---------------
Author: Cory Hershman