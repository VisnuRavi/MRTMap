package Stage;

import MRT.Station;
import MRT.StationBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestParser {
    @Test
    public void testGetStation() {
        String name = "name";
        String line = "line";
        Station expectedStation = new StationBuilder().setName(name).setLine(line).getStation();
        Station resultStation = Parser.getInstance().getStation(name, line);
        assertTrue(expectedStation.equals(resultStation));
    }
}
