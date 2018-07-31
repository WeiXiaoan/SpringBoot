package java.dijkstra;

import java.util.Arrays;

/**
 * Created by SmallAnn on 2018/7/26
 * Dijkstra算法 - 通过边实现松弛
 */
public class Dijkstra {


    static int m = Integer.MAX_VALUE / 2;
    //模拟图
    static int a[][] = {
            {0, 1, 12, m, m, m},
            {m, 0, 9 , 3, m, m},
            {m, m, 0 , m, 5, m},
            {m, m, 4,  0, 13,15},
            {m, m, m,  m, 0, 4},
            {m, m, m,  m, m, 0},
    };

    static int length = a.length;

    static int[] dis = new int[a[0].length];

    static boolean[] book = new boolean[dis.length];

    public static void main(String[] args){
        //初始化dis
        System.arraycopy(a[0], 0, dis, 0, a.length);

        int start = 0;
        while (start < a.length){
            start++;

            //找到dis数组里的最小值，这个操作要循环dis.length次
            int index = 0;
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < a.length; i++) {
                if(dis[i] < min && !book[i]){
                    index = i; min = dis[i];
                }
            }
            book[index] = true;
            System.out.println(min);

            //遍历比较通过这个点中转能否有更好的距离
            for(int i = 0; i < length; i++) {
//                System.out.println("a[i][index] = " + a[i][index] + "a[index][i] = " + a[index][i]);
                if(dis[i] > dis[index] + a[index][i]) {
                    dis[i] = dis[index] + a[index][i];
                }
            }
        }
        
        System.out.println(Arrays.toString(dis));

    }
}
