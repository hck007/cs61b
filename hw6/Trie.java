import java.util.HashMap;
import java.util.Map;

public class Trie {
    private Node root = new Node(' ');

    private class Node {
        char c;
        boolean isBlue = false;
        Map<Character, Node> next = new HashMap<>();
        Node parent = null;

        private Node(char c) {
            this.c = c;
        }
    }

    public void add(String s) {
        Node currentNode = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!currentNode.next.containsKey(c)) {
                currentNode.next.put(c, new Node(c));
            }
            currentNode.next.get(c).parent = currentNode;
            currentNode = currentNode.next.get(c);
        }
        currentNode.isBlue = true;
    }

    public boolean containsWord(String s) {
        Node currentNode = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (currentNode.next == null || !currentNode.next.keySet().contains(c)) {
                return false;
            }
            currentNode = currentNode.next.get(c);
        }
        return currentNode.isBlue;
    }

    public boolean containsPrefix(String p) {
        Node currentNode = root;
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (currentNode.next == null || !currentNode.next.containsKey(c)) {
                return false;
            }
            currentNode = currentNode.next.get(c);
        }
        return true;
    }

    public String longestPrefixOf(String s) {
        Node currentNode = root;
        String current = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (currentNode.next.keySet().contains(c)) {
                current += c;
            } else {
                break;
            }
            currentNode = currentNode.next.get(c);
        }
        return current;
    }
}
