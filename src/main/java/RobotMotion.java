import java.util.ArrayList;
import java.util.List;
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
	private static List<String> commandList = new ArrayList<>();

	public static void main(String[] args) {
		RobotMotion robotMotion = new RobotMotion();
		robotMotion.loop();
	}
	
	public void loop() {
		while(true) {
			try {
				System.out.print("Enter command: "); // user input
				Scanner input = new Scanner(System.in);
				String command = input.nextLine().trim();
				String[] c = getStrings(command);
				int arg = getArg(c);
				Boolean success = false;
				switch(c[0]) {
				case "U":
					System.out.println("Pen up");
					success = penUp();
					break;
				case "D":
					System.out.println("Pen down");
					success = penDown();
					break;
				case "R":
					System.out.println("Turn right");
					success = turnRight();
					break;
				case "L":
					System.out.println("Turn left");
					success = turnLeft();
					break;
				case "M":
					System.out.println("Move forward");
					if (c.length < 2) {
						System.out.println("Invalid move command: missing spaces (s) argument." +
											" Correct format: [M s|m s]");
						break;
					}
					success = moveForward(arg);
					break;
				case "P":
					System.out.println("print array");
					success = printArray();
					break;
				case "C":
					System.out.println("Print current");
					success = printCurrent();
					break;
				case "Q":
					System.out.println("Stop program");
					success = stopProgram();
					break;
				case "I":
					System.out.println("Initialize system");
					if (c.length < 2) {
						System.out.println("Invalid initialize command: missing size of array (n) argument." +
											" Correct format: [I n|i n]");
						break;
					}
					success = initializeSystem(arg);
					break;
				case "H":
					System.out.println("Replay commands");
					success = replayCommandList();
					break;
				default:
					if (command.isEmpty()) {
						System.out.println("Invalid input: empty command");
					}
					else {
						System.out.println("Unknown command.");
					}
					help();
				}
				if (!c[0].equals("H")) {
					commandList.add(command);
				}
				if(!success) {
					System.out.println("Command unsuccessfully executed");
				}
			} catch(Exception e) {
				System.out.println("user input failed");
			}
		}
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
		System.out.println("System not initialized.");
		return false;
	}
	
	public static boolean penDown() {
		if(robotPosition != null && roomArray != null) {
			robotPosition.setPenDown(true);
			roomArray.trace(robotPosition.getPos_x(), robotPosition.getPos_y());
			return true;
		}
		System.out.println("System not initialized.");
		return false;
	}
	
	public static boolean turnRight() {
		if(robotPosition != null) {
			robotPosition.turnRight();
			return true;
		}
		System.out.println("System not initialized.");
		return false;
	}
	
	public static boolean turnLeft() {
		if(robotPosition != null) {
			robotPosition.turnLeft();
			return true;
		}
		System.out.println("System not initialized.");
		return false;
	}
	
	public static boolean moveForward(int s) {
		if (s < 0) {
			System.out.println("Invalid move command: number of spaces (s) must be positive.");
		}
		else if (robotPosition != null && roomArray != null) {
			if(robotPosition.getDirection() == RobotPosition.Direction.NORTH) {
				for(int it = 0; it < s+1; it++) {
					//add trace while moving
					if(robotPosition.getPenDown()) {
						roomArray.trace(robotPosition.getPos_x(), robotPosition.getPos_y()+it);
					}
					//reached array limit
					if(robotPosition.getPos_y()+it == roomArray.getSize()-1) {
						s = it;
						System.out.println("Reached floor array bounds. Move executed for " + s + " steps.");
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
						System.out.println("Reached floor array bounds. Move executed for " + s + " steps.");
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
						System.out.println("Reached floor array bounds. Move executed for " + s + " steps.");
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
						System.out.println("Reached floor array bounds. Move executed for " + s + " steps.");
						break;
					}
				}
				//set new robot position
				robotPosition.move(-s,0);
			}
			return true;
		}
		else {
			System.out.println("System not initialized.");
		}
		return false;
	}
	
	public static boolean printArray() {
		if(roomArray != null) {
			roomArray.print();
			return true;
		}
		System.out.println("System not initialized.");
		return false;
	}
	
	public static boolean printCurrent() {
		if(robotPosition != null) {
			robotPosition.print();
			return true;
		}
		System.out.println("System not initialized.");
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
		else {
			System.out.println("Invalid initialize command: size of array (n) must be greater than zero.");
		}
		return false;
	}

	public static boolean replayCommandList(){
		System.out.println();
		for (int i = 0; i < commandList.size(); i++) {
			try{
			String command = commandList.get(i);
			String[] c = getStrings(command);
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
						System.out.println("initialize system");
						success = initializeSystem(arg);
						break;
					case "H":
						System.out.println("replay commands");
						System.out.println("unsuccessfully executed: infinite loop");
						//success = replayCommandHistory();
						break;
					default:
						//help();
				}
				if(!success) {
					System.out.println("command unsuccessfully executed");
				}
			} catch(Exception e) {
				System.out.println("user input failed");
			}
		}
		return true;
	}

	public static boolean help() {
		System.out.println("Valid commands:");
		System.out.println("U - pen up");
		System.out.println("D - pen down");
		System.out.println("R - turn right");
		System.out.println("L - turn left");
		System.out.println("M s - move forward s spaces");
		System.out.println("P - print array");
		System.out.println("C - print current position");
		System.out.println("Q - stop program");
		System.out.println("I n - initialize system with n size array");
		System.out.println("H - replay all the commands executed");
		return true;
	}

}
