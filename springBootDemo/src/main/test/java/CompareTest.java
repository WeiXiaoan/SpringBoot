import java.util.TreeSet;

/**
 * Created by SmallAnn on 2018/7/4
 */
public class CompareTest {


    public static void main(String[] args){
        Person p1 = new Person(1, 10, 999);
        Person p2 = new Person(1, 25, 9999);
        Person p3 = new Person(1, 25, 100000);
        Person p4 = new Person(4, 18, 999999999);

        TreeSet<Person> treeSet = new TreeSet<>();
        treeSet.add(p1);
        treeSet.add(p2);
        treeSet.add(p3);
        treeSet.add(p4);

        for(Person r : treeSet){
            System.out.println(r.toString());
        }
    }



}
