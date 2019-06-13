public class ArrayDequeTest {
    public static void main(String[] args){
        ArrayDeque L = new ArrayDeque();
        L.addFirst(2); // 8, 2, 3, 4, 5, 6, 7, 9
        L.addLast(3);
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);
        L.addLast(7);
        L.addFirst(8);
        L.addLast(9);
        L.addLast(10);
        L.addFirst(11);
        System.out.println(L.removeFirst());
        L.printDeque();
    }
}
