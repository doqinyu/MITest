package utils;

import java.util.HashSet;
import java.util.Set;

public class HashUtils {

    public static int rsHash(String value) {
        int one = 378551;
        int two = 63689;
        int hash = 0;

        for(int i = 0; i < value.length(); ++i) {
            hash = hash * two + value.charAt(i);
            two *= one;
        }

        return hash & 2147483647;
    }

    public static String genTableName(String table, String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null, table is " + table);
        }
        int hash = rsHash(key);
        Integer nums = 32;
        if (nums == null) {
            throw new IllegalArgumentException("Cannot find table split num");
        }

        return table + "_" + (hash % nums);
    }

    public static void main(String[] args) {
        String table = "puri_video_base";
        String key = "93988397313056256c5f28c87796760d";
        String s = genTableName(table, key);

    }

}
