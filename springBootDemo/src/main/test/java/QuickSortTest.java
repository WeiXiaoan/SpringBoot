package java;

/**
 * Created by SmallAnn on 2018/7/11
 */
public class QuickSortTest {

    public static void main(String[] args){
        int[] num = new int[10];
        for(int i = 0 ; i < num.length ; i++ ){
            num[i] = (int)(Math.random() * 10);
            System.out.print(num[i] + "  ");
        }

        System.out.println();

        quickSort(num, 0, num.length - 1);

        for (int aNum : num) {
            System.out.print(aNum + "  ");
        }
    }


    static void quickSort(int[] num, int left, int right) {
        if(num.length <= 0){
            return;
        }

        if(left >= right) {
            return;
        }

        int temp = num[left];

        int i = left;
        int j = right;

        while (i != j){
            while (i < j && num[j] >= temp) {
                j--;
            }

            while (i < j && num[i] <= temp) {
                i++;
            }

            if(i != j){
                int swap = num[i];
                num[i] = num[j];
                num[j] = swap;
            }
        }

        //基准数归位
        num[left] = num[i];
        num[i] = temp;

        for (int aNum : num) {
            System.out.print(aNum + "  ");
        }
        System.out.println("");

        quickSort(num, left, i - 1);
        quickSort(num, i + 1, right);
    }
}
