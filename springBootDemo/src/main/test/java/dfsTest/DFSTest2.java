package java.dfsTest;

/**
 * Created by SmallAnn on 2018/7/12
 */
public class DFSTest2 {

    static int endX = 3, endY = 3; //终点的坐标
    static int min = Integer.MAX_VALUE;

    //模拟迷宫，0是空地，1是墙
    static int a[][] = {
            {0, 0, 1, 0, 1},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0}
    };

    static boolean book[][] = new boolean[a.length][a[0].length];

    //模拟四种走法
    static int next[][] = {
            {1, 0},
            {0, 1},
            {-1, 0},
            {0, -1},
    };

    public static void main(String[] args){
        book[0][0] = true;
        dfs(0, 0 ,0);
        System.out.println(min);
    }

    static void dfs(int x, int y, int step){
        //枚举4种走法
        for(int i = 0; i < 4; i++) {
            int next = (int)Math.pow(-1, i);
            if(i < 2) x += next;
            else y += next;

            //验证该点的合法性
            if(x < 0 || y < 0 || x >= a.length || y >= a[0].length || a[x][y] == 1 || book[x][y]){
                continue;
            }

            //是否到达
            if(x == endX && y == endY){
                if(step < min){
                    min = step;
                }
                return;
            }

            //标记该点
            book[x][y] = true;

            //进行下一步
            dfs(x, y, step + 1);

            //取消该点标记
            book[x][y] = false;
        }
    }
}
