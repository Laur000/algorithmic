package src;

public class SolutionsBinarySearch {
 /*   //---------------------Find Minimum in Rotated Sorted Array-----------------------
    //You are given an array of length n which was originally sorted in ascending order.
    //It has now been rotated between 1 and n times. For example, the array nums = [1,2,3,4,5,6] might become:
    //[3,4,5,6,1,2] if it was rotated 4 times.
    //[1,2,3,4,5,6] if it was rotated 6 times.
    //Notice that rotating the array 4 times moves the last four elements of the array to the beginning.
    //Rotating the array 6 times produces the original array.
    //Assuming all elements in the rotated sorted array nums are unique,return the minimum element of this array.
   */
    public int findMin(int[] nums) {
        //Solution 1: you can look for the point where they are not in increasing order nums[i] > nums[i+1]
        //Solution 2: Go to the middle and ask where we are. You can be in the left sorted portion or the right
        //sorted portion, that can be higher or lower than the middle. So it's logic to search in the lower side to
        //find the minimum

        int left= 0, right= nums.length-1;

        while(left <= right){
            //if this condition isn't fulfilled, it means that the array isn't sorted
            if(nums[left] <= nums[right]){
                return nums[left];
            }
            int mid = (left-right)/2;

            //if the middle is bigger the far left value, we know that we want to search in the smaller
            //side. So we are going to search on the right array
            if(nums[mid] >= nums[left]){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        return 0;
    }

    /*-----------------------------Find Target in Rotated Sorted Array----------------
    * */
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;

        while(l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            //determine which side is sorted
            if (nums[l] <= nums[mid]) { //the left side is sorted
                //If target > nums[mid] or target < nums[l], the target is not in this portion,
                if (target > nums[mid] || target < nums[l]) {
                    //so search the right half by setting:
                    l = mid + 1;
                } else {
                    //so search the left half by setting:
                    r = mid - 1;
                }
            } else { //right side is sorted
                if (target < nums[mid] || target > nums [r]) {
                    //search on the left side
                    r = mid - 1;
                } else {
                    //search on the right side
                    l = mid + 1;
                }
            }

        }

        return -1;

    }









}
