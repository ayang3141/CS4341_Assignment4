import java.io.*;
import java.util.*;
import java.lang.*;


public class RewardMatrix {

    int numRows;
    int numCols;
    double[][] rewardMatrix = new double[numRows][numCols];
    List<Coordinate> terminalList = new ArrayList<Coordinate>();
    String fileName;

    public RewardMatrix(String fileName) {
        this.fileName = fileName;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public double getRewardValue(Coordinate coordinate) {
        return this.rewardMatrix[coordinate.getX()][coordinate.getY()];
    }

    // This method determines if the given coordinate is out of bounds
    public boolean OutOfBounds(Coordinate coordinate) {
        if((coordinate.getX() >= 0) && (coordinate.getX() <= numRows-1)
                && (coordinate.getY() >= 0) && (coordinate.getY() <= numCols-1)) {
            return false;
        }
        return true;
    }

    public boolean checkTerminal(Coordinate position) {
        return getRewardValue(position) != 0;
    }

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
