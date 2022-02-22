import java.util.Random;

// This class is purely responsible for the agent and what it should do
public class Agent {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    RewardMatrix rewardMatrix;

    // Constructor for the Agent class
    public Agent(RewardMatrix rewardMatrix) {
        this.rewardMatrix = rewardMatrix;
    }



    // Get the coordinates of some number of spaces to the right of the Agent
    public Coordinate getRightSpace(Coordinate oldPosition) {
        Coordinate newPosition = new Coordinate(oldPosition.getX(), oldPosition.getY() + 1);
        if(checkSpace(newPosition)) {
            return newPosition;
        }
        return oldPosition;
    }

    // Get the coordinates of some number of spaces to the left of the Agent
    public Coordinate getLeftSpace(Coordinate oldPosition) {
        Coordinate newPosition = new Coordinate(oldPosition.getX(), oldPosition.getY() - 1);
        if(checkSpace(newPosition)) {
            return newPosition;
        }
        return oldPosition;
    }

    // Get the coordinates of some number of spaces above the Agent
    public Coordinate getUpSpace(Coordinate oldPosition) {
        Coordinate newPosition = new Coordinate(oldPosition.getX() - 1, oldPosition.getY());
        if(checkSpace(newPosition)) {
            return newPosition;
        }
        return oldPosition;
    }

    // Get the coordinates of some number of spaces below the Agent
    public Coordinate getDownSpace(Coordinate oldPosition) {
        Coordinate newPosition = new Coordinate(oldPosition.getX() + 1, oldPosition.getY());
        if(checkSpace(newPosition)) {
            return newPosition;
        }
        return oldPosition;
    }

    // Get the next state given an action and a deflection probability
    public Coordinate getNextState(Coordinate oldPosition, int action, double intendedProb) {
        Random random = new Random();
        int deflection = -1;
        double[] cumProb = new double[] {intendedProb, 0.5 + 0.5*intendedProb, 1.0}; // {no deflect, deflect right, deflect left}
        for(int i = 0; i < cumProb.length && deflection == -1; i++) {
            double roll = random.nextDouble();
            if(roll < cumProb[i]) {
                deflection = i;
            }
        }

        switch(action) {
            case Agent.UP:
                if(deflection == 0) { // no deflection
                    return getUpSpace(oldPosition);
                }
                else if(deflection == 1) { // deflect right
                    return getRightSpace(oldPosition);
                }
                else { // deflect left
                    return getLeftSpace(oldPosition);
                }
            case Agent.DOWN:
                if(deflection == 0) { // no deflection
                    return getDownSpace(oldPosition);
                }
                else if(deflection == 1) { // deflect right
                    return getLeftSpace(oldPosition);
                }
                else { // deflect left
                    return getRightSpace(oldPosition);
                }
            case Agent.LEFT:
                if(deflection == 0) { // no deflection
                    return getLeftSpace(oldPosition);
                }
                else if(deflection == 1) { // deflect right
                    return getUpSpace(oldPosition);
                }
                else { // deflect left
                    return getDownSpace(oldPosition);
                }
            case Agent.RIGHT:
                if(deflection == 0) { // no deflection
                    return getRightSpace(oldPosition);
                }
                else if(deflection == 1) { // deflect right
                    return getDownSpace(oldPosition);
                }
                else { // deflect left
                    return getUpSpace(oldPosition);
                }
        }

        return null;
    }

    // determine the best action to take at the current position given an epsilon probability
    public int nextAction(double[][][] qTable, Coordinate current, double epsilon) {
        int actionToTake = -1;
        Random random = new Random();
        if(random.nextDouble() <= epsilon) { // take a random action
            actionToTake = random.nextInt(4);
        }
        else { // otherwise pick action with highest score
            double bestValue = Double.NEGATIVE_INFINITY;
            for(int actIndex = 0; actIndex < 4; actIndex++) {
                double currentValue = qTable[current.getX()][current.getY()][actIndex];
                if(currentValue > bestValue) {
                    bestValue = currentValue;
                    actionToTake = actIndex;
                }
            }
        }

        return actionToTake;
    }

    // This method checks if the coordinate is a valid position on the board
    public boolean checkSpace(Coordinate coordinate) {
        if(this.rewardMatrix.OutOfBounds(coordinate)) {
            return false;
        }
        return true;
    }

    /*
        Print out the Q-Learning Policy:
        UP = ^
        DOWN = v
        LEFT = <
        RIGHT = >
     */
    public void printQPolicy(double[][][] qTable) {
        int numRows = qTable.length;
        int numCols = qTable[0].length;

        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                int direction = -1;
                double bestValue = Double.NEGATIVE_INFINITY;
                for(int k = 0; k < 4; k++) {
                    double currentValue = qTable[i][j][k];
                    if(currentValue > bestValue) {
                        bestValue = currentValue;
                        direction = k;
                    }
                }

                if(rewardMatrix.checkTerminal(new Coordinate(i, j))) {
                    System.out.print('G');
                }
                else {
                    if(direction == Agent.UP) {
                        System.out.print("^");
                    }
                    else if(direction == Agent.DOWN) {
                        System.out.print("v");
                    }
                    else if(direction == Agent.LEFT) {
                        System.out.print("<");
                    }
                    else if(direction == Agent.RIGHT) {
                        System.out.print(">");
                    }
                }



                if(j == numCols - 1) {
                    System.out.print("\n");
                }
                else {
                    System.out.print("\t");
                }




            }
        }
    }



}