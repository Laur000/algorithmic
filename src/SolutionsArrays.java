package src;

import java.util.*;
import java.util.stream.Collectors;

public class SolutionsArrays {

    //Given an integer array nums
    //return true if any value appears more than once in the array, otherwise return false.
    public static boolean hasDuplicate(int[] nums) {
        HashSet<Integer> setTest = new HashSet<>();
        for (int num : nums) {
            if (setTest.contains(num)) {
                return true;
            }
            setTest.add(num);
        }
        return false;
    }

    //Given two strings s and t, return true if the two strings are anagrams of each other,
    //otherwise return false. An anagram is a string that
    //contains the exact same characters as another string, but the order of the characters can be different.
    public static boolean isAnagram(String s, String t) {
        //if they have different length they are not anagrams
        if (s.length() != t.length()) {
            return false;
        }

        //Solution 1: sort them and see if they are the same
        //nLog(n)

        //Solution 2: Create 2 hashMaps and compare them, if you have 3 'a', the key will be 3
        //O(s+t) ==> O(n)
        HashMap<Character, Integer> charCountMap1 = new HashMap<>();
        HashMap<Character, Integer> charCountMap2 = new HashMap<>();

        //charCountMap.getOrDefault -- if the character C already exists in the Map, it returns
        //the actual key, if it s not, it returns 0. So when we add a new one, the key will be 1
        //then if it already exists it iterates through the map
        for (int i = 0; i < s.length(); i++) {
            charCountMap1.put(s.charAt(i), charCountMap1.getOrDefault(s.charAt(i), 0) + 1);
            charCountMap2.put(t.charAt(i), charCountMap2.getOrDefault(t.charAt(i), 0) + 1);
        }
        return charCountMap1.equals(charCountMap2);
    }

