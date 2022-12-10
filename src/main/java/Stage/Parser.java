package Stage;

import MRT.Station;
import MRT.StationBuilder;

public class Parser extends Stage {
    private static Parser parser;

    private String input;

    private Parser() {}

    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
            Executor executor = Executor.getInstance();
            parser.setNextStage(executor);
        }
        return parser;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void execute() {
        String[] splitInput = input.split(",");
        String firstStationName = splitInput[0];
        String firstStationLine = splitInput[1];
        String secondStationName = splitInput[2];
        String secondStationLine = splitInput[3];
        Station firstStation = getStation(firstStationName, firstStationLine);
        Station secondStation = getStation(secondStationName, secondStationLine);
        Executor.getInstance().setStations(firstStation, secondStation);
        this.nextStage.proceed();
    }

    public Station getStation(String name, String line) {
        return new StationBuilder().setLine(line).setName(name).getStation();
    }
}
