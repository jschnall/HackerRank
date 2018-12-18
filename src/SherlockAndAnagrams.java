import java.io.*;
import java.util.*;

/**
 * Two strings are anagrams of each other if the letters of one string can be rearranged to form the other string. Given a string, find the number of pairs of substrings of the string that are anagrams of each other.
 *
 * For example , the list of all anagrammatic pairs is  at positions  respectively.
 *
 * Function Description
 *
 * Complete the function sherlockAndAnagrams in the editor below. It must return an integer that represents the number of anagrammatic pairs of substrings in .
 *
 * sherlockAndAnagrams has the following parameter(s):
 *
 * s: a string .
 * Input Format
 *
 * The first line contains an integer , the number of queries.
 * Each of the next  lines contains a string  to analyze.
 *
 * Constraints
 *
 *
 *
 * String  contains only lowercase letters  ascii[a-z].
 *
 * Output Format
 *
 * For each query, return the number of unordered anagrammatic pairs.
 *
 * Sample Input 0
 *
 * 2
 * abba
 * abcd
 * Sample Output 0
 *
 * 4
 * 0
 * Explanation 0
 *
 * The list of all anagrammatic pairs is  and  at positions  and  respectively.
 *
 * No anagrammatic pairs exist in the second query as no character repeats.
 *
 * Sample Input 1
 *
 * 2
 * ifailuhkqq
 * kkkk
 * Sample Output 1
 *
 * 3
 * 10
 * Explanation 1
 *
 * For the first query, we have anagram pairs  and  at positions  and respectively.
 *
 * For the second query:
 * There are 6 anagrams of the form  at positions  and .
 * There are 3 anagrams of the form  at positions  and .
 * There is 1 anagram of the form  at position .
 *
 * Sample Input 2
 *
 * 1
 * cdcd
 * Sample Output 2
 *
 * 5
 * Explanation 2
 *
 * There are two anagrammatic pairs of length :  and .
 * There are three anagrammatic pairs of length :  at positions  respectively.
 */

public class SherlockAndAnagrams {
    // s1 and s2 passed in will always be the same length
    // string.split("") timed out on 3 testcases, charArray method is definitely faster
    static boolean isAnagram(String s1, String s2) {
        Map<Character, Integer> map = new HashMap<>();
        for (char s : s1.toCharArray()) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        for (char s : s2.toCharArray()) {
            if (!map.containsKey(s)) {
                return false;
            }
            int count = map.getOrDefault(s, 0) - 1;
            if (count > 0) {
                map.put(s, count);
            } else {
                map.remove(s);
            }
        }
        if (map.isEmpty()) {
            return true;
        }
        return false;
    }

    // Complete the sherlockAndAnagrams function below.
    static int sherlockAndAnagrams(String s) {
        int result = 0;
        // Use map:
        // - Even if you add each form of the anagram found, set can't deal with the case
        // where there are more than 2 of the same exact string
        // - Store only 1 anagram of the string so can break out of loop early and do fewer compares
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 1; i < s.length(); i++) {
            // clear the map so we only compare strings of the current length
            map.clear();
            for (int j = 0; (j + i) <= s.length(); j++) {
                String str = s.substring(j, j + i);
                // Check if any of the keys in the map are anagrams of str
                boolean found = false;
                for (String str2 : map.keySet()) {
                    if (isAnagram(str, str2)) {
                        found = true;
                        int count = map.getOrDefault(str2, 0);
                        result += count;
                        map.put(str2, count + 1);
                        break;
                    }
                }
                if (!found) {
                    map.put(str, 1);
                }
            }
        }
        //System.out.println(map);
        return result;
    }

    // Switched scanner to BufferedReader for better speed
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(reader.readLine());
        for (int qItr = 0; qItr < q; qItr++) {
            String s = reader.readLine();

            int result = sherlockAndAnagrams(s);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        reader.close();
    }
}