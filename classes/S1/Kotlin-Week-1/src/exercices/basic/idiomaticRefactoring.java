package exercices.basic;


/*
 * Problem: Longest Common Prefix
 *
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an null.
 *
 * Example 1:
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 *
 *Example 2:
 * Input: strs = ["dog","racecar","car"]
 * Output: null
 * Explanation: There is no common prefix among the input strings.
 * */

public class idiomaticRefactoring {

    public static String longestCommonPrefix(String[] strs) {
        String longestCommonPrefix = null;
        if  (strs == null || strs.length == 0) {
            return null;
        }

        for (int i = 0; i < strs.length; i++) {
            var str = strs[i];
            for (int j = strs.length - 1; j > i; j--) {
                var prefix = getPrefix(str, strs[j]);
                if (longestCommonPrefix == null || prefix.length() < longestCommonPrefix.length()) {
                    longestCommonPrefix = prefix;
                }
            }
        }
        return longestCommonPrefix.isEmpty() ? null : longestCommonPrefix;
    }

    public static String getPrefix(String str1, String str2) {
        int lenght = str1.length() > str2.length() ? str2.length() : str1.length();
        String prefix = "";
        for (int i = 0; i < lenght; i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                prefix+= str1.charAt(i);
            } else {
                break;
            }
        }
        return prefix;
    }

    public static void main(String[] args) {
        var strs = new String[]{
                "flower",
                "flow",
                "flight"
        };
        System.out.println(longestCommonPrefix(strs));

        var strs2 = new String[] {"dog","racecar","car"};
        System.out.println(longestCommonPrefix(strs2));
    }
}


