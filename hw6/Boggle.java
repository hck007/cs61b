import java.util.*;

public class Boggle {
    
    // File path of dictionary file
    private static String dictPath = "words.txt";
    //private static Trie dict = new Trie();
    private static HashSet<String> allWords = new HashSet<>();
    private static Board b;
    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        Trie dict = new Trie();
        int height = new In(dictPath).readAllLines().length;
        In in = new In(dictPath);
        for (int i = 0; i < height; i++) {
            String s = in.readLine();
            dict.add(s);
        }
        b = new Board(boardFilePath);;
        getAllWords(dict);
        List<String> set = new ArrayList<>(allWords);
        set.sort(new stringComparator());
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (i >= set.size()) {
                return ans;
            } else {
                ans.add(set.get(i));
            }
        }
        return ans;
    }


    /**
     * Use DFS to explore each node in the Board b
     *
     */
    private static void getAllWords(Trie dict) {
        List<List<Integer>> adj = b.getAdj();
        for (int i = 0; i < adj.size(); i++) {
            boolean[] marked = new boolean[adj.size()];
            String word ="";
            DFS(adj, dict, i, marked, word);
        }
    }

    private static void DFS(List<List<Integer>> adj, Trie dict, int source, boolean[] marked, String word) {
        if (marked[source]) {
            return;
        }
        String letter = Character.toString(b.getChar(source));
        marked[source] = true;
        if (dict.containsPrefix(letter)) {
            word += letter;
            if (dict.containsWord(word)) {
                allWords.add(word);
            }
            for (int w : adj.get(source)) {
                DFS(adj, dict, w, marked, word);
            }
            marked[source] = false;
        }
    }



    public static class stringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            if (s2.length() == s1.length()) {
                for (int i = 0; i < s1.length(); i++) {
                    if (s2.charAt(i) != s1.charAt(i)) {
                        return s1.charAt(i) - s2.charAt(i);
                    }
                }
            }
            return s2.length() - s1.length();
        }
    }
}
