package src;

import java.util.HashSet;

public class SolutionSlidingWindow {
    /*------------------Longest Substring Without Duplicates------------------------
    Given a string s, find the length of the longest substring without duplicate characters.
    A substring is a contiguous sequence of characters within a string.
    */
    public static int lengthOfLongestSubstring(String s) {
        //Solution 1: use sliding window to make sure to we no longer have duplicates

        // Initialize a HashSet to store the unique characters in the current window
        HashSet<Character> charSet = new HashSet<>();
        // Initialize two pointers for the sliding window: `l` (left) and `r` (right)
        int l = 0;
        // Variable to store the result, i.e., the length of the longest substring
        int res = 0;

        // Loop through the string using the right pointer `r`
        for (int r = 0; r < s.length(); r++) {
            // If the character at position `r` is already in the set,
            // remove characters from the left pointer `l` until the duplicate is removed
            while (charSet.contains(s.charAt(r))) {
                charSet.remove(s.charAt(l));
                l++;  // Move the left pointer to the right
            }
            // Add the current character to the set
            charSet.add(s.charAt(r));

            // Update the result with the maximum length found
            // The length of the current substring is `r - l + 1`
            res = Math.max(res, r - l + 1);
        }

        return res;
    }
}
