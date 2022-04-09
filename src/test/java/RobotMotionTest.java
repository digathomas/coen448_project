import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
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

    @DisplayName("Robot Position Null test")
    @Test
    void robotPositionNull(){
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = new RoomArray(3);
        assertFalse(RobotMotion.penUp());
        assertFalse(RobotMotion.penDown());
        assertFalse(RobotMotion.turnRight());
        assertFalse(RobotMotion.turnLeft());
        assertFalse(RobotMotion.moveForward(0));
        assertFalse(RobotMotion.printCurrent());
        assertTrue(RobotMotion.printArray());
    }

    @DisplayName("Room Array Null test")
    @Test
    void roomArrayNull(){
        RobotMotion.robotPosition = new RobotPosition();
        RobotMotion.roomArray = null;
        assertTrue(RobotMotion.penUp());
        assertFalse(RobotMotion.penDown());
        assertTrue(RobotMotion.turnRight());
        assertTrue(RobotMotion.turnLeft());
        assertFalse(RobotMotion.moveForward(0));
        assertTrue(RobotMotion.printCurrent());
        assertFalse(RobotMotion.printArray());
    }

    @DisplayName("Robot Position and Room Array Null test")
    @Test
    void robotPositionAndRoomArrayNull(){
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

    @DisplayName("Move forward command with negative number of steps  test")
    @Test
    void moveForwardNegativeStepsTest() {
        RobotMotion.robotPosition = new RobotPosition();
        RobotMotion.roomArray = new RoomArray(9);
        String expected = "Invalid move command: number of spaces (s) must be positive.";
        RobotMotion.moveForward(-3);
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Initialize system to size less than one test")
    @Test
    void initializeSystemSizeLessThanOneTest() {
        RobotMotion.initializeSystem(0);
        String expected = "Invalid initialize command: size of array (n) must be greater than zero.";
        assertEquals(expected, outputStreamCaptor.toString().trim());
        outputStreamCaptor.reset();
        RobotMotion.initializeSystem(-3);
        assertEquals(expected, outputStreamCaptor.toString().trim());
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

    @DisplayName("Replaying commands")
    @Test
    void replayCommandList() {
        assertTrue(RobotMotion.replayCommandList()); //empty list

        assertTrue(RobotMotion.initializeSystem(4));
        assertTrue(RobotMotion.penDown());
        assertTrue(RobotMotion.moveForward(3));
        assertTrue(RobotMotion.turnRight());
        assertTrue(RobotMotion.moveForward(2));
        assertEquals(2,RobotMotion.robotPosition.getPos_x());
        assertEquals(3,RobotMotion.robotPosition.getPos_y());

        assertTrue(RobotMotion.replayCommandList()); //position comes back to same
        assertEquals(2,RobotMotion.robotPosition.getPos_x());
        assertEquals(3,RobotMotion.robotPosition.getPos_y());

        assertTrue(RobotMotion.replayCommandList()); //no infinite loop
    }

    @Test
    void help() {
        String expected ="Valid commands:\r\nU - pen up\r\nD - pen down\r\nR - turn right\r\nL - turn left\r\n" +
                "M s - move forward s spaces\r\nP - print array\r\nC - print current position\r\nQ - stop program\r\n" +
                "I n - initialize system with n size array\r\nH - replay all the commands executed";
        RobotMotion.help();
        String actual = outputStreamCaptor.toString().trim();
        assertEquals(expected, actual);
    }

    @DisplayName("Pen up command - system uninitialized")
    @Test
    void penUpSystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.penUp();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Pen down command - system uninitialized output message test")
    @Test
    void penDownSystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.penDown();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Turn right command - system uninitialized output message test")
    @Test
    void turnRightSystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.turnRight();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Turn left command - system uninitialized output message test")
    @Test
    void turnLeftSystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.turnLeft();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Move forward command - system uninitialized output message test")
    @Test
    void moveForwardSystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.moveForward(0);
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Print current command - system uninitialized output message test")
    @Test
    void printCurrentSystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.printCurrent();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @DisplayName("Print array command - system uninitialized output message test")
    @Test
    void printArraySystemUninitializedOutputMessageTest() {
        RobotMotion.robotPosition = null;
        RobotMotion.roomArray = null;
        String expected = "System not initialized.";
        RobotMotion.printArray();
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }
}