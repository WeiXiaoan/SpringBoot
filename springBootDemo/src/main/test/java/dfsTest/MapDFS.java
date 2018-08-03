package dfsTest;

/**
 * Created by SmallAnn on 2018/7/19
 */
public class MapDFS {

    static int sum = 0;
    //模拟图
    static int a[][] = {
            {0, 0, 1, 0, 1},
            {0, 0, 1, 1, 0},
            {1, 1, 0, 0, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0}
    };

    static int size = a.length;
    static boolean book[] = new boolean[a.length];

    public static void main(String[] args){
        book[0] = true;
        dfs(0);
    }

    static void dfs(int cur) {
        System.out.println(cur);
        sum++;
        if(sum == size) return;

        for(int i = 0; i < size; i++){
            if(a[cur][i] == 1 && !book[i]){
                book[i] = true;
                dfs(i);
            }
        }
    }
}
