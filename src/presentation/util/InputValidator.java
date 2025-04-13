package presentation.util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,6}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0[3|5|7|8|9])+([0-9]{8})$");
    private static final Scanner scanner = new Scanner(System.in);

    public static String validateEmail(String prompt) {
        String email;
        do {
            System.out.print(prompt);
            email = scanner.nextLine().trim();
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Email không hợp lệ! Hãy nhập lại.");
            }
        } while (!EMAIL_PATTERN.matcher(email).matches());
        return email;
    }

    public static String validatePhoneNumber(String prompt) {
        String phone;
        do {
            System.out.print(prompt);
            phone = scanner.nextLine().trim();
            if (!PHONE_PATTERN.matcher(phone).matches()) {
                System.out.println("Số điện thoại không hợp lệ! Hãy nhập lại.");
            }
        } while (!PHONE_PATTERN.matcher(phone).matches());
        return phone;
    }

    public static int validateInteger(String prompt, int min, int max) {
        int number;
        while (true) {
            try {
                System.out.print(prompt);
                number = Integer.parseInt(scanner.nextLine().trim());
                if (number < min || number > max) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Giá trị không hợp lệ! Vui lòng nhập một số trong khoảng " + min + " - " + max);
            }
        }
    }
}

