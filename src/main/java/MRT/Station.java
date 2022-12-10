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

    public boolean equals(Station s) {
        if (s.name.equals(this.name) && s.line.equals(this.line)) {
            return true;
        }
        return false;
    }
}
