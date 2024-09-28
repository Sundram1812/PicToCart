package Java8;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListLearn {
    public static void main(String[] args) {
        List<Integer> num= new ArrayList<>();

        num.add(1);
        num.add(4);
        num.add(2);
        num.add(3);
        num.add(4);
        num.add(5);
        num.add(6);
        num.add(2);
        num.add(2);
        num.add(3);

        System.out.println(num);

//        Iterator<Integer> it= num.iterator();
//
//        while (it.hasNext()){
//
//            if (it.next()==5){
//                System.out.println("removed");
//                it.remove();
//            }
//            System.out.println(it.next());
//        }
//
//        System.out.println(num);

//        num.stream().forEach((e)-> System.out.println(e));
//        List<Integer> collect = num.stream().map(e -> e * 10).collect(Collectors.toList());
//        System.out.println(collect);

//        List<Integer> collected = num.stream().filter(e -> e % 2 == 0).collect(Collectors.toList());
//        System.out.println(collected);

        Set<Integer> val=new HashSet<>();
        Set<Integer> duplicate = num.stream().filter(e -> !val.add(e)).collect(Collectors.toSet());
        System.out.println(duplicate);

        int sum = num.stream().filter(e -> e % 2 == 0).mapToInt(e->e).sum();
        System.out.println(sum);

    }
}
