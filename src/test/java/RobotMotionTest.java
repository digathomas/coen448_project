import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class RobotMotionTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    RobotMotion robotMotion;

    @BeforeEach
    public void setup(){
        robotMotion = new RobotMotion();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("Getting argument from strings test")
    @Test
    void getArg()
    {
        assertEquals(robotMotion.getArg(new String[]{"U"}),-1);
        assertNotEquals(robotMotion.getArg(new String[]{"M", "4"}), -1);
    }

    @DisplayName("Retrieve array of strings from single string")
    @ParameterizedTest()
    @ValueSource(strings = {"U", "D", "r", "L", "M 4"})
    void getStrings(String command){
        char uppercase = Character.toUpperCase(command.charAt(0));
        assertEquals(uppercase,robotMotion.getStrings(command)[0].charAt(0));
    }

    @Test
    void robotPositionNull(){
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        assertFalse(RobotMotion.penUp());
        assertFalse(RobotMotion.penDown());
        assertFalse(RobotMotion.turnRight());
        assertFalse(RobotMotion.turnLeft());
        assertFalse(RobotMotion.moveForward(0));
        assertFalse(RobotMotion.printCurrent());
        assertFalse(RobotMotion.printArray());
    }

    @DisplayName("Pen up test")
    @Test
    void penUp() {
        RobotMotion.robotPosition = new RobotPosition();
        assertTrue(RobotMotion.penUp());
        assertFalse(RobotMotion.robotPosition.getPenDown());
    }

    @DisplayName("Pen down and trace test")
    @Test
    void penDown() {
        RobotMotion.robotPosition = new RobotPosition();
        RobotMotion.roomArray = new RoomArray(3);
        assertTrue(RobotMotion.penDown());
        assertTrue(RobotMotion.robotPosition.getPenDown());

        int x = RobotMotion.robotPosition.getPos_x();
        int y = RobotMotion.robotPosition.getPos_y();
        assertTrue(RobotMotion.roomArray.getRoomArray()[x][y]);
    }

    @DisplayName("Turning left and right test")
    @Test
    void turnRightAndLeft() {
        RobotMotion.robotPosition = new RobotPosition();
        RobotPosition.Direction oldDirection = RobotMotion.robotPosition.getDirection();

        //turn right
        assertTrue(RobotMotion.turnRight());
        assertNotEquals(oldDirection, RobotMotion.robotPosition.getDirection());

        //turn left
        RobotPosition.Direction oldDirection2 = RobotMotion.robotPosition.getDirection();
        assertTrue(RobotMotion.turnLeft());
        assertNotEquals(oldDirection2, RobotMotion.robotPosition.getDirection());

        assertEquals(oldDirection, RobotMotion.robotPosition.getDirection());
    }

    @DisplayName("RobotMotion forward in given direction test")
    @Test
    void moveForward() {
        RobotMotion.robotPosition = new RobotPosition();
        RobotMotion.roomArray = new RoomArray(9);

        //North PenDown = false
        RobotMotion.moveForward(1);
        assertFalse(RobotMotion.roomArray.getRoomArray()[0][1]);
        assertEquals(0,RobotMotion.robotPosition.getPos_x());
        assertEquals(1,RobotMotion.robotPosition.getPos_y());

        //East PenDown = false
        RobotMotion.turnRight();
        RobotMotion.moveForward(2);
        assertFalse(RobotMotion.roomArray.getRoomArray()[0][1]);
        assertFalse(RobotMotion.roomArray.getRoomArray()[1][1]);
        assertFalse(RobotMotion.roomArray.getRoomArray()[2][1]);
        assertEquals(2,RobotMotion.robotPosition.getPos_x());
        assertEquals(1,RobotMotion.robotPosition.getPos_y());

        //South PenDown = true
        RobotMotion.turnRight();
        RobotMotion.penDown();
        RobotMotion.moveForward(1);
        assertTrue(RobotMotion.roomArray.getRoomArray()[2][1]);
        assertTrue(RobotMotion.roomArray.getRoomArray()[2][0]);
        assertEquals(2,RobotMotion.robotPosition.getPos_x());
        assertEquals(0,RobotMotion.robotPosition.getPos_y());

        //West PenDown = true
        //moving off board
        RobotMotion.turnRight();
        RobotMotion.moveForward(3);
        assertTrue(RobotMotion.roomArray.getRoomArray()[2][0]);
        assertTrue(RobotMotion.roomArray.getRoomArray()[1][0]);
        assertTrue(RobotMotion.roomArray.getRoomArray()[0][0]);
        assertEquals(0,RobotMotion.robotPosition.getPos_x());
        assertEquals(0,RobotMotion.robotPosition.getPos_y());


    }

    @DisplayName("Print out array test")
    @Test
    void printArray() {
        RobotMotion.roomArray = new RoomArray(1);
        String expected = "0     \n    0";
        RobotMotion.printArray();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Print array with robot position test")
    @Test
    void printCurrent() {
        RobotMotion.initializeSystem(1);
        String expected = "Position: 0, 0\r\nPen: up\r\nFacing: north";
        RobotMotion.printCurrent();
        String actual = outputStreamCaptor.toString().trim();
        assertEquals(expected, actual);
    }

    @DisplayName("Initializing robot system test")
    @Test
    void initializeSystem() {
        assertFalse(RobotMotion.initializeSystem(0));
        assertTrue(RobotMotion.initializeSystem(4));
        assertNotNull(RobotMotion.robotPosition);
        assertNotNull(RobotMotion.roomArray);

        //roomArray initialization
        int x,y,i;
        for (i = 0; i < 3; i++) {
            x = (int) (Math.random() * 3);
            y = (int) (Math.random() * 3);
            assertFalse(RobotMotion.roomArray.getRoomArray()[x][y]);
        }
    }

    @Test
    void help() {
        String expected ="U - pen up\r\nD - pen down\r\nR - turn right\r\nL - turn left\r\nM s - move forward s spaces\r\nP - print array\r\nC - print current position\r\nQ - stop program\r\nI n - initilize system with n size array";
        RobotMotion.help();
        String actual = outputStreamCaptor.toString().trim();
        assertEquals(expected, actual);
    }
}