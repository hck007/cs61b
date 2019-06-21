import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        boolean actual = offByOne.equalChars('a', 'b');
        assertTrue(actual);
        boolean actual2 = offByOne.equalChars('d', 'd');
        assertFalse(actual2);
        boolean actual3 = offByOne.equalChars('%', '&');
        assertTrue(actual3);
        boolean actual4 = offByOne.equalChars('A', 'b');
        assertFalse(actual4);
    }

}