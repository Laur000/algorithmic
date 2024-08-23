package src;

import java.util.Arrays;
import java.util.Stack;

import src.utils.TreeNode;


public class SolutionsTrees {
    //------------------Min Depth------------------
    //return the minimum depth of a binary tree
    public static int minDepth(TreeNode root){
        if(root == null){
            return 0;
        }

        // If left subtree is null, only consider right subtree
        if(root.left == null){
            return minDepth(root.right) + 1;
        }
        // If right subtree is null, only consider left subtree
        if(root.right == null){
            return minDepth(root.left) + 1;
        }

        // If both subtrees are non-null, find the minimum depth of both subtrees
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    //------------------Invert Tree------------------
    //You are given the root of a binary tree root. Invert the binary tree and return its root.
    public static TreeNode invertTree(TreeNode root) {
        if(root == null){
            return null;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    //------------------ Same Tree ------------------
    //Given the roots of two binary trees p and q,
    //return true if the trees are equivalent, otherwise return false.
    //Two binary trees are considered equivalent if
    //they share the exact same structure and the nodes have the same values.
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null){
            return true;
        }
        //if they are not null but the values are the same, we check the next roots
        if(p != null && q != null  && p.val == q.val){
            return isSameTree(p.left,q.left) && isSameTree(p.right, q.right); //this returns the result from true && false
        }else {
            return false;
        }
    }

    //----------------Lowest Common Ancestor in Binary Search Tree--------------------
    //Given a binary search tree (BST) where all node values are unique, and two nodes from the tree p and q,
    //return the lowest common ancestor (LCA) of the two nodes.
    //The lowest common ancestor between two nodes p and q is the lowest node in a tree T such
    //that both p and q as descendants. The ancestor is allowed to be a descendant of itself.
    //            6
    //         2     8
    //       0   4  7 9
    //          3 5
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //Solution 1: If they are in different subtrees they cannot have the ascensor in the right or left so
        //Let's say p = 2  and q = 8. So we are comparing them with the root
        //if p is in the left and q is in the right, it means that the root is the LCA
        //Also, if p or q are equal with the ROOT, it means the root is the LCA
        while (true) {
            //here we basically go in the right or left depending the situation.
            //and when we find that one is bigger and the other one is lower OR one of them is equal
            //we return the root
            if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else {
                return root;
            }
        }
    }

    //-----------------------Kth Smallest Integer in BST------------------------------
    //Given the root of a binary search tree, and an integer k,
    //return the kth smallest value (1-indexed) in the tree.
    public static int kthSmallest(TreeNode root, int k) {
        //Solution 1: If you do a In-Order Traversal on a binary tree, you will have a sorted array
        //Then you can take the k-th element in the array.

        //Solution 2: Use a Stack and do the problem iteratively. LIFO
        //You travers the Tree, and when you encounter a LEFT NODE that is NULL. That is the trigger
        //to POP from the stack. And then you increase/decrease the k++. If k will be the k-th element that you passed
        //that means that is your element, because in the left you have the smallest.
        //ONLY AFTER we visit a node, we go to his Right Child

        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;

        while (!stack.isEmpty() || curr != null) { //as long as stack is not empty we continue
            while (curr != null) { //when curr is NULL, we stop and exit this wile
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop(); //we pop from the stack the element and save it to the current one
            k--;                //decrement the k
            if (k == 0) {       //when k is zero, you found your value. Now return it
                return curr.val;
            }
            curr = curr.right;
        }

        return -1; //didn't t find it
    }

    //------------------Binary Tree from Preorder and Inorder Traversal------------------
    //You are given two integer arrays preorder and inorder.
    //preorder= [3, 9 ,20, 15, 7] is the preorder traversal of a binary tree &&
    //inorder= [ 9, 3, 15, 20 ,7] is the inorder traversal of the SAME TREE
    //Both arrays are of the same size and consist of unique values.
    //Rebuild the binary tree from the preorder and inorder traversals and return its root.
    public TreeNode buildTree(int[] preorder, int[] inorder){
    /*//Solution 1:
        //1. The first value in the PRE-Order Traversal is going to be the Root.
        //2. We found the Root. The go in the IN-ORDER Traversal, find the root position
        //and that guarantees that the left values from the Root are in the left tree and the right values
        //are in the right. If int the left we have 1 value and in the right 3 values, we know how to partition
        //the PRE-order traversal array. First element and then the rest of 3 elements
        //With this logic, we go back to Pre-Order, take the first left value, and put it
        //in the left subtree.
        //3. Then if that value does not have other values in the left&&right positions of the IN-order traversal
        //we know that is the ROOT of that tree. We go to the right end do the same thing. In this case 20 has values
        //in the right and left, And we repeat the process*/

        //if one of them is empty return null
        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        //the root will be always the first value of the preorder
        TreeNode root = new TreeNode(preorder[0]);

        //find the mid value in inorder mid = index of root value
        int mid = -1;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == preorder[0]) {
                mid = i;
                break;
            }
        }

        //here we are taking the left part of the inorder and preorder to call the function
        int[] leftPreorder = Arrays.copyOfRange(preorder, 1, mid + 1); //here we are not including the root
        int[] leftInorder = Arrays.copyOfRange(inorder, 0, mid); //here we are not including mid - the root
        //create the left part
        root.left = buildTree(leftPreorder,leftInorder);

        //taking the right part
        int[] rightPreorder = Arrays.copyOfRange(preorder, mid + 1, preorder.length);
        int[] rightInorder = Arrays.copyOfRange(inorder, mid + 1, inorder.length);
        //create right
        root.right = buildTree(rightPreorder, rightInorder);

     return root;
    }

}
