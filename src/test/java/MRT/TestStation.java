package MRT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStation {
    @Test
    public void testEquals() {
        Station s1 = new Station("name", "line", 1);
        Station s2 = new Station("name", "line", 2);
        Station s3 = new Station("wrongname", "line", 1);
        Station s4 = new Station("name", "wrongline", 1);

        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(s3));
        assertFalse(s1.equals(s4));
    }
}
