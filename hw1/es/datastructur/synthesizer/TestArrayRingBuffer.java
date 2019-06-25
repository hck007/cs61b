package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTests() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        boolean actual = arb.isEmpty();
        assertTrue(actual);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        Object actual2 = arb.dequeue();
        assertEquals(1, actual2);
        arb.enqueue(4);
        Object actual3 = arb.peek();
        assertEquals(2, actual3);
        arb.enqueue(5);
        arb.enqueue(6);
        assertTrue(arb.isFull());
    }
    @Test
    public void TestIterator() {
        ArrayRingBuffer arb = new ArrayRingBuffer(5);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.dequeue();
        arb.enqueue(5);
        for (Object item: arb) {
            System.out.println(item);
        }
    }
    @Test
    public void TestEquals() {
        ArrayRingBuffer arb1 = new ArrayRingBuffer(3);
        arb1.enqueue(0);
        arb1.enqueue(1);
        arb1.enqueue(2);
        arb1.dequeue();
        arb1.enqueue(3);
        ArrayRingBuffer arb2 = new ArrayRingBuffer(3);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);
        boolean actual = arb1.equals(arb2);
        assertTrue(actual);
    }
}
