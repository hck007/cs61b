public class TestTrie {
    public static void Test() {
        Trie t = new Trie();
        t.add("sam");
        t.add("sad");
        t.add("sap");
        t.add("same");
        System.out.println(t.containsWord("same"));
        System.out.println(t.longestPrefixOf("sample"));
        System.out.println(t.containsWord("s"));
        System.out.println(t.containsPrefix("sa"));
        System.out.println(t.containsPrefix("same"));
        System.out.println(t.containsPrefix("s"));
    }


    public static void main(String[] args) {
        Test();
    }
}
