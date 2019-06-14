public class LinkedListDeque <T>{
    private Node sentFront;
    private Node sentBack;
    private int size;

    public LinkedListDeque(){
        size = 0;
        sentFront = new Node(null, null, null);
        sentBack = new Node(null, null, null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
    }

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        private Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
        private T getRecursiveNode(int index){
            if (index == 0){
                return sentFront.next.item;
            }
            return sentFront.next.getRecursiveNode(index - 1);
        }
    }
    public void addFirst(T item){
        size += 1;
        Node x = new Node(null, item, null);
        x.next = sentFront.next;
        x.prev = sentFront;
        x.next.prev = x;
        x.prev.next = x;
     }
     public void addLast(T item){
        size += 1;
        Node x = new Node(null, item, null);
        x.next = sentBack;
        x.prev = sentBack.prev;
        x.prev.next = x;
        x.next.prev = x;


     }
     public boolean isEmpty(){
        return size == 0;
     }
     public int size(){
        return size;
     }
     public void printDeque(){
        for (int i = size; i > 0; i--){
            System.out.print(sentFront.next.item + " ");
            sentFront.next = sentFront.next.next;
        }
     }
     public T removeFirst(){
        if (size == 0){
            return null;
        }
        size -= 1;
        Node x = sentFront.next;
        T ANS = x.item;
        x.next.prev = sentFront;
        x.prev.next = x.next;
        return ANS;
     }
     public T removeLast(){
        if (size == 0){
            return null;
        }
        size -= 1;
        Node x = sentBack.prev;
        T ANS = x.item;
        x.next.prev = x.prev;
        x.prev.next = sentBack;
        return ANS;
     }
    public T get(int index){
        Node node = sentFront.next;
        for (int i = 0; i < index; i ++){
            node = node.next;
        }
        return node.item;
    }
    public T getRecursive(int index){
        T ANS = sentFront.getRecursiveNode(index);
        return ANS;
    }
}
