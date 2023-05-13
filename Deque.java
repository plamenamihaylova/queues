import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code Deque} class represents a double-ended queue of generic items.
 * It supports adding and removing items from either the front or the back of the data structure:
 * <em>addFirst</em>, <em>removeFirst</em>, <em>addLast</em>, and <em>removeLast</em>, along with
 * methods
 * for testing if the stack is empty, what is the sie of the deque,
 * and iterating through the items in FIFO order.
 * <p>
 * This implementation uses a doubly linked list with a static nested class for
 * linked-list nodes.
 * <p>
 *
 * @param <Item> the generic type each item in this stack
 */

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;


    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    /**
     * Construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Check if the deque is empty.
     *
     * @return {@code true} if the current stack is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Return the sie of the deque.
     *
     * @return the number of items in the current deque
     */
    public int size() {
        return size;
    }

    /**
     * Add new item to the front of the deque.
     *
     * @param item - the item that will be added to the deque
     * @trows IllegalArgumentException if the param is null
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node previousFirst = first;

        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = previousFirst;

        if (size == 0) last = first;
        else previousFirst.previous = first;
        size++;
    }

    /**
     * Add new item at the back of the deque.
     *
     * @param item - the item that will be added to the deque
     * @trows IllegalArgumentException if the param is null
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node previousLast = last;

        last = new Node();
        last.item = item;
        last.previous = previousLast;
        if (size == 0) first = last;
        else previousLast.next = last;
        size++;
    }

    /**
     * Remove the first element of the deque.
     *
     * @return the first item in the deque
     * @throws NoSuchElementException if the current deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item removedItem = first.item;
        first = first.next;
        if (first != null) first.previous = null;
        if (size == 1) last = first;
        size--;

        return removedItem;
    }

    /**
     * Remove the last element of the deque.
     *
     * @return the last item in the deque
     * @throws NoSuchElementException if the current deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item removedItem = last.item;
        last = last.previous;
        if (last != null) last.next = null;
        if (size == 1) first = last;
        size--;

        return removedItem;
    }

    /**
     * Return a deque iterator that iterates through the items in FIFO order
     *
     * @return an iterator that iterates through the items in FIFO order
     */
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }


    /**
     * Return a string representation of the deque.
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

    private class DequeIterator implements Iterator<Item> {
        Node current;

        public DequeIterator(Node item) {
            current = item;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("First");
        deque.addLast("Last");
        deque.addFirst("Another first");
        deque.addFirst("Third first");
        deque.addLast("Another last");
        System.out.println("The size of the deque is: " + deque.size());
        System.out.println(deque.toString());
        System.out.println("Removing items:");
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println("----");
        System.out.println("The size of the deque is: " + deque.size());
        System.out.println(deque.toString());
        System.out.println("Is the deque empty? " + deque.isEmpty());
    }
}
