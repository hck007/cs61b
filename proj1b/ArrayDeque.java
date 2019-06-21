public class ArrayDeque<T> implements Deque<T> {

    private T[] items;

    private int size;

    private int startingIndex;



    public ArrayDeque() {

        size = 0;

        items = (T[]) new Object[8];

        startingIndex = 0;

    }
    /*
    public boolean isEmpty(){

        return size == 0;

    }*/


    @Override
    public void printDeque() {

        for (int i = startingIndex; i < startingIndex + size; i++) {

            if (items[i % items.length] != null) {

                System.out.print(items[i % items.length] + " ");

            }

        }

    }
    @Override
    public int size() {

        return size;

    }



    private void resize(int capacity) {

        T[] temp = (T[]) new Object[2 * capacity];

        for (int i = 0; i < size; i++) {

            temp[i] = items[(i + startingIndex) % items.length];

        }

        items = temp;

        startingIndex = 0;

    }


    @Override
    public void addFirst(T item) {

        if (size + 1 > items.length) {

            resize(size);

            items[items.length - 1] = item;

            startingIndex = items.length - 1;

        } else {

            startingIndex = Math.floorMod((startingIndex - 1), items.length);

            items[startingIndex % items.length] = item;

        }

        size++;





    }
    @Override
    public void addLast(T item) {

        if (size + 1 > items.length) {

            resize(size);

            items[startingIndex + size] = item;

        } else {

            items[(startingIndex + size) % items.length] = item;

        }



        size++;

    }
    @Override
    public T removeFirst() {

        T ANS = items[startingIndex % items.length];

        //startingIndex = (startingIndex + 1) % items.length;

        items[startingIndex] = null;

        startingIndex = (startingIndex + 1) % items.length;

        if (size > 0) {

            size--;
        }

        double RUsage = (double) size / (double) items.length;

        if (RUsage < 0.25 && items.length > 16) {

            resize(size);

        }

        return ANS;





    }
    @Override
    public T removeLast() {

        int finalIndex = Math.floorMod((startingIndex + size - 1), items.length);

        T ANS = items[finalIndex];

        items[finalIndex] = null;

        if (size > 0) {

            size--;
        }

        double RUsage = (double) size / (double) items.length;

        if (RUsage < 0.25 && items.length > 16) {

            resize(size);

        }

        return ANS;

    }
    @Override
    public T get(int index) {

        T ANS = items[(startingIndex + index) % items.length];

        return ANS;

    }

}

