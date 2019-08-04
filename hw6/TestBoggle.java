import java.util.List;
import java.util.TreeSet;

public class TestBoggle {
    public static void Test() {
       List<String> ans = Boggle.solve(7, "exampleBoard.txt");
        for (String s : ans) {
            System.out.print(s + " ");
        }

    }

    public static void main(String[] args) {
        Test();
    }
}
