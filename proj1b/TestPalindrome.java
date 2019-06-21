import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testisPalindrome() {
        boolean actual1 = palindrome.isPalindrome("hello");
        assertFalse(actual1);
        boolean actual2 = palindrome.isPalindrome("Able saw I I was elbA");
        assertTrue(actual2);

    }
    @Test
    public void testisoboPalindrome() {
        CharacterComparator cc = new OffByOne();
        boolean actual1 = palindrome.isPalindrome("flake", cc);
        assertTrue(actual1);

    }
}