package exception_learning;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, Integer> map= new LinkedHashMap<>();
        map.put("a",10);
        map.put("b",6);
        map.put("c",9);

        System.out.println(map.keySet());
        System.out.println(map.values());
//        map.keySet().stream().
    }
}
