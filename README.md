# coen448_project

## Contributors
Samantha Famira, 40094949  
Thomas Le, 40096120  
Thomas Tran, 40095654  

## How to run the program
Open the coen448_project as a Gradle project in the IDE of your choosing (IntelliJ or Eclipse)
and run RobotMotion.java.

## Objective
The objective of the project is to test the practical use of the software testing techniques
covered in the course. The project will be performed over supervised lab sessions. There will be
deliverables (see the Project Milestones section), including a final demonstration of all the
artifacts developed during the project. The demo will be performed during the lab sessions

## Project description
Imagine a robot that walks around a room under the control of a Java program. The robot holds
a pen in one of two positions, up or down. While the pen is down, the robot traces out shapes as
it moves; while the pen is up, the robot moves about freely without writing anything. The room
will be represented by an N by N array called floor that is initialized to zeros. Initially the robot is
at position [0, 0] of the floor, its pen is up, and is facing north (as shown in the below figure).

The robot moves around the floor (i.e. the array) as it receives commands from the user. The
set of robot commands your program must process are as follows:

### Commands and their meaning
[U|u] Pen up
[D|d] Pen down
[R|r] Turn right
[L|l] Turn left
[M s|m s] Move forward s spaces (s is a non-negative integer)
[P|p] Print the N by N array and display the indices
[C|c] Print current position of the pen and whether it is up or down and its
facing direction
[Q|q] Stop the program
[I n|i n] Initialize the system: The values of the array floor are zeros and the robot
is back to [0, 0], pen up and facing north. n size of the array, an integer
greater than zero 
