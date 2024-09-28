package exception_learning;

public class ArithmeticOperation {
    public static void main(String[] args) throws CustomException {
        Division division= new Division();
//        System.out.println(division.divide(102,0));

//        CalculateAge calculateAge=new CalculateAge();
//        System.out.println(calculateAge.eligibleForVote(15));

        DivideNumber divideNumber=new DivideNumber();
        System.out.println(divideNumber.divideBy(0));
    }
}

class Division{
    public int divide(int a, int b){
        int val=0;

        try{
            val= a/b;


        }catch (ArithmeticException aex){
//            aex.printStackTrace();
//            System.out.println(aex.getCause());
//            aex.getCause();
//            System.out.println(aex.getMessage());
            System.out.println(aex.fillInStackTrace());
        }catch (Exception ex){
            System.out.println();
        }

        return val;
    }
}

class CalculateAge{
    public boolean eligibleForVote(int age){
        try{
            if(age < 18 ){
                throw new CustomException("Your age is below 18 so you can not vote, By Custom exception");
            }else  {
                return true;
            }
        }catch (CustomException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return false;
    }
}

class CustomException extends Exception{
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}

class DivideNumber{
    int num=100;
    public int divideBy(int n) throws CustomException{
        return num / n;
    }
}
