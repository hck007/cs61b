import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        private Node(K key, V value) {
            this.key = key;
            this.val = value;
            left = null;
            right = null;
            size = 1;
        }
    }

    public BSTMap() {
        root = null;
    }

    private int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (n == null) {
            return null;
        }
        while (n != null) {
            int cmp = key.compareTo(n.key);
            if (cmp < 0) {
                n = n.left;
            } else if (cmp > 0) {
                n = n.right;
            } else {
                return n.val;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node n ,K key, V value) {
        if (n == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = put(n.left, key, value);
        } else if (cmp > 0) {
            n.right = put(n.right, key, value);
        } else {
            n.val = value;
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();

    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V itemReturned = get(key);
        root = remove(root, key);
        return itemReturned;
    }

    private void removeMin() {
        root = removeMin(root);
    }

    private Node removeMin(Node n) {
        if (n.left == null) {
            return n.right;
        }
        n.left = removeMin(n.left);
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    private Node remove(Node n, K key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = remove(n.left, key);
        } else if (cmp > 0) {
            n.right = remove(n.right, key);
        } else {
            if (n.right == null) {
                return n.left;
            }
            if (n.left == null) {
                return n.right;
            }
            Node t = n;
            n = min(t.right);
            n.right = removeMin(t.right);
            n.left = t.left;
        }
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    public K min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();

    }

    private void printNode(Node n) {
        System.out.print(n.key);
        System.out.println(n.val);
    }

    private void printInOrder(Node n) {
        if (n.left == null && n.right == null) {
            printNode(n);
        } else if (n.left == null) {
            printNode(n);
            printInOrder(n.right);
        } else if (n.right == null) {
            printNode(n);
            printInOrder(n.left);
        } else {
            printInOrder(n.left);
            printNode(n);
            printInOrder(n.right);
        }

    }

    public void printInOrder() {
        printInOrder(root);
    }
}


