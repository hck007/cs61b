import static org.junit.Assert.*;
import org.junit.Test;

public class TestUnionFind {
    @Test
    public void TestFind() {
        UnionFind ds = new UnionFind(9);
        ds.union(2, 3);
        ds.union(1, 2);
        ds.union(5, 7);
        ds.union(8, 4);
        ds.union(7, 2);
        ds.union(0, 6);
        ds.union(6, 4);
        ds.union(6, 3);
        int actual = ds.find(1);
        assertEquals(2, actual);

    }
    @Test
    public void TestParent() {
        UnionFind ds = new UnionFind(9);
        ds.union(2, 3);
        ds.union(1, 2);
        ds.union(5, 7);
        ds.union(8, 4);
        ds.union(7, 2);
        ds.union(0, 6);
        ds.union(6, 4);
        ds.union(6, 3);
        int actual = ds.parent(8);
        assertEquals(4, actual);
        int actual2 = ds.parent(2);
        assertEquals(-9, actual2);
    }
    @Test
    public void TestSizeOf() {
        UnionFind ds = new UnionFind(9);
        ds.union(2, 3);
        ds.union(1, 2);
        ds.union(5, 7);
        ds.union(8, 4);
        ds.union(7, 2);
        ds.union(0, 6);
        ds.union(6, 4);
        ds.union(6, 3);
        int actual = ds.sizeOf(0);
        assertEquals(4, actual);
    }
    @Test
    public void TestConnected() {
        UnionFind ds = new UnionFind(9);
        ds.union(2, 3);
        ds.union(1, 2);
        ds.union(5, 7);
        boolean actual = ds.connected(2, 7);
        assertFalse(actual);
    }
}
