import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class RobotMotionTest {

    RobotMotion robotMotion;
    @BeforeEach
    public void setup(){
        robotMotion = new RobotMotion();
    }

    @ParameterizedTest()
    @MethodSource("arrayOfStrings")
    static void getArg(String[] c){
        assertNotEquals(RobotMotion.getArg(c),-1);
    }

    static Stream<Arguments> arrayOfStrings() {
        return Stream.of(
                arguments(Arrays.asList("U")),
                arguments(Arrays.asList("r")),
                arguments(Arrays.asList("M", " ", "4"))
        );
    }

    @ParameterizedTest()
    @ValueSource(strings = {"U", "D", "r", "L", "M 4"})
    static void getStrings(String command){

    }

    @Test
    static void robotPositionNull(){
        assertFalse(RobotMotion.penUp());
        assertFalse(RobotMotion.penDown());
        assertFalse(RobotMotion.turnRight());
        assertFalse(RobotMotion.turnLeft());
        assertFalse(RobotMotion.moveForward(0));
        assertFalse(RobotMotion.printCurrent());
    }

    @Test
    void penUp() {
        RobotMotion.robotPosition = new RobotPosition();
        assertTrue(RobotMotion.penUp());
        assertFalse(RobotMotion.robotPosition.getPenDown());
    }

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

    @Test
    void moveForward() {
        RobotMotion.robotPosition = new RobotPosition();
        RobotMotion.roomArray = new RoomArray(9);




    }

    @Test
    void printArray() {
        RobotMotion.roomArray = new RoomArray(9);
        assertTrue(RobotMotion.printArray());
    }

    @Test
    void printCurrent() {
        RobotMotion.robotPosition = new RobotPosition();
        assertTrue(RobotMotion.printCurrent());
    }

    @Test
    void initializeSystem() {
        assertFalse(RobotMotion.initializeSystem(0));
        assertTrue(RobotMotion.initializeSystem(3));
        assertNotNull(RobotMotion.robotPosition);
        assertNotNull(RobotMotion.roomArray);

        //roomArray initialization
        int x,y,i;
        for (i = 0; i < 3; i++) {
            x = (int) Math.random() * 3;
            y = (int) Math.random() * 3;
            assertFalse(RobotMotion.roomArray.getRoomArray()[x][y]);
        }
    }

    @Test
    void help() {
    }
}