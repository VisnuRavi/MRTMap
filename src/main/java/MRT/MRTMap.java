package main.java.MRT;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MRTMap implements PathFinder {
    static Map<Station, List<Station>> adjList;

    static Map<String, List<Station>> nameToStations;

    private MRTMap() {}

    private static MRTMap mrtmap;

    public static MRTMap getInstance() {
        if (mrtmap == null) {
            mrtmap = new MRTMap();
            adjList = new HashMap<>();
            nameToStations = new HashMap<>();
        }
        return mrtmap;
    }

    public void initStations(File file) {

    }

    public void findShortestPath(String firstStation, String secondStation) {

    }
}
