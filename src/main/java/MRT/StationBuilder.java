package MRT;

public class StationBuilder {
    String name;
    String line;
    int duration;

    public StationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StationBuilder setLine(String line) {
        this.line = line;
        return this;
    }

    public StationBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Station getStation() {
        return new Station(name, line, duration);
    }
}
