import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        if (args.length == 0) throw new IllegalArgumentException(
                "You must provide integer argument to this program.");
        int n = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randomizedQueue.enqueue(item);
        }
        while (n > 0) {
            System.out.println(randomizedQueue.dequeue());
            n--;
        }
    }
}