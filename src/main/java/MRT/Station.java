package MRT;

//station helps with the hashing of the obj
public class Station {
    String name;
    String line;
    int duration;

    public Station(String name, String line, int duration) {
        this.name = name;
        this.line = line;
        this.duration = duration;
    }
}
