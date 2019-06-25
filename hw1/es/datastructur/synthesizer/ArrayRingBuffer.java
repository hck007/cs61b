package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        fillCount++;
        if (fillCount > this.capacity()) {
            throw new RuntimeException();
        }
        last = Math.floorMod(fillCount + first - 1, this.capacity());
        rb[last] = x;

    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        fillCount -= 1;
        if (fillCount < 0) {
            throw new RuntimeException();
        }
        T returnItem = rb[first];
        first = Math.floorMod(first + 1, this.capacity());
        return returnItem;

    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        return rb[first];
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (this.fillCount != other.fillCount) {
            return false;
        }
        Iterator<T> a = this.iterator();
        Iterator<T> b = other.iterator();
        while (a.hasNext()) {
            if (a.next() != b.next()) {
                return false;
            }
        }
        return true;
    }
    /**
     * implement Iterator class
     */
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private int count;

        public ArrayRingBufferIterator() {
            pos = first;
            count = 0;
        }

        public boolean hasNext() {
            return count < fillCount;
        }

        public T next() {
            count++;
            T itemReturned = rb[pos];
            pos++;
            pos = Math.floorMod(pos, capacity());
            return itemReturned;
        }

    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.

}
    // TODO: Remove all comments that say TODO when you're done.
