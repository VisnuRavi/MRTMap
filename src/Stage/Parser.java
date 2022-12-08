package Stage;

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
        String firstStation = splitInput[0];
        String secondStation = splitInput[1];
        Executor.getInstance().setStations(firstStation, secondStation);
        this.nextStage.proceed();
    }
}