    //Given an array of integers nums and an integer target,
    //return the indices i and j such that nums[i] + nums[j] == target and i != j.
    //You may assume that every input has exactly one pair of indices i and j that satisfy the condition.
    //Return the answer with the smaller index first.
    public static int[] twoSum(int[] nums, int target) {

        //Solution 1: brut Force
        //O(n^2)
       /* for(int i =0 ; i<nums.length; i++){
            if(nums[i] > target+1){
                break;
            }
            for(int j = 0 ; j<nums.length; j++){
                if(i!=j && nums[i]+nums[j] == target){
                    return new int[] {i,j};
                }
            }
        }
        return new int[] {};*/

        //Solution 2: target - num[i] = num[2], check for num[2]
        HashMap<Integer, Integer> prevMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int diff = target - num;

            //this check is O(1) so in the moment when you have [3,4,5,6] and the target is 7
            //you check the num=3, and diff=4. You do not find it and add 3 to the hashMap
            // next you'll have num=4 and diff=3, and you will find the diff
            if (prevMap.containsKey(diff)) {
                return new int[]{prevMap.get(diff), i};
            }

            prevMap.put(num, i);
        }
        return new int[]{};
    }


    //Given an array of strings strs, group all anagrams together into sublists.
    //You may return the output in any order.
    //An anagram is a string that contains the exact same characters as another
    // string, but the order of the characters can be different.
    //Input: strs = ["act","pots","tops","cat","stop","hat"]
    //Output: [["hat"],["act", "cat"],["stop", "pots", "tops"]]
    public static List<List<String>> groupAnagrams(String[] strs) {
        //Solution 1: sort all the string and compare them between them to see if they are anagrams
        //O(m*nlogn)
      /*  List<List<String>> result = new ArrayList<>();
        //List<String> element = new ArrayList<>();

        for(int i=0; i<strs.length;i++){
            List<String> element = new ArrayList<>();
            element.add(strs[i]);

            for(int j=i+1; j<strs.length;j++){
                if(strs[i].length() == strs[j].length()){
                    char[] charStringI = strs[i].toCharArray();
                    Arrays.sort(charStringI);
                    char[] charStringJ = strs[j].toCharArray();
                    Arrays.sort(charStringJ);

                    if(Arrays.equals(charStringI, charStringJ)){
                        element.add(strs[j]);
                    }
                }
            }
            result.add(element);
        }

        return result;
    }*/

        //Solution 2: create a int[] formed of 26 of 0 ( a-z) and make the 1 when you see a value
        //take each value 1 by 1, create a KEY formed of 0010100101010 and then add it as a key in the map
        //when you create the key again for a new string, we are looking to see if the key exists in the hashMap
        //when it does, we add the value to the list
        //O(m*n)

        Map<String, List<String>> answer = new HashMap<>();

        for (String individualStr : strs) {
            int[] count = new int[26]; //initialize the "alphabet"
            for (char c : individualStr.toCharArray()) {
                count[c - 'a']++;  //go through the string char by char and make the char that you found a 1
            }

            String key = Arrays.toString(count);
            if (!answer.containsKey(key)) {
                //if the anagram is not already in the hashMap, we add a new key here
                //for example here the new element is (00010001000100, [])
                answer.put(key, new ArrayList<>());
            }

            //if the key that we crated already exist in the HashMap, e only and to the list of strings
            //the value of the individual string (00010001000100, [indivStr])
            answer.get(key).add(individualStr);
        }

        return new ArrayList<>(answer.values());
    }

    //Given an integer array nums and an integer k, return the k most frequent
    //elements within the array.
    //The test cases are generated such that the answer is always unique.
    public static int[] topKFrequent(int[] nums, int k) {
        //use Bucket Sort. Make a map, key-value pair. Where the key = count
        // value = value that occurrences N times.
        //and to take the k = 2 values, you start at the end, from the bigger key
        //ex: [ 2, 4,4, 3, 3, 3, 5, 5, 5]
        // count:  |  1   2      3      4  5  6 |
        // values: | [2] [4]  [3, 5]   [] [] [] |
        //ALSO: The Map will have the maximum value of nums.length because if they have only 1 occurence that will be enaugh

        //or you can do a classic


        Map<Integer, Integer> count = new HashMap<>();
        for (int n : nums) {
            //here we ar going through nums and count each appearance
            //!This is a good trick to count the appearance in O(n)
            //the key is the Number, the Value is the count
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        //sort the map in reverse order
        Map<Integer, Integer> sortedMap = count.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function in case of key collisions (not likely here)
                        LinkedHashMap::new // Keep the insertion order
                ));
        int[] result = new int[k];
        int indx = 0;

        //take the first k elements from the sorted map
        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
            if (indx >= k) {
                return result;
            }
            Integer key = entry.getKey();
            result[indx] = key;
            indx++;
        }

        return result;


    /*    List<Integer>[] freq = new List[nums.length + 1];

        //here we initialize each array list
        for (int i = 0; i < freq.length; i++) {
            freq[i] = new ArrayList<>();
        }
*/
     /*   //here we form the Bucket Sort in Java
        //get the 'value' = count, put the 'KEY' = nums[i] in the list
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            freq[entry.getValue()].add(entry.getKey());
        }

        //make the result, go to the back of the list and take the last k elements.
        int[] res = new int[k];
        int index = 0;
        for (int i = freq.length - 1; i > 0 && index < k; i--) {
            for (int n : freq[i]) {
                res[index++] = n;
                if (index == k) {
                    return res;
                }
            }
        }*/
    }


    //-------------------------------------------------------------------------
    //Design an algorithm to encode a list of strings to a single string.
    // The encoded string is then decoded back to the original list of strings.
    public static String encode(List<String> strs) {
        //Solution 1: put length# in front of every word
        //Use a string builder to build the encoded string
        StringBuilder encodedString = new StringBuilder();

        for(String str : strs){
            encodedString.append(str.length()).append("#").append(str);
        }
        return encodedString.toString();


        //Solution 2: my solution
      /*  if(strs.isEmpty()){
            return "";
        }
        String result = String.join("cox", strs);
        return result;*/
    }

    public List<String> decode(String str) {
        //Solution 1: Decode the string
        List<String> list = new ArrayList<>();
        int i = 0;
        while (i < str.length()) { //ex: 4#neet2#ab
            int j = i; //i - will be the end position of the word
            while (str.charAt(j) != '#') j++; //j  will be the starting position of the word

            int length = Integer.valueOf(str.substring(i, j)); // substring 0,1 so the first char
            i = j + 1 + length; //1 + 1 + 4-length
            list.add(str.substring(j + 1, i)); // substring 2, 6
        }
        return list;
        //my solution
    /*    if(str.isEmpty()){
            return new ArrayList<>(List.of(""));
        }
        String[] words = str.split("cox");
        return new ArrayList<>(Arrays.asList(words));*/
    }

    //--------------------------------------------------------------------------
    //Products of Array Discluding Self//
    //You have a array [1,2,3,4] the output will be the multiplication of the array except arr[i]
    //so for this will be [24, 12, 8, 6]
    public static int[] productExceptSelf(int[] nums) {
        //Solution 1: multiply all the elements in the array and divide them by arr[i]
        //Solution 2: so the multiplication of arr[i] is equal with all the prefixes[] * suffixes[]
            //we can put go through arr two times start-end and end-start and first put the
            //multiplications of the prefixes in the output[i] and the suffixes in the output[i]
        int[] output = new int[nums.length];

        int prefix = 1, suffix = 1;
        for(int i = 0 ; i<nums.length;i++){
            //first we are creating the output with the PREFIX in it. We take each value and multiply it
            output[i] = prefix;
            prefix = nums[i] * prefix;
        }
        for(int i = nums.length-1 ; i >= 0;i--){
            //first we are creating the output with the PREFIX in it. We take each value and multiply it
            output[i] = output[i]*suffix;
            suffix = nums[i] * suffix;
        }
        return output;
    }

    //Longest Consecutive Sequence//
    //Given an array of integers nums, return the length of the longest consecutive sequence of elements.
    //A consecutive sequence is a sequence of elements in which each element is exactly 1
    //greater than the previous element.
    public static int longestConsecutive(int[] nums) {
        //Solution 1: Sort the nums on O(nlogn)
        //then create a set, count them, and if you do not have a consecutive sequence, re-set the value

        //Solution 2: Put the elements in a HashSet for quick checking
        //find the start of a sequence by checking if you have k-1 in the SET. If it's not, then that's
        //the start of the sequence. Then check for k+1


        int longest = 0;
        HashSet<Integer> numsSet = new HashSet<>();
        for(int number: nums){
            numsSet.add(number);
        }

        for(int i=0; i<nums.length; i++){
            if(!numsSet.contains(nums[i]-1)){ //if it doesn't contain it means that is the head of a new sequence
                int length = 1;   //let s say num[i] = 4
                while (numsSet.contains(nums[i] + length)) { //here we are checking for 4+1, 4+2, 4+3
                    length++;
                }
                longest = Math.max(length,longest);
            }
        }
        return longest;
    }

    //Given an array of meeting time intervals where
    //intervals[i] = [starti, endi], determine if a person could attend all meetings.
    public boolean canAttendMeetings(int[][] intervals){
       //Solution 1: check for overlapping
        // Compare the end time of the previous meeting with the start time of the current meeting

        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];

        //sort them by the start time, if the difference is negative we put b first.
        Arrays.sort(intervals, (a,b) -> a[0]-b[0] );

        for(int i=0; i< intervals.length; i++){
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        for(int i=0, j=1; i < intervals.length && j< intervals.length-1; i++,j++){
            if(end[i] > start[j]){
                return false;
            }
        }
        return true;
    }
}
