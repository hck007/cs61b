package synthesizer;

import java.util.Iterator;





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







}
