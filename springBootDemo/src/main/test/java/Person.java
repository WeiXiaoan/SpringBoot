import java.util.Comparator;

/**
 * Created by SmallAnn on 2018/7/4
 */
public class Person implements Comparable<Person>{

    private long id;
    private int age;
    private int gold;

    public Person(long id, int age, int gold) {
        this.id = id;
        this.age = age;
        this.gold = gold;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public int compareTo(Person o) {
        return Comparator.comparingLong(Person::getId)
                .thenComparing(Person::getAge)
                .thenComparing(Person::getGold)
                .reversed()
                .compare(this, o);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age=" + age +
                ", gold=" + gold +
                '}';
    }
}
