package es.datastructur.synthesizer;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        double capacity = Math.round((double) SR / frequency);
        buffer = new ArrayRingBuffer<Double>((int) capacity);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0.0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        for (int i = 0; i < buffer.capacity(); i++) {
            double r = Math.random() - 0.5;
            buffer.dequeue();
            buffer.enqueue(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {

        double itemRemoved = buffer.dequeue();
        double firstItem = buffer.peek();
        double itemEnqueued = (itemRemoved + firstItem) * DECAY / 2;
        buffer.enqueue(itemEnqueued);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {

        double itemReturned = buffer.peek();
        return itemReturned;
    }
}

