package main.java.Stage;

import main.java.MRT.MRTMap;

public class Executor extends Stage {
    private String firstStation;
    private String secondStation;

    private static Executor executor;

    private Executor() {}

    public static Executor getInstance() {
        if (executor == null) {
            executor = new Executor();
        }
        return executor;
    }

    public void setStations(String firstStation, String secondStation) {
        this.firstStation = firstStation;
        this.secondStation = secondStation;
    }

    public void execute() {
        MRTMap.getInstance().findShortestPath(firstStation, secondStation);
    }
}
