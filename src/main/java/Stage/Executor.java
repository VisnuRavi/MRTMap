package Stage;

import MRT.MRTMap;
import MRT.Station;

public class Executor extends Stage {
    private Station firstStation;
    private Station secondStation;

    private static Executor executor;

    private Executor() {}

    public static Executor getInstance() {
        if (executor == null) {
            executor = new Executor();
        }
        return executor;
    }

    public void setStations(Station firstStation, Station secondStation) {
        this.firstStation = firstStation;
        this.secondStation = secondStation;
    }

    public void execute() {
        MRTMap.getInstance().findShortestPath(firstStation, secondStation);
    }
}
