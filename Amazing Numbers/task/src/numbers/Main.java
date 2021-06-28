package numbers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Amazing Numbers!");
        printInstructions();

        long num = 1;

        do {
            System.out.print("Enter a request: ");
            String[] numArr = sc.nextLine().split(" ");
            if (numArr[0].equals("")) {
                printInstructions();
            } else if (isNumeric(numArr[0])) {  // Checking whether the input is numeric
                num = Long.parseLong(numArr[0]);
                int x = 1;
                if (numArr.length > 1) {    // If there's a second number, make it X, otherwise it'll stay 1
                    x = Integer.parseInt(numArr[1]);
                }
                if (num < 1) {
                    if (num != 0) {
                        System.out.println("\nThe first parameter should be a natural number or zero.\n");
                    }
                } else if (x < 1) {
                    System.out.println("\nThe second parameter should be a natural number.\n");
                } else if (numArr.length >= 3) {
                    if (numArr.length == 3) {
                        if (checkThirdProperty(numArr[2])) {
                            System.out.println("\nThe property [" + numArr[2].toUpperCase() + "] is wrong.");
                            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, " +
                                    "SPY, SQUARE, SUNNY]\n");
                        } else {
                            // Results
                            printResults(x, numArr, num);
                            System.out.println();
                        }
                    } else if (numArr.length == 4) {
                        if (checkThirdProperty(numArr[2]) && checkThirdProperty(numArr[3])) {
                            System.out.println("\nThe properties [" + numArr[2].toUpperCase() +
                                    ", " + numArr[3].toUpperCase() + "] are wrong.");
                            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, " +
                                    "SPY, SQUARE, SUNNY]\n");
                        } else if (checkThirdProperty(numArr[2]) || checkThirdProperty(numArr[3])) {
                            if (checkThirdProperty(numArr[2])) {
                                System.out.println("\nThe property [" + numArr[2].toUpperCase() + "] is wrong.");
                            } else {
                                System.out.println("\nThe property [" + numArr[3].toUpperCase() + "] is wrong.");
                            }
                            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, " +
                                    "SPY, SQUARE, SUNNY]\n");
                        } else {
                            if (exclusiveProps(numArr[2], numArr[3])) {
                                errorExclusiveProps(numArr[2], numArr[3]);
                            } else {
                                // Results
                                printResults(x, numArr, num);
                                System.out.println();
                            }
                        }
                    }
                } else {
                    // Results if there's not a third parameter
                    printResults(x, numArr, num);
                }
            } else {
                System.out.println("\nThe first parameter should be a natural number or zero.\n");
            }
        } while (num != 0);

        System.out.println("\nGoodbye!");

    }

    public static boolean isOdd(long num) {
        return (num % 2 != 0);
    }

    public static boolean isEven(long num) {
        return (num % 2 == 0);
    }

    public static boolean isBuzz(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    public static boolean isPalindrome(long num) {
        long temp;
        temp = num;
        long d;
        long reverse;
        reverse = 0;
        while (temp > 0) {
            d = temp % 10;
            reverse = reverse * 10 + d;
            temp = temp / 10;
        }
        return reverse == num;
    }

    public static boolean isGapful(long num) {
        String numStr = Long.toString(num);
        String s1 = String.valueOf(numStr.charAt(0));
        String s2 = String.valueOf(numStr.charAt(numStr.length() - 1));
        String s = s1 + s2;
        int c = Integer.parseInt(s);
        return num >= 100 && num % c == 0;
    }

    public static boolean isDuck(long num) {
        String numStr = Long.toString(num);
        boolean duck = false;
        for (int i = 1; i < numStr.length(); i++) {
            if (numStr.charAt(i) == '0') {
                duck = true;
                break;
            }
        }
        return duck;
    }

    public static boolean isSpy(long num) {
        String numStr = Long.toString(num);
        int sum = 0;
        int product = 1;
        for (int i = 0; i < numStr.length(); i++) {
            sum += Character.getNumericValue(numStr.charAt(i));
            product *= Character.getNumericValue(numStr.charAt(i));
        }
        return (sum == product);
    }

    public static boolean isSquare(long num) {
        double sqrt = Math.sqrt(num);
        return ((sqrt - Math.floor(sqrt)) == 0);
    }

    public static boolean isSunny(long num) {
        double sqrt = Math.sqrt(num + 1);
        return ((sqrt - Math.floor(sqrt)) == 0);
    }

    protected static boolean checkThirdProperty(String property) {
        String[] availableTypes = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY"};
        boolean check = false;
        for (String availableType : availableTypes) {
            if (property.equalsIgnoreCase(availableType)) {
                check = true;
                break;
            }
        }
        return !check;
    }

    static boolean exclusiveProps(String prop1, String prop2) {
        boolean check;
        if (prop1.equalsIgnoreCase("even") && prop2.equalsIgnoreCase("odd")) {
            check = false;
        } else if (prop2.equalsIgnoreCase("even") && prop1.equalsIgnoreCase("odd")) {
            check = false;
        } else if (prop1.equalsIgnoreCase("duck") && prop2.equalsIgnoreCase("spy")) {
            check = false;
        } else if (prop2.equalsIgnoreCase("duck") && prop1.equalsIgnoreCase("spy")) {
            check = false;
        } else if (prop1.equalsIgnoreCase("square") && prop2.equalsIgnoreCase("sunny")) {
            check = false;
        } else check = !prop2.equalsIgnoreCase("square") || !prop1.equalsIgnoreCase("sunny");
        return !check;
    }

    static void errorExclusiveProps(String prop1, String prop2) {
        System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n",
                prop1.toUpperCase(), prop2.toUpperCase());
        System.out.println("There are no numbers with these properties.\n");
    }

    public static void printResults(int x, String[] numArr, long num) {

        if (x == 1 && numArr.length == 1) {
            System.out.println("\nProperties of " + num);
            System.out.println("buzz: " + isBuzz(num));
            System.out.println("duck: " + isDuck(num));
            System.out.println("palindromic: " + isPalindrome(num));
            System.out.println("gapful: " + isGapful(num));
            System.out.println("spy: " + isSpy(num));
            System.out.println("square: " + isSquare(num));
            System.out.println("sunny: " + isSunny(num));
            System.out.println("even: " + isEven(num));
            System.out.println("odd: " + isOdd(num));
            System.out.println();
        } else {
            if (numArr.length == 3) {
                identifyNumA(numArr, num);

            } else if (numArr.length == 4) {
                identifyNumB(numArr, num);

            } else {
                for (int y = 0; y < x; y++) {
                    long numIterator = num + y;

                    boolean[] typeNum = {isBuzz(numIterator), isDuck(numIterator), isPalindrome(numIterator),
                            isGapful(numIterator), isSpy(numIterator), isSquare(numIterator), isSunny(numIterator),
                            isEven(numIterator), isOdd(numIterator)};
                    String[] strTypeNum = {"buzz", "duck", "palindromic", "gapful", "spy", "square", "sunny",
                            "even", "odd"};
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < typeNum.length; i++) {
                        if (typeNum[i]) {
                            arrayList.add(strTypeNum[i]);
                        }
                    }
                    String output = String.join(", ", arrayList);
                    System.out.print(numIterator + " is " + output);
                    System.out.println();
                }
                System.out.println();
            }

        }

    }

    private static void identifyNumA(String[] numArr, long num) {
        long availableRange = Long.parseLong(numArr[1]);
        long checkSecondParam = 0;
        long i = num;
        switch (numArr[2].toLowerCase()) {
            case "buzz":
                while (checkSecondParam < availableRange) {
                    if (isBuzz(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "spy":
                while (checkSecondParam < availableRange) {
                    if (isSpy(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "even":
                while (checkSecondParam < availableRange) {
                    if (isEven(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "odd":
                while (checkSecondParam < availableRange) {
                    if (isOdd(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "duck":
                while (checkSecondParam < availableRange) {
                    if (isDuck(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "palindromic":
                while (checkSecondParam < availableRange) {
                    if (isPalindrome(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "square":
                while (checkSecondParam < availableRange) {
                    if (isSquare(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;
            case "sunny":
                while (checkSecondParam < availableRange) {
                    if (isSunny(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
            default: // Gapful
                while (checkSecondParam < availableRange) {
                    if (isGapful(i)) {
                        checkSecondParam++;
                        printProps(i);
                    }
                    i++;
                }
                break;

        }
        System.out.println();

    }

    public static void identifyNumB(String[] numArr, long num) {
        long availableRange = Long.parseLong(numArr[1]);
        long checkSecondParam = 0;
        long i = num;

        switch (numArr[2].toLowerCase()) {
            case "buzz":
                if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "spy":
                if (numArr[3].equalsIgnoreCase("buzz")) {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isBuzz(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isSpy(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "even":
                if (numArr[3].equalsIgnoreCase("buzz")) {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isBuzz(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isEven(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "odd":
                if (numArr[3].equalsIgnoreCase("buzz")) {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isBuzz(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isOdd(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "duck":
                if (numArr[3].equalsIgnoreCase("buzz")) {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isBuzz(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isDuck(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "palindromic":
                if (numArr[3].equalsIgnoreCase("buzz")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isBuzz(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isPalindrome(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "square":
                if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isSquare(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isSquare(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isSquare(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isSquare(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isSquare(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isSquare(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            case "sunny":
                if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isSunny(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isSunny(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isSunny(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isSunny(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isSunny(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else {
                    while (checkSecondParam < availableRange) {
                        if (isBuzz(i) && isGapful(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
            default: // Gapful
                if (numArr[3].equalsIgnoreCase("spy")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isSpy(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("even")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isEven(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("odd")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isOdd(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("duck")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isDuck(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("palindromic")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isPalindrome(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("square")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isSquare(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                } else if (numArr[3].equalsIgnoreCase("sunny")) {
                    while (checkSecondParam < availableRange) {
                        if (isGapful(i) && isSunny(i)) {
                            checkSecondParam++;
                            printProps(i);
                        }
                        i++;
                    }
                }
                break;
        }
        System.out.println();
    }


    private static void printProps(long i) {
        String[] props = new String[7];
        int j = 0;
        if (isBuzz(i)) {
            props[j] = "buzz";
            j++;
        }
        if (isDuck(i)) {
            props[j] = "duck";
            j++;
        }
        if (isPalindrome(i)) {
            props[j] = "palindromic";
            j++;
        }
        if (isGapful(i)) {
            props[j] = "gapful";
            j++;
        }
        if (isSpy(i)) {
            props[j] = "spy";
            j++;
        }
        if (isSquare(i)) {
            props[j] = "square";
            j++;
        }
        if (isSunny(i)) {
            props[j] = "sunny";
            j++;
        }
        if (isEven(i)) {
            props[j] = "even";
            j++;
        }
        if (isOdd(i)) {
            props[j] = "odd";
        }
        StringBuilder strProps = new StringBuilder();
        for (String s : props) {
            if (s != null) {
                strProps.append(s).append(", ");
            }
        }
        System.out.print("\n" + i + " is " + strProps.substring(0, strProps.length() - 2));
    }

    public static void printInstructions() {
        System.out.println();
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and a property to search for;");
        System.out.println("- two natural numbers and two properties to search for;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
        System.out.println();
    }

    public static boolean isNumeric(String string) {
        // Checks if the provided string
        // is a numeric by applying a regular
        // expression on it.
        String regex = "[0-9]+[.]?[0-9]*";
        return Pattern.matches(regex, string);
    }

}//class
