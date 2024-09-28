public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
//        Test.main(new String[]{"a", "b"});
        main("sundram", 3);
    }

    public static void main(String name, int a) {
        System.out.println(name+" "+a);
        
    }
}

class Test extends Main{
    public static void main(String[] args) {
        System.out.println("Overidden ");
    }
}