package coen448_project;

public class RobotPosition {
	
	private int pos_x;
	private int pos_y;
	private Direction direction;
	private Boolean penDown;
	
	//constructor
	public RobotPosition(){
		this.pos_x = 0;
		this.pos_y = 0;
		this.direction = Direction.NORTH;
		this.penDown = false;
	}
	
	public void print() {
		System.out.println("Position: " + pos_x + ", " + pos_y);
		if(penDown == true) {
			System.out.println("Pen: down");
		} else {
			System.out.println("Pen: up");
		}
		if(direction == Direction.NORTH) {
			System.out.println("Facing: north");
		} else if(direction == Direction.EAST) {
			System.out.println("Facing: east");
		} else if(direction == Direction.SOUTH) {
			System.out.println("Facing: south");
		} else if(direction == Direction.WEST) {
			System.out.println("Facing: west");
		}
	}
	
	public void turnRight() {
		if(direction == Direction.NORTH) {
			direction = Direction.EAST;
		} else if(direction == Direction.EAST) {
			direction = Direction.SOUTH;
		} else if(direction == Direction.SOUTH) {
			direction = Direction.WEST;
		} else if(direction == Direction.WEST) {
			direction = Direction.NORTH;
		}
	}
	
	public void turnLeft() {
		if(direction == Direction.NORTH) {
			direction = Direction.WEST;
		} else if(direction == Direction.EAST) {
			direction = Direction.NORTH;
		} else if(direction == Direction.SOUTH) {
			direction = Direction.EAST;
		} else if(direction == Direction.WEST) {
			direction = Direction.SOUTH;
		}
	}
	
	public void move(int delta_x, int delta_y) {
		this.pos_x += delta_x;
		this.pos_y += delta_y;
	}
	
	//setters
	public void setPenDown(boolean p) {
		this.penDown = p;
	}
	
	//getters
	public int getPos_x() {
		return this.pos_x;
	}
	public int getPos_y() {
		return this.pos_y;
	}
	public Direction getDirection() {
		return this.direction;
	}
	public boolean getPenDown() {
		return this.penDown;
	}
	
	//enums
	static public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST
	}
}
