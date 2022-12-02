import java.util.Scanner;

public class SafeInput {
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String retVal = "";
        boolean done = false;

        do {
            System.out.println("\n" + prompt + ": ");
            retVal = pipe.nextLine();
            if (retVal.matches(regEx)) {
                done = true;
            } else {
                System.out.println("\n" + retVal + " must match the pattern " + regEx);
                System.out.println("Try again!");
            }
        } while (!done);
        return retVal;
    }

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        boolean done = false;
        String retVal = "";
        do {
            System.out.println("\n" + prompt + ": ");
            retVal = pipe.nextLine();
            if (retVal.equals("")) {
                System.out.println("Please try again");
            } else {
                done = true;
            }
        } while (!done);
        return retVal;
    }

    public static int getInt(Scanner pipe, String prompt) {
        boolean done = false;
        int retVal = 0;
        String trash = "";
        do {
            System.out.println("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You entered the invalid input: " + trash);
            }
        } while (!done);
        return retVal;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        boolean done = false;
        double retVal = 0;
        String trash = "";
        do {
            System.out.println("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You entered the invalid input: " + trash);
            }
        } while (!done);
        return retVal;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        boolean done = false;
        int retVal;
        do {
            retVal = SafeInput.getInt(pipe, prompt + " [" + low + "-" + high + "]");
            if (retVal >= low && retVal <= high) {
                done = true;
            } else {
                System.out.println("The number was not between the values [" + low + "-" + high + "]");
            }
        } while (!done);
        return retVal;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        boolean done = false;
        double retVal;
        do {
            retVal = SafeInput.getDouble(pipe, prompt + " [" + low + "-" + high + "]");
            if (retVal >= low && retVal <= high) {
                done = true;
            } else {
                System.out.println("The number was not between the values [" + low + "-" + high + "]");
            }
        } while (!done);
        return retVal;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        boolean done = false;
        boolean retVal = false;
        String trash = "";
        String answer = "";
        do {
            answer = SafeInput.getNonZeroLenString(pipe, prompt);
            if (answer.equalsIgnoreCase("y")) {
                done = true;
            } else if (answer.equalsIgnoreCase("n")) {
                done = true;
                retVal = true;
            } else {
                System.out.println("You have entered an invalid input");
            }
        } while (!done);
        return retVal;
    }

    public static void prettyHeader(Scanner pipe, String prompt) {
        String message = SafeInput.getNonZeroLenString(pipe, prompt);
        int messageLength = message.length();
        int messageOffset = 27 - (messageLength / 2);
        System.out.println("\n");
        for (int i = 1; i <= 60; i++) {
            System.out.print("*");
        }
        System.out.print("\n***");
        for (int i = 1; i <= messageOffset; i++) {
            System.out.print(" ");
        }
        System.out.print(message);
        for (int i = 1; i <= messageOffset; i++) {
            System.out.print(" ");
        }
        System.out.println("***");
        for (int i = 1; i <= 60; i++) {
            System.out.print("*");
        }
        System.out.println("");
    }
}
