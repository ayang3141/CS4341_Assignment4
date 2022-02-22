import java.io.*;
import java.util.*;
import java.lang.*;

public class RewardMatrix {
    // This class is responsible for handling the reward matrix and any related methods

    // useful variables
    int numRows;    // number of rows in the matrix
    int numCols;    // number of columns in the matrix
    double[][] rewardMatrix = new double[numRows][numCols]; // reward matrix array
    String fileName;    // name of the board text file

    // constructor for the RewardMatrix class
    public RewardMatrix(String fileName) {
        this.fileName = fileName;
    }

    // retrieves the number of rows in the matrix
    public int getNumRows() {
        return this.numRows;
    }

    // retrieves the number of columns in the matrix
    public int getNumCols() {
        return this.numCols;
    }

    // gets the reward of the respective coordinate on the board
    public double getRewardValue(Coordinate coordinate) {
        return this.rewardMatrix[coordinate.getX()][coordinate.getY()];
    }

    // determines if the given coordinate is out of bounds
    public boolean OutOfBounds(Coordinate coordinate) {
        if((coordinate.getX() >= 0) && (coordinate.getX() <= numRows-1)
                && (coordinate.getY() >= 0) && (coordinate.getY() <= numCols-1)) {
            return false;
        }
        return true;
    }

    // checks if the given coordinate is a terminal state
    public boolean checkTerminal(Coordinate position) {
        return getRewardValue(position) != 0;
    }

    // randomly generates a starting position on the board that is not a terminal state
    public Coordinate generateStartPoint() {
        Random random = new Random();
        int x = random.nextInt(this.numRows);
        int y = random.nextInt(this.numCols);
        while(checkTerminal(new Coordinate(x, y))) {
            x = random.nextInt(this.numRows);
            y = random.nextInt(this.numCols);
        }
        return new Coordinate(x, y);
    }

    // This method generates the game board from the given file name
    public void generateBoard(double constantReward) throws IOException {
        // File I/O
        File levelFile = new File(this.fileName);

        // count total chars
        BufferedReader colScanner = new BufferedReader(new FileReader(levelFile));
        String line = null;
        while((line = colScanner.readLine()) != null) {
            System.out.println(line);
            //tokenize it here
            String[] tokens = line.split("\t");
            numCols = tokens.length;
            numRows++;
        }

        System.out.println(numCols);
        System.out.println(numRows);

        Scanner sc = new Scanner(levelFile);
        sc.useDelimiter("\\n|\\t");
        double[][] level = new double[numRows][numCols];



        // now, actually put them in a list. would be better to do this all in one loop, but this works.
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {

                if(sc.hasNext()) {
                    level[i][j] = (double)sc.nextInt(); // Convert to char
                }


            }
        }
        this.rewardMatrix = level;
        sc.close();

    }





}
