package presentation.util;

import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static String readStringWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt + " (Nhấn Enter để giữ nguyên): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    public static String readPassword(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static double readDouble(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                break;
            } else {
                System.out.println("Vui lòng nhập một số thực hợp lệ.");
                scanner.next();
            }
        }
        return value;
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());

                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Vui lòng nhập một số trong khoảng " + min + " - " + max);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void waitForEnter() {
        System.out.println("Nhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

}
