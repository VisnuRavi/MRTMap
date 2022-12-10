package MRT;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMRTMap {
    @Test
    public void testGetNextStation() {
        String[] nextLine = {"a-line", "a1", "1", "a2", "2"};
        Station expectedA1 = new Station("a1", "a-line", 1);
        Station expectedA2 = new Station("a2", "a-line", 2);
        Station expectedA3 = null;
        Station resultA1 = MRTMap.getInstance().getNextStation("a-line", nextLine, 1);
        Station resultA2 = MRTMap.getInstance().getNextStation("a-line", nextLine, 3);
        Station resultA3 = MRTMap.getInstance().getNextStation("a-line", nextLine, 5);
        assertTrue(expectedA1.equals(resultA1));
        assertTrue(expectedA2.equals(resultA2));
        assertTrue(resultA3 == expectedA3);
    }

    @Test
    public void testAddToAdjListWithPrevAndNext() {
        Station prev = new Station("prev", "prev-line", 1);
        Station curr = new Station("curr", "curr-line", 2);
        Station next = new Station("next", "next-line", 3);

        MRTMap.getInstance().addToAdjList(prev, curr, next);
        List<Pair> adj = MRTMap.getInstance().getAdjList().get(curr);
        Pair p1 = new Pair(prev, 2);
        Pair p2 = new Pair(next, 3);
        for (int i=0; i<adj.size(); i++) {
            Pair p = adj.get(i);
            assertTrue(p.equals(p1) || p.equals(p2));
        }
    }

    @Test
    public void testAddtoAdjListNoPrevOrNext() {
        Station curr = new Station("curr", "curr-line", 2);
        MRTMap.getInstance().addToAdjList(null, curr, null);
        List<Pair> adj = MRTMap.getInstance().getAdjList().get(curr);
        assertTrue(adj.isEmpty());
    }

    @Test
    public void testAddToNameToStations() {
        Station aline = new Station("a", "a-line", 2);
        Station extra = new Station("b", "a-line", 3);
        Station bline = new Station("a", "b-line", 3);

        MRTMap mrtMap = MRTMap.getInstance();
        mrtMap.addToAdjList(null, aline, extra);
        mrtMap.addToNameToStations(aline);
        assertTrue(mrtMap.getNameToStations().get("a").get(0).equals(aline));

        mrtMap.addToAdjList(null, bline, null);
        mrtMap.addToNameToStations(bline);
        List<Station> sameName = mrtMap.getNameToStations().get("a");
        assertTrue(sameName.get(0).equals(aline) && sameName.get(1).equals(bline));
        List<Pair> alineAdj = mrtMap.getAdjList().get(aline);
        Pair blinep = new Pair(bline, 0);
        assertTrue(alineAdj.get(alineAdj.size()-1).equals(blinep));
        List<Pair> blineAdj = mrtMap.getAdjList().get(bline);
        Pair alinep = new Pair(aline, 0);
        assertTrue(blineAdj.get(blineAdj.size()-1).equals(alinep));
    }


}
