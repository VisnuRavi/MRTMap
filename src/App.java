import MRT.MRTMap;
import Stage.Parser;
import Stage.Stage;

import java.io.File;
import java.util.Scanner;

public class App {
    String mrtDataPath = "/home/visnu/Documents/MRTPath/input/data.csv";

    public void run() {
        initialiseMRTMap();
        String input = getInput();
        getShortestPath(input);
    }

    private String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private void getShortestPath(String input) {
        Parser parser = Parser.getInstance();
        parser.setInput(input);
        parser.execute();
    }

    private void initialiseMRTMap() {
        File file = new File(mrtDataPath);
        MRTMap mrtMap = MRTMap.getInstance();
        mrtMap.initStations(file);
    }
}
