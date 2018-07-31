package java;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SmallAnn on 2018/7/13
 */
public class BFSTest {

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

    static List<Node> nodes = new ArrayList<>();

    public static void main(String[] args){
        Node start = new Node(0, 0, 0);
        nodes.add(start);
        int head = 0;


        while (head < nodes.size()){
            int x = nodes.get(head).x;
            int y = nodes.get(head).y;
            
            //是否到达
            if(x == endX && y == endY){
                System.out.println(nodes.get(head).s);
                break;
            }

            //枚举4种走法
            for(int i = 0; i < 4; i++) {
                int next = (int)Math.pow(-1, i);
                if(i < 2) x += next;
                else y += next;

                //验证该点的合法性
                if(x < 0 || y < 0 || x >= a.length || y >= a[0].length || a[x][y] == 1 || book[x][y]){
                    continue;
                }

                book[x][y] = true;
                Node node = new Node(x, y, nodes.get(head).s + 1);
                nodes.add(node);
            }

            head++;
        }




        }
}
