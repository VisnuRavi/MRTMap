package MRT;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MRTMap implements PathFinder {
    //map each key stn to a list of pair of stn and the weight that links to key station
    static Map<Station, List<Pair>> adjList;
    //map each stn name to the list of stations that have the same name(but different lines)
    static Map<String, List<Station>> nameToStations;

    private MRTMap() {}

    private static MRTMap mrtmap;

    public Map<Station, List<Pair>> getAdjList() {
        return adjList;
    }

    public Map<String, List<Station>> getNameToStations() {
        return nameToStations;
    }

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
                    addToAdjList(prev, curr, next);
                    addToNameToStations(curr);
                }
            }
            fileReader.close();
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Station getNextStation(String stationLine, String[] nextLine, int index) {
        if (index >= nextLine.length) {
            return null;
        }
        String nextName = nextLine[index];
        int nextDuration = Integer.parseInt(nextLine[index + 1]);
        return new StationBuilder()
                .setName(nextName)
                .setDuration(nextDuration)
                .setLine(stationLine)
                .getStation();
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

    //if have 1 station name linked to many lines
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

    public void findShortestPath(Station firstStation, Station secondStation) {
        Map<Station, Station> parent = new HashMap<>();
        Map<Station, Pair> shortestPathEst = new HashMap<>();
        PriorityQueue<Pair> shortestPathPQ = new PriorityQueue<>(
                (p1, p2) -> {
                    if (p1.duration < p2.duration) {
                        return -1;
                    } else if (p1.duration == p2.duration) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
        );
        //init the PQ
        for (Map.Entry<Station, List<Pair>> set : adjList.entrySet()) {
            Pair p = null;
            if (set.getKey().equals(firstStation)) {
                p = new Pair(set.getKey(), 0);
            } else {
                p = new Pair(set.getKey(), Integer.MAX_VALUE);
            }
            shortestPathPQ.add(p);
            shortestPathEst.put(set.getKey(), p);
        }

        while (!shortestPathPQ.isEmpty()) {
            Pair p = shortestPathPQ.poll();
            Station shortestStation = p.station;
            int shortestDuration = p.duration;

            List<Pair> adj = adjList.get(shortestStation);
            for (int i=0; i<adj.size(); i++) {
                Pair adjPair = adj.get(i);
                Station adjStn = adjPair.station;
                int adjDur = adjPair.duration;

                Pair adjCurrPair = shortestPathEst.get(adjStn);
                int adjCurrDur = adjCurrPair.duration;
                if (shortestDuration + adjDur < adjCurrDur) {
                    shortestPathPQ.remove(adjCurrPair);
                    Pair relaxedP = new Pair(adjStn, shortestDuration + adjDur);
                    shortestPathEst.put(adjStn, relaxedP);
                    shortestPathPQ.add(relaxedP);
                    parent.put(adjStn, shortestStation);
                }
            }
        }

        //need to map to exact same obj
        Station actualSecondStation = null;
        List<Station> secondNameStations = nameToStations.get(secondStation.name);
        for (int i=0; i<secondNameStations.size(); i++) {
            if (secondStation.equals(secondNameStations.get(i))) {
                actualSecondStation = secondNameStations.get(i);
            }
        }
        int totalShortestDuration = shortestPathEst.get(actualSecondStation).duration;
        List<Station> path = new ArrayList<>();
        Station currStation = null;
        Station parentStation = actualSecondStation;
        while (currStation == null || !currStation.equals(firstStation)) {
            path.add(parentStation);
            currStation = parentStation;
            parentStation = parent.get(currStation);
        }

        System.out.println("Total duration: " + totalShortestDuration);
        for (int j=path.size()-1; j>=0; j--) {
            Station stn = path.get(j);
            System.out.println("Station: " + stn.name + " line: " + stn.line);
        }
    }
}
