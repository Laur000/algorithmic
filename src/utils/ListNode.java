package src.utils;

import java.util.List;

public class ListNode {
    public ListNode next;
    public int val;

    public ListNode() {}
    public ListNode(int val) { this.val = val; }
     public ListNode(int val, ListNode next) { this.val = val; this.next = next;}
    // Method to append a node to the end of the list
    public void appendToEnd(int val) {
        ListNode end = new ListNode(val);
        ListNode current = this;
        while (current.next != null) {
            current = current.next;
        }
        current.next = end;
    }

    // Method to print all the elements of the list
    public void printList() {
        ListNode current = this;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null"); // End of the list
    }

}
