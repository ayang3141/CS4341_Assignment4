import java.io.IOException;
import java.util.Random;

public class Program {

    public static void main(String[] args) throws IOException {
        // declare the argument variables
        final String fileName = args[0]; // args[0] is the file name
        final int MAXRUNTIME = Integer.parseInt(args[1]); // args[1] is how long the program will run
        final double intendedProb = Double.parseDouble(args[2]); // args[2] is the prob of moving in the "correct" direction
        final double actionReward = Double.parseDouble(args[3]); // args[3] is the constant reward for each action


        // initialize important variables
        double epsilon = 0.1;
        Random random = new Random();
        double stepSize = 0.1;
        double discount = 0.9;

//        System.out.println(fileName);
//        System.out.println(MAXRUNTIME);
//        System.out.println(intendedProb);
//        System.out.println(actionReward);



        // scan the board text file and generate the reward matrix
        RewardMatrix rewardMatrix = new RewardMatrix(fileName);
        rewardMatrix.generateBoard(actionReward);
        Agent agent = new Agent(rewardMatrix);


        // initialize Q learning table (same dimensions as map)
        int numRows = rewardMatrix.getNumRows();
        int numCols = rewardMatrix.getNumCols();
        double[][][] qTable = new double[numRows][numCols][4];


        // ----- Q LEARNING ALGORITHM STARTS -------

        boolean TIME_LEFT = true;   // if there is still time left
        long startTime = System.nanoTime(); // get the starting time

        while(TIME_LEFT) { // while there is still time left
            // start at a random location on the board
            Coordinate current = rewardMatrix.generateStartPoint();

            while(TIME_LEFT) { // loop until a terminal state has been found or time runs out

                // if the current coordinate (or state) is a terminal state
                if(rewardMatrix.checkTerminal(current)) {
                    // set the terminal state to its respective reward
                    qTable[current.getX()][current.getY()][0] = rewardMatrix.getRewardValue(current);
                    qTable[current.getX()][current.getY()][1] = rewardMatrix.getRewardValue(current);
                    qTable[current.getX()][current.getY()][2] = rewardMatrix.getRewardValue(current);
                    qTable[current.getX()][current.getY()][3] = rewardMatrix.getRewardValue(current);

//                    System.out.println("Terminal State Found");
//                    agent.printQPolicy(qTable);
//                    System.out.println("\n");
                    break;
                }

                // choose next action from epsilon greedy policy;
                int action = agent.nextAction(qTable, current, epsilon);


                // take the action and get next state s'
                Coordinate newState = agent.getNextState(current, action, intendedProb);


                // update current state in Q learning table
                // MAX_{new_action} (Q(new_state, new_action)) = MAX(Q(new_state, new_action1), Q(new_state, new_action2), ...
                double oldValue = qTable[current.getX()][current.getY()][action];
                double maxNextActionScore = Math.max(Math.max(qTable[newState.getX()][newState.getY()][Agent.UP], qTable[newState.getX()][newState.getY()][Agent.DOWN]),
                        Math.max(qTable[newState.getX()][newState.getY()][Agent.LEFT], qTable[newState.getX()][newState.getY()][Agent.RIGHT]));
                double newValue = oldValue + stepSize * (actionReward + rewardMatrix.getRewardValue(newState) + discount * maxNextActionScore - oldValue);
                qTable[current.getX()][current.getY()][action] = newValue;


//                String prevAction = "";
//                if(action == Agent.UP) {
//                    prevAction = "UP";
//                }
//                else if(action == Agent.DOWN) {
//                    prevAction = "DOWN";
//                }
//                else if(action == Agent.LEFT) {
//                    prevAction = "LEFT";
//                }
//                else if(action == Agent.RIGHT) {
//                    prevAction = "RIGHT";
//                }
//                System.out.println("value for previous state " + current + " and action " + prevAction + " is " + newValue);

                // set old state to new state
                current = newState;

//                iterationNum++;
                // checks if there is still time left
                TIME_LEFT = (System.nanoTime() - startTime)/(1000000000) <= MAXRUNTIME;
            } // end of terminal state while loop




            // checks if there is still time left
            TIME_LEFT = (System.nanoTime() - startTime)/(1000000000) <= MAXRUNTIME;
        } // end of time left while loop

        System.out.println("Q Learning Algorithm Successfully Ended");

        /*
            after completed, print out the Q-Learning Policy:
            UP = ^
            DOWN = v
            LEFT = <
            RIGHT = >
         */
        agent.printQPolicy(qTable);
    }




}
