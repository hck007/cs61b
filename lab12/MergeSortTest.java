import edu.princeton.cs.algs4.Queue;

public class MergeSortTest {
    public static void TestMakesingleItemQueues() {
        Queue<String> items = new Queue<>();
        items.enqueue("Alice");
        items.enqueue("Bob");
        items.enqueue("Ceily");
        items.enqueue("David");
        Queue<Queue<String>> ans = MergeSort.makeSingleItemQueues(items);
        for (Queue<String> q : ans) {
            System.out.println(q);
        }
    }

    public static void TestMergeSortedQueues() {
        Queue<String> q1 = new Queue<>();
        q1.enqueue("Alice");
        q1.enqueue("Vena");
        Queue<String> q2 = new Queue<>();
        q2.enqueue("Ethan");
        Queue<String> ans = MergeSort.mergeSortedQueues(q1, q2);
        for (String s : ans) {
            System.out.println(s);
        }
    }

    public static void TestAll() {
        Queue<String> q = new Queue<>();
        q.enqueue("apple");
        q.enqueue("zebra");
        q.enqueue("bob");
        q.enqueue("erskine");
        Queue<String> ans = MergeSort.mergeSort(q);
        for (String i : ans) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        //TestMakesingleItemQueues();
        //TestMergeSortedQueues();
        TestAll();
    }
}
