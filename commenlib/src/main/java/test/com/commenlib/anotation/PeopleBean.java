package test.com.commenlib.anotation;

/**
 * Created by zhangjian on 2019/5/14 14:38
 */
public class PeopleBean {
    public String name;
    public int age;

    public PeopleBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PeopleBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
