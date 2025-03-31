import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {
        Random rand = new Random();
        for(int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);
            swap(r1,r2); // swap nodes at these indices
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        if (index < 0 || index >= size) return null;
        Node curr = head;
        for (int i = 0; i < index; i++){
            curr = curr.next;
        }
        if (curr.prev != null){
            curr.prev.next = curr.next;
        } else {
            head = curr.next;
        }
        if (curr.next != null){
            curr.next.prev = curr.prev;
        } else {
            tail = curr.prev;
        }
        size--;
        return curr.data;
    }

    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        if (index < 0 || index > size) return;
        Node newNode = new Node(x);

        if (index == 0){
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (size == 0) tail = head;
        } else {
            Node current = head;
            for (int i = 0; i < index -1; i++){
                current = current.next;
            }
            newNode.next = current.next;
            if (current.next != null) current.next.prev = newNode;
            current.next = newNode;
            newNode.prev = current;
            if (newNode.next == null) tail = newNode;
        }
        size++;
    }

    // swap two cards in the deck at the specific indices This method utilizes the re-
    //move_from_index and insert_at_index methods to effectively swap 2 cards
    //in the deck. Hint: Removing and adding cards will impact the size of the LinkedList
    //and might also impact the index number you might be adding to after you
    //remove a card. Think through this carefully.
    public void swap(int index1, int index2) {
        if (index1 == index2) return;
        Card temp = remove_from_index(index1);
        insert_at_index(temp, index2);
    }

    // add card at the end of the list
    public void add_at_tail(Card data) {
        Node newNode = new Node(data);
        if (tail != null){
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else{
            head = tail = newNode;
        }
        size++;
    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        if (head == null) return null;
        Card data = head.data;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null;
        size--;
        return data;
    }

    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
    // and verifying that the size of the list is the same when counted both ways.
    // 1) if a node is incorrectly removed
    // 2) and head and tail are correctly updated
    // 3) each node's prev and next elements are correctly updated
    public void sanity_check() {
        // count nodes, counting forward
        Node curr = head;
        int count_forward = 0;
        while (curr != null) {
            curr = curr.next;
            count_forward++;
        }

        // count nodes, counting backward
        curr = tail;
        int count_backward = 0;
        while (curr != null) {
            curr = curr.prev;
            count_backward++;
        }

        // check that forward count, backward count, and internal size of the list match
        if (count_backward == count_forward && count_backward == size) {
            System.out.println("Basic sanity Checks passed");
        }
        else {
            // there was an error, here are the stats
            System.out.println("Count forward:  " + count_forward);
            System.out.println("Count backward: " + count_backward);
            System.out.println("Size of LL:     " + size);
            System.out.println("Sanity checks failed");
            System.exit(-1);
        }
    }
    // new swap method: This method utilizes the remove_from_index and insert_at_index methods to effectively swap 2 cards in the deck


    // print the deck
    public void print() {
        Node curr = head;
        int i = 1;
        while(curr != null) {
            curr.data.print_card();
            if(curr.next != null)
                System.out.print(" -->  ");
            else
                System.out.println(" X");

            if (i % 7 == 0) System.out.println("");
            i = i + 1;
            curr = curr.next;
        }
        System.out.println("");
    }
}