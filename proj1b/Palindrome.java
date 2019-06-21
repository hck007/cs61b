public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque aList = new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            aList.addLast(letter);
        }
        return aList;
    }
    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }
        if (word.charAt(0) != word.charAt(word.length() - 1)) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - i - 1))) {
                return false;
            }
        }
        return true;
    }


}
