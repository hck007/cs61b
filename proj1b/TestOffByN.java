import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {
    static CharacterComparator offByN = new OffByN(5);
    @Test
    public void testOffByN() {
        boolean actual = offByN.equalChars(' ', 'a');
        assertFalse(actual);
        boolean actual2 = offByN.equalChars('a', 'f');
        assertTrue(actual2);
        boolean actual3 = offByN.equalChars('a', 'a');
        assertFalse(actual3);

    }
}
