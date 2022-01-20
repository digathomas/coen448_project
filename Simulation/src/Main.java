import java.util.Scanner;

class Main{
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        RobotBoard board = new RobotBoard();
        while(true){
            while (board.getBoard() == null){
                System.out.print("Initialize the system with the following format [i n]: ");
                String initial = scanner.nextLine().toLowerCase().trim();
                if (initial.charAt(0) != 'i')
                    System.out.println("Please try again\n");
                else{
                    board.move(initial);
                }
            }
            System.out.print("Enter Command: ");
            String command = scanner.nextLine().toLowerCase().trim();
            board.move(command);
        }
    }
}

class RobotBoard{

    //private static char[] directions = {'n', 'e', 's', 'w'};
    private final char[] directions = {'^', '>', 'v', '<'};
    private char directionIndex = 0; //n e s w

    private char[][] board = null;
    private int posX;
    private int posY;
    private Boolean penUp = true;

    RobotBoard(){
        posX = 0;
        posY = 0;
    }

    public int move (String command){
        try {
            //Moving forward and Initialize systems
            if (command.charAt(1) == ('0') || command.charAt(1) == (' ')){
                final int s = Integer.parseInt(command.substring(2).trim());
                //Move forward s spaces
                if (command.charAt(0) == 'm') {
                    moveForward(s);
                } else if (command.charAt(0) == 'i') {
                    initializeSystem(s);
                } else {
                    throw new Exception("This is an Invalid command. Please enter a valid command");
                }
            } else {
                switch (command) {
                    case ("u"):
                        setPenUp(true);
                        break;
                    case ("d"):
                        setPenUp(false);
                        break;
                    case ("r"):
                        turn('r');
                        break;
                    case ("l"):
                        turn('l');
                        break;
                    case ("p"):
                        printArray();
                        break;
                    case ("c"):
                        printCurrentPosition();
                        break;
                    case ("q"):
                        return quit();
                    default:
                        throw new Exception("This is an Invalid command. Please enter a valid command");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        return 1;
    }

    private void moveForward(int s) throws Exception {
        int tempX = posX;
        int tempY = posY;
        int increment = 1;

        switch (directionIndex){
            case(0):
                //Moving North
                tempY += s;
                break;
            case(1):
                //Moving East
                tempX += s;
                break;
            case(2):
                //Moving South
                tempY -= s;
                increment = -1;
                break;
            case(3):
                //Moving West
                tempX -= s;
                increment = -1;
                break;
        }

        if (tempX < 0 || tempX >= board.length || tempY < 0 || tempY >= board.length)
            throw new Exception("Position moves out of bound, Try again");

        if (tempX != posX){
            for (int i = posX; i < tempX; i += increment){
                board[i][posY] = 'X';
            }
        }
        else if(tempY != posY){
            for (int j = posY; j < tempY; j += increment){
                board[posX][j] = 'X';
            }
        }

    }
    private void initializeSystem(int n) throws Exception {
        if (n < 1)
            throw new Exception("Please Enter a valid board size value");
        board = new char[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                board[i][j] = '-';
            }
        }
    }
    private void setPenUp(Boolean bool){
        penUp = bool;
    }
    private void turn(char turnMovement){

        if (turnMovement == 'l') {
            directionIndex--;
        }
        else{
            directionIndex++;
        }

        if (directionIndex > 3){
            directionIndex = 0;
        }
        else if (directionIndex < 0){
            directionIndex = 3;
        }
    }
    private void printArray() {

    }
    private void printCurrentPosition(){

    }
    private int quit(){
        return 0;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }
}