package src;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        int[] nums = new int[] {1,2,3,4};
        String[] strings = {"act","pots","tops","cat","stop","hat"};
        // List<String> listA = new ArrayList<>(Arrays.asList(strings));
        System.out.println(Arrays.toString(SolutionsArrays.productExceptSelf(nums)));
    }
}