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

        private Node(Node prev, T item, Node next) {
            prev = prev;
            item = item;
            next = next;
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
        sentFront.next = x;

     }
     public void addLast(T item){
        size += 1;
        Node x = new Node(null, item, null);
        sentBack.prev = x;

     }
     public boolean isEmpty(){
        return size == 0;
     }
     public int size(){
        return size;
     }
     public void printDeque(){
        while (sentFront.next != null){
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
        sentFront.next = sentFront.next.next;
        return x.item;
     }
     public T removeLast(){
        if (size == 0){
            return null;
        }
        size -= 1;
        Node x = sentBack.prev;
        sentBack.prev.prev = sentBack.prev;
        return x.item;
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
