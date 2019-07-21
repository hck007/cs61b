import edu.princeton.cs.algs4.Queue;

public class QuickSortTest {
    public static void TestSort() {
        Queue<Integer> q1 = new Queue<>();
        q1.enqueue(2);
        q1.enqueue(1);
        q1.enqueue(5);
        q1.enqueue(3);
        q1.enqueue(4);
        Queue<Integer> ans = QuickSort.quickSort(q1);
        for (int i : ans) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        TestSort();
    }
}
