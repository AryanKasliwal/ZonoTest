import java.util.Scanner;

public class Main {
    private static String ukRegex = "B\\d{3}[A-Za-z]{2}\\w{7}";
    private static String germanyRegex = "A[A-Za-z]{2}\\w{9}";

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        OrderManager orderManager = OrderManager.getOrderManagerInstance();
        String prompt = sc.nextLine();
        int numGloves = 0;
        int numMasks = 0;
        String purchaseCountry;
        String passportNum = "";
        int checkIndex;
        String otherCountry; 
        while (prompt != "exit") {
            checkIndex = 1;
            String [] input = prompt.split(":");
            purchaseCountry = input[0];
            if (purchaseCountry.equals("UK")) {
                otherCountry = "Germany";
            }
            else {
                otherCountry = "UK";
            }
            if (input[1].matches(ukRegex) || input[1].matches(germanyRegex)) {
                passportNum = input[1];
                checkIndex = 2;
            }
            if (input[checkIndex].charAt(0) == 'G') {
                numGloves = Integer.parseInt(input[checkIndex + 1]);
                numMasks = Integer.parseInt(input[checkIndex + 3]);
            }
            else if (input[checkIndex].charAt(0) == 'M') {
                numMasks = Integer.parseInt(input[checkIndex + 1]);
                numGloves = Integer.parseInt(input[checkIndex + 3]);
            }
            System.out.println(orderManager.placeOrder(numGloves, numMasks, purchaseCountry, otherCountry, passportNum));
            prompt = sc.nextLine();
        }
        sc.close();
    }

    
}