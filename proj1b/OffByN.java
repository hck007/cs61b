public class OffByN implements CharacterComparator {
    private int n;
    public OffByN(int N) {
        n = N;
    }
    public boolean equalChars(char x, char y) {
        int difference = x - y;
        if (Math.abs(difference) == n) {
            return true;
        }
        return false;
    }
}
