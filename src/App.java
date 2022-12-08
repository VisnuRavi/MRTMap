import Stage.Parser;
import Stage.Stage;

import java.util.Scanner;

public class App {
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
        //get data from file and populate the stations and mrtmap
    }
}
