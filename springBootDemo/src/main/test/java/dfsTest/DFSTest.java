package java.dfsTest;

import java.util.Arrays;

/**
 * Created by SmallAnn on 2018/7/11
 */
public class DFSTest {
    
    public static void main(String[] args){
        int size = 3;
        int[] a = new int[size];
        boolean[] have = new boolean[]{true, true, true};
        dfs(0, a, have);
    }

    static void dfs(int step, int[]a, boolean[] have) {
        if(step == a.length){
            System.out.println(Arrays.toString(a));
            return;
        }

        for(int i = 0 ; i < a.length ; i++) {
            if(!have[i]) {
                continue;
            }

            a[step] = i;
            have[i] = false;

            dfs(step + 1, a, have);
            have[i] = true;
        }
    }
}
