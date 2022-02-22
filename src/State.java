public class State {

    Coordinate coordinate;
    int previousAction;

    public State(Coordinate coordinate, int previousAction) {
        this.coordinate = coordinate;
        this.previousAction = previousAction;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public int getPreviousAction() {
        return this.previousAction;
    }



}
