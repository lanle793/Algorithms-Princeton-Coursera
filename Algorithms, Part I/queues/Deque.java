import java.util.*;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    List<Item> list;

    // construct an empty deque
    public Deque() {
        list = new ArrayList<Item>();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return list.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        list.add(0, item);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item");
        }
        list.add(item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.remove(0);
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list.remove(size() - 1);
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return list.iterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque deque = new Deque<String>();
        StdOut.println("The deque is empty: " + deque.isEmpty());
        // deque.removeLast();
        deque.addLast("Disculpe");
        deque.addFirst("Gracias");
        StdOut.println("The deque is empty: " + deque.isEmpty());
        StdOut.println("The deque size is: " + deque.size());
        StdOut.println("Remove first item: " + deque.removeFirst());
        deque.addLast("Mujer");
        StdOut.println("Remove last item: " + deque.removeLast());
        deque.addFirst("Hombre");
        deque.addLast("Pan");
        Iterator<String> it = deque.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
        // deque.addFirst(null);
    }

}
