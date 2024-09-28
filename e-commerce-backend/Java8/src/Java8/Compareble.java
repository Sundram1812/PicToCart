package Java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Compareble {
    public static void main(String[] args) {
        List<Student> students=new ArrayList<>();
        students.add(new Student("Ayush", 50));
        students.add(new Student("sundram", 10));
        students.add(new Student("peter", 69));

        Collections.sort(students, new SortByAge());

        students.stream().forEach(s-> System.out.println(s));

        Collections.sort(students, new SortByName());

        students.stream().forEach(s-> System.out.println(s));

    }

}

class SortByAge implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o2.age- o1.age;
    }
}

class SortByName implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o1.name.compareTo(o2.name);
    }
}
