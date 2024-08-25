package src;

import src.utils.ListNode;

import java.util.HashMap;
import java.util.Map;
import src.utils.Node;

public class SolutionsLists {

    //-------------Combine 2 ordered lists---------------
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //We are going to compare each node, if the node is smaller we iterate
        //through list 1, adding the smaller node in the dummy list
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;

        while(list1 != null && list2 != null){
            //when the element is smaller than the other, we add the element into the NEW list, and move to the next one
            //in the list where it was smaller
            if(list1.val < list2.val){
                node.next = list1;
                list1 = list1.next;
            }else{
                node.next = list2;
                list2 = list2.next;
            }
            //then we switch to the actual value
            node=node.next;

        }

        //if the elements finishes in one of the lists, we add the rest of the list in our new list
        if (list1 != null) {
            node.next = list1;
        } else {
            node.next = list2;
        }

        //this is the start element of our list
        //we basically started with 2 nodes in the same place, and move one of them.
        return dummy.next;
    }

    //------------Reverse a list---------------
    public static ListNode reverseList(ListNode head){
        //Solution 1: go with two nodes at the same time and do it iterative
        ListNode current = head;
        ListNode previous = null;
        ListNode nextCurr= null;

        while(current!=null){
            //we save the next element in current
            nextCurr = current.next;

            //the next current will be equal with the previous one
            current.next = previous;

            //the previous will be equal with the current one
            previous = current;

            //here with this pointer we go through the list
            current = nextCurr;
        }
        return previous;
    }

    //----------------------------Remove Node From End of Linked List-------------------------
    //You are given the beginning of a linked list head, and an integer n.
    //Remove the nth node from the end of the list and return the beginning of the list.
    //Input: head = [1,2,3,4], n = 2
    //Output: [1,2,4]
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        //Solution 1: if you want to delete the N-th node from the end
        //you can go N steps in the list with a node, and then start again
        //and go with node2 util the node1.next is null
        //this way you will receive the n-th node
        ListNode dummy = new ListNode(0, head);
        ListNode left = dummy;
        ListNode right = head;

        //n=2, we are going two steps in, right = 3
        while (n > 0) {
            right = right.next;
            n--;
        }

        //then for right to reach null will take two steps
        //and do not forget that left = 0 here, so left will be 2 in the end
        while (right != null) {
            left = left.next;
            right = right.next;
        }

        //skip that element in the node
        left.next = left.next.next;
        return dummy.next;
    }

 /*   //---------------------------Copy Linked List with Random Pointer----------------------
    //You are given the head of a linked list of length n. Unlike a singly linked list, each node contains an additional pointer random, which may point to any node in the list, or null.
    //Create a deep copy of the list.
    //The deep copy should consist of exactly n new nodes, each including:
    //The original value val of the copied node
    //A next pointer to the new node corresponding to the next pointer of the original node
    //A random pointer to the new node corresponding to the random pointer of the original node
    //Note: None of the pointers in the new list should point to nodes in the original list.
    //Return the head of the copied linked list.
    //In the examples, the linked list is represented as a list of n nodes. Each node is represented as a pair of [val, random_index] where random_index is the index of the node (0-indexed) that the random pointer points to, or null if it does not point to any node.
*/
 public Node copyRandomList(Node head) {
     if (head == null) {
         return null;
     }

     // Step 1: Create a HashMap to store the mapping from original nodes to their copies
     Map<Node, Node> map = new HashMap<>();

     // Step 1: First pass - create all the new nodes and store them in the map
     Node current = head;
     while (current != null) {
         map.put(current, new Node(current.val));
         current = current.next;
     }

     // Step 2: Second pass - assign next and random pointers using the map
     current = head;
     while (current != null) {
         Node copy = map.get(current);
         copy.next = map.get(current.next); // Set the next pointer
         copy.random = map.get(current.random); // Set the random pointer
         current = current.next;
     }

     // Step 3: Return the head of the copied list
     return map.get(head);
 }
}
