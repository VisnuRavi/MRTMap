package MRT;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MRTMap implements PathFinder {
    static Map<Station, List<Pair>> adjList;

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
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextLine;

            while (csvReader.peek() != null) {
                nextLine = csvReader.readNext();
                String stationLine = nextLine[0];

                Station prev = null;
                Station curr = null;
                Station next = null;
                next = getNextStation(stationLine, nextLine, 1);

                for (int i=1; i<nextLine.length; i=i+2) {
                    prev = curr;
                    curr = next;
                    if (i < nextLine.length - 2) {
                        next = getNextStation(stationLine, nextLine, i+2);
                    } else {
                        next = null;
                    }
                }
                addToAdjList(prev, curr, next);
                addToNameToStations(curr);
            }
            fileReader.close();
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Station getNextStation(String stationLine, String[] nextLine, int index) {
        String nextName = nextLine[index];
        int nextDuration = Integer.parseInt(nextLine[index + 1]);
        StationBuilder nextStationB = new StationBuilder()
                .setName(nextName)
                .setDuration(nextDuration)
                .setLine(stationLine);
        return nextStationB.getStation();
    }

    public void addToAdjList(Station prev, Station curr, Station next) {
        List<Pair> adj = new ArrayList<>();
        if (prev != null) {
            Pair p = new Pair(prev, curr.duration);
            adj.add(p);
        }

        if (next != null) {
            Pair p = new Pair(next, next.duration);
            adj.add(p);
        }
        adjList.put(curr, adj);
    }

    public void addToNameToStations(Station curr) {
        String name = curr.name;
        if (nameToStations.containsKey(name)) {
            List<Station> stations = nameToStations.get(name);
            List<Pair> adjCurr = adjList.get(curr);
            for (int i=0; i<stations.size(); i++) {
                Station existingStn = stations.get(i);
                List<Pair> adjExisting = adjList.get(existingStn);
                adjExisting.add(new Pair(curr, 0));

                adjCurr.add(new Pair(existingStn, 0));
            }
            stations.add(curr);
        } else {
            List<Station> stations = new ArrayList<>();
            stations.add(curr);
            nameToStations.put(name, stations);
        }
    }

    public void findShortestPath(String firstStation, String secondStation) {

    }
}
