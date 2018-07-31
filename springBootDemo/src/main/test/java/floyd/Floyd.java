package java.floyd;

import java.util.Arrays;

/**
 * Created by SmallAnn on 2018/7/24
 */
public class Floyd {

    static int sum = 0;

    static int max = 1000;
    //模拟图
    static int a[][] = {
            {0,   2,   6,   4},
            {max, 0,   3, max},
            {7,   max, 0,   1},
            {5,   max, 12,  0},
    };
    static int size = a.length;


    public static void main(String[] args){
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                if(i == j) continue;
                //分别检测从0-3号定点中转，是否能更近
                for(int k = 0; k < size; k++){
                    if(k == j || k == i) continue;
                    if(a[i][j] > a[i][k] + a[k][j]){
                        a[i][j] = a[i][k] + a[k][j];
                    }
                }
            }
        }
        
        System.out.println(Arrays.deepToString(a));
    }
}
