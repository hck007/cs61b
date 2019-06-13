public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int startingIndex;

    public ArrayDeque(){
        size = 0;
        items = (T[]) new Object[8];
        startingIndex = 0;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public void printDeque(){
        for (T item: items){
            System.out.print(item + " ");
        }
    }
    public int size(){
        return size;
    }

    public void resize(int capacity){
        T[] temp = (T[]) new Object[2 * capacity];
        for (int i = 0; i < size; i ++){
            temp[i] = items[(i + startingIndex) % items.length];
        }
        items = temp;
        startingIndex = 0;
    }

    public void addFirst(T item){
        if (size + 1 > items.length){
            resize(size);
            items[items.length - 1] = item;
            startingIndex = items.length - 1;
        }else if(startingIndex - 1 < 0){
            startingIndex = items.length - 1;
            items[startingIndex] = item;
        }else{
            startingIndex -= 1;
            items[startingIndex] = item;
        }
        size ++;


    }
    public void addLast(T item){
        if (size + 1 > items.length){
            resize(size);
            items[startingIndex + size] = item;
        }else if (startingIndex + size > items.length){
            items[(startingIndex + size) % items.length] = item;
        }else{
            items[startingIndex + size] = item;
        }
        size ++;
    }
    public T removeFirst(){
        T ANS = items[startingIndex % items.length];
        startingIndex = (startingIndex + 1) % items.length;
        items[startingIndex] = null;
        size --;
        double RUsage = size / items.length;
        if (RUsage < 0.25){
            resize(size);
        }
        return ANS;


    }
    public T removeLast(){
        T ANS = items[(startingIndex + size) % items.length];
        items[(startingIndex + size) % items.length] = null;
        size --;
        double RUsage = size / items.length;
        if (RUsage < 0.25){
            resize(size);
        }
        return ANS;
    }
    public T get(int index){
        T ANS = items[(startingIndex + index) % items.length];
        return ANS;
    }
    }


