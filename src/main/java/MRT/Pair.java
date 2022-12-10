package MRT;

public class Pair {
    Station station;
    int duration;

    public Pair (Station station, int duration) {
        this.station = station;
        this.duration = duration;
    }

    public boolean equals(Pair p) {
        return p.station.equals(this.station) && p.duration == this.duration;
    }
}
