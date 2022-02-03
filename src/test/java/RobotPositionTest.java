import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class RobotPositionTest {

    RobotPosition robotPosition;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @BeforeEach
    public void setup(){
        robotPosition = new RobotPosition();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void print() {
        robotPosition.print();
        assertTrue(outputStreamCaptor.toString().contains("up"));
        assertTrue(outputStreamCaptor.toString().contains("north"));

        robotPosition.turnRight();
        robotPosition.print();
        assertTrue(outputStreamCaptor.toString().contains("east"));

        robotPosition.turnRight();
        robotPosition.setPenDown(true);
        robotPosition.print();
        assertTrue(outputStreamCaptor.toString().contains("down"));
        assertTrue(outputStreamCaptor.toString().contains("south"));

        robotPosition.turnRight();
        robotPosition.print();
        assertTrue(outputStreamCaptor.toString().contains("down"));
        assertTrue(outputStreamCaptor.toString().contains("west"));


    }

    @DisplayName("RobotPosition TurnRight Test")
    @Test
    void turnRight() {
        //Starts North
        robotPosition.turnRight();
        assertEquals(RobotPosition.Direction.EAST,robotPosition.getDirection());
        robotPosition.turnRight();
        assertEquals(RobotPosition.Direction.SOUTH,robotPosition.getDirection());
        robotPosition.turnRight();
        assertEquals(RobotPosition.Direction.WEST,robotPosition.getDirection());
        robotPosition.turnRight();
        assertEquals(RobotPosition.Direction.NORTH,robotPosition.getDirection());
    }

    @DisplayName("RobotPosition TurnLeft Test")
    @Test
    void turnLeft() {
        //Starts North
        robotPosition.turnLeft();
        assertEquals(RobotPosition.Direction.WEST,robotPosition.getDirection());
        robotPosition.turnLeft();
        assertEquals(RobotPosition.Direction.SOUTH,robotPosition.getDirection());
        robotPosition.turnLeft();
        assertEquals(RobotPosition.Direction.EAST,robotPosition.getDirection());
        robotPosition.turnLeft();
        assertEquals(RobotPosition.Direction.NORTH,robotPosition.getDirection());
    }

    @DisplayName("RobotPosition move(x,y) Test")
    @Test
    void move() {
        int a = robotPosition.getPos_x();
        int b = robotPosition.getPos_y();

        robotPosition.move(3,2);
        assertEquals(a+3, robotPosition.getPos_x());
        assertEquals(b+2, robotPosition.getPos_y());

        robotPosition.move(1,0);
        assertEquals(a+4, robotPosition.getPos_x());
        assertEquals(b+2, robotPosition.getPos_y());
    }
}