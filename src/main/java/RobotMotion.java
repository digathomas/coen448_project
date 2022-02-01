import java.util.Scanner;

/*
 * DEMO COMMANDS
 * I 10
 * D
 * M 4
 * R
 * M 3
 * C
 * P
 */

public class RobotMotion {
	
	//global variable
	public static RoomArray roomArray = null;
	public static RobotPosition robotPosition = null;

	public static void main(String[] args) {
		loop();
	}
	
	public static void loop() {
		while(true) {
			try {
				System.out.print("Enter command: "); // user input
				Scanner input = new Scanner(System.in);
				String command = input.nextLine();
				String[] c = getStrings(command);
				//TODO: try catch block
				int arg = getArg(c);
				Boolean success = true;
				switch(c[0]) {
				case "U":
					System.out.println("pen up");
					success = penUp();
					break;
				case "D":
					System.out.println("pen down");
					success = penDown();
					break;
				case "R":
					System.out.println("turn right");
					success = turnRight();
					break;
				case "L":
					System.out.println("turn left");
					success = turnLeft();
					break;
				case "M":
					System.out.println("move forward");
					success = moveForward(arg);
					break;
				case "P":
					System.out.println("print array");
					success = printArray();
					break;
				case "C":
					System.out.println("print current");
					success = printCurrent();
					break;
				case "Q":
					System.out.println("stop program");
					success = stopProgram();
					break;
				case "I":
					System.out.println("initilize system");
					success = initializeSystem(arg);
					break;
				default:
					help();
				}
				if(!success) {
					System.out.println("Error: command unsuccesfully executed");
				}
			} catch(Exception e) {
				System.out.println("Error: user input failed");
				continue;
			} finally {
				
			}
		}
		//input.close()
	}

	public static String[] getStrings(String command) {
		String[] c = command.split("\\s+", 2);
		c[0] = c[0].toUpperCase(); //command to upper case
		return c;
	}

	public static int getArg(String[] c) {
		int arg = -1;
		if(c.length > 1) {
			arg = Integer.parseInt(c[1]); //argument to integer
		}
		return arg;
	}

	public static boolean penUp() {
		if(robotPosition != null) {
			robotPosition.setPenDown(false);
			return true;
		}
		return false;
	}
	
	public static boolean penDown() {
		if(robotPosition != null && roomArray != null) {
			robotPosition.setPenDown(true);
			roomArray.trace(robotPosition.getPos_x(), robotPosition.getPos_y());
			return true;
		}
		return false;
	}
	
	public static boolean turnRight() {
		if(robotPosition != null) {
			robotPosition.turnRight();
			return true;
		}
		return false;
	}
	
	public static boolean turnLeft() {
		if(robotPosition != null) {
			robotPosition.turnLeft();
			return true;
		}
		return false;
	}
	
	public static boolean moveForward(int s) {
		if(robotPosition != null && roomArray != null) {
			if(robotPosition.getDirection() == RobotPosition.Direction.NORTH) {
				for(int it = 0; it < s+1; it++) {
					//add trace while moving
					if(robotPosition.getPenDown()) {
						roomArray.trace(robotPosition.getPos_x(), robotPosition.getPos_y()+it);
					}
					//reached array limit
					if(robotPosition.getPos_y()+it == roomArray.getSize()-1) {
						s = it;
						break;
					}
				}
				//set new robot position
				robotPosition.move(0,s);
			} else if(robotPosition.getDirection() == RobotPosition.Direction.EAST) {
				for(int it = 0; it < s+1; it++) {
					//add trace while moving
					if(robotPosition.getPenDown()) {
						roomArray.trace(robotPosition.getPos_x()+it, robotPosition.getPos_y());
					}
					//reached array limit
					if(robotPosition.getPos_x()+it == roomArray.getSize()-1) {
						s = it;
						break;
					}
				}
				//set new robot position
				robotPosition.move(s,0);
			} else if(robotPosition.getDirection() == RobotPosition.Direction.SOUTH) {
				for(int it = 0; it < s+1; it++) {
					//add trace while moving
					if(robotPosition.getPenDown()) {
						roomArray.trace(robotPosition.getPos_x(), robotPosition.getPos_y()-it);
					}
					//reached array limit
					if(robotPosition.getPos_y()-it == 0) {
						s = it;
						break;
					}
				}
				//set new robot position
				robotPosition.move(0,-s);
			} else if(robotPosition.getDirection() == RobotPosition.Direction.WEST) {
				for(int it = 0; it < s+1; it++) {
					//add trace while moving
					if(robotPosition.getPenDown()) {
						roomArray.trace(robotPosition.getPos_x()-it, robotPosition.getPos_y());
					}
					//reached array limit
					if(robotPosition.getPos_x()-it == 0) {
						s = it;
						break;
					}
				}
				//set new robot position
				robotPosition.move(-s,0);
			}
			return true;
		}
		return false;
	}
	
	public static boolean printArray() {
		if(roomArray != null) {
			roomArray.print();
			return true;
		}
		return false;
	}
	
	public static boolean printCurrent() {
		if(robotPosition != null) {
			robotPosition.print();
			return true;
		}
		return false;
	}
	
	public static boolean stopProgram() {
		System.exit(0);
		return false;
	}
	
	public static boolean initializeSystem(int n) {
		if(n>0) {
			roomArray = new RoomArray(n);
			robotPosition = new RobotPosition();
			return true;
		}
		return false;
	}
	
	public static boolean help() {
		System.out.println("U - pen up");
		System.out.println("D - pen down");
		System.out.println("R - turn right");
		System.out.println("L - turn left");
		System.out.println("M s - move forward s spaces");
		System.out.println("P - print array");
		System.out.println("C - print current position");
		System.out.println("Q - stop program");
		System.out.println("I n - initilize system with n size array");
		return true;
	}

}
