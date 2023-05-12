import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int tail;
    private int head;
    private int size;


    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        head = 0;
        tail = 0;
        size = 0;
    }

    /**
     * @return {@code true} if the current randomized queue is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items in the current randomized queue
     */
    public int size() {
        return size;
    }

    /**
     * adds new item to the randomized queue after the already added items
     *
     * @param item - the item that will be added to the randomized queue
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == queue.length) resize(2 * queue.length);
        size++;
        if (size == 1) tail = head;
        else tail++;
        if (queue.length == 0) queue = (Item[]) new Object[1];
        queue[tail] = item;
    }


    /**
     * removes random element of the randomized queue
     *
     * @return the item that was removed
     * @throws NoSuchElementException if the current stack is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniformInt(size);
        Item removedItem = queue[index];

        Item[] result = (Item[]) new Object[queue.length - 1];
        if (index == 0) result = generateArray(1, size);
        else if (index == size - 1) result = generateArray(0, size - 1);
        else {
            Item[] str1 = generateArray(0, index);
            Item[] str2 = generateArray(index + 1, size);
            System.arraycopy(str1, 0, result, 0, str1.length);
            System.arraycopy(str2, 0, result, str1.length, str2.length);

        }
        queue = result;
        size--;
        tail--;

        if (size > 0 && size == queue.length / 4) resize(queue.length / 2);
        return removedItem;

    }

    /**
     * @return random element of the randomized queue without removing it
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return queue[StdRandom.uniformInt(size)];
    }

    /**
     * returns an iterator to this deque that iterates through the items in FIFO order
     *
     * @return an iterator to this deque that iterates through the items in FIFO order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(queue);
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iteratorQueue;
        private int numOfIterations = size;


        public RandomizedQueueIterator(Item[] queue) {
            iteratorQueue = queue;
        }

        public boolean hasNext() {
            return numOfIterations > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int index = StdRandom.uniformInt(numOfIterations);
            Item itemToShow = iteratorQueue[index];

            iteratorQueue = helperMethod(index, iteratorQueue);

            numOfIterations--;
            return itemToShow;
        }

        private Item[] helperMethod(int index, Item[] inputQueue) {
            Item[] result;
            if (index == 0 && size == 1) result = (Item[]) new Object[] { inputQueue[0] };
            else result = generateArray(index, inputQueue);

            return result;
        }

        private Item[] generateArray(int index, Item[] queue) {
            Item[] result = (Item[]) new Object[queue.length - 1];
            for (int i = 0; i < index; i++) {
                result[i] = queue[i];
            }
            for (int i = index + 1; i < result.length; i++) {
                result[i - 1] = queue[i];
            }
            return result;
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
            s.append(item);
            s.append("\n");
        }

        return s.toString();
    }

    private void resize(int capasity) {
        Item[] copy = (Item[]) new Object[capasity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[head + i];
        }
        queue = copy;
        head = 0;
        tail = size - 1;
    }

    private Item[] generateArray(int startIndex, int endIndex) {
        Item[] result = (Item[]) new Object[(endIndex - startIndex)];
        for (int i = 0; i < result.length; i++) {
            result[i] = queue[startIndex + i];
        }
        return result;
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
        System.out.println(q.toString());
        System.out.println("(" + q.size() + "left on queue)");
    }


}
