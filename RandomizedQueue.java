import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ASSESSMENT SUMMARY
 * <p>
 * Compilation:  PASSED (0 errors, 7 warnings)
 * API:          PASSED
 * <p>
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   PASSED
 * <p>
 * Correctness:  49/49 tests passed
 * Memory:       128/129 tests passed
 * Timing:       165/193 tests passed
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] queue;
    private int size;


    /**
     * Construct an empty randomized queue.
     */
    public RandomizedQueue() {
        queue = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
    }

    /**
     * Check if the randomized queue is empty.
     *
     * @return {@code true} if the current randomized queue is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the size of the randomized queue.
     *
     * @return the number of items in the current randomized queue
     */
    public int size() {
        return size;
    }

    /**
     * Add new item to the randomized queue after the already added items.
     *
     * @param item - the item that will be added to the randomized queue
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == queue.length) resize(2 * queue.length);

        if (queue.length == 0) queue = (Item[]) new Object[INIT_CAPACITY];
        queue[size++] = item;
    }

    private void resize(int capasity) {
        Item[] copy = (Item[]) new Object[capasity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    /**
     * Remove random element from the randomized queue.
     *
     * @return the item that was removed
     * @throws NoSuchElementException if the current stack is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniformInt(size);
        Item removedItem = queue[index];
        queue = updateInputQueueAfterRandomRemoval(index, queue);
        size--;

        if (size > 0 && size == queue.length / 4) resize(queue.length / 2);
        return removedItem;
    }

    private Item[] updateInputQueueAfterRandomRemoval(int index, Item[] inputQueue) {
        Item[] result = (Item[]) new Object[inputQueue.length - 1];

        if (index == 0) {
            System.arraycopy(inputQueue, index + 1, result, 0, result.length);

        }
        else if (index < inputQueue.length - 1) {
            Item[] tmp1 = (Item[]) new Object[index];
            System.arraycopy(inputQueue, 0, tmp1, 0, tmp1.length);
            Item[] tmp2 = (Item[]) new Object[inputQueue.length - (index + 1)];
            System.arraycopy(inputQueue, index + 1, tmp2, 0, tmp2.length);

            System.arraycopy(tmp1, 0, result, 0, tmp1.length);
            System.arraycopy(tmp2, 0, result, tmp1.length, tmp2.length);
        }
        else {
            Item[] tmp = (Item[]) new Object[index];
            System.arraycopy(inputQueue, 0, tmp, 0, tmp.length);
            result = tmp;
        }

        return result;

    }

    /**
     * Return random element from the randomized queue without removing it.
     *
     * @return random element from the randomized queue
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return queue[StdRandom.uniformInt(size)];
    }

    /**
     * Return an iterator to this deque that iterates through the items in FIFO order.
     *
     * @return an iterator to this deque that iterates through the items in FIFO order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(queue);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randomizedQueue;
        private int numOfIterations = size;


        public RandomizedQueueIterator(Item[] input) {
            randomizedQueue = input;
        }

        public boolean hasNext() {
            return numOfIterations > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int index = StdRandom.uniformInt(numOfIterations);
            Item itemToShow = randomizedQueue[index];
            randomizedQueue = updateInputQueueAfterRandomRemoval(index, randomizedQueue);
            numOfIterations--;
            return itemToShow;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * returns a string representation of the deque
     *
     * @return the sequence of items in this deque in FIFO order, separated by new lines
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            if (item != null) {
                s.append(item);
                s.append("\n");
            }
        }
        return s.toString();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                q.enqueue(item);
            }
            else if (!q.isEmpty()) {
                StdOut.println(q.dequeue());
                System.out.println("(" + q.size() + "left on queue)");
            }
        }
        for (String str : q) {
            System.out.println(str);
            System.out.println("---");
        }
        System.out.println("(" + q.size() + "left on queue)");

    }
}
