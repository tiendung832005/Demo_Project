package presentation.controller;

import business.model.Account;
import business.service.AccountService;
import presentation.util.Console;


public class AuthController {
    private AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean login() {
        Console.clearScreen();
        Console.println("===== ĐĂNG NHẬP HỆ THỐNG =====");
        Console.println("");

        String username = Console.readString("Tên đăng nhập: ");
        String password = Console.readPassword("Mật khẩu: ");

        boolean success = accountService.login(username, password);

        if (success) {
            Console.println("\nĐăng nhập thành công!");
            Console.println("Xin chào, " + accountService.getCurrentAccount().getUsername());
            Console.waitForEnter();
            return true;
        } else {
            Console.println("\nĐăng nhập thất bại! Tên đăng nhập hoặc mật khẩu không đúng.");
            Console.waitForEnter();
            return false;
        }
    }

    public void logout() {
        accountService.logout();
        Console.println("Đã đăng xuất khỏi hệ thống.");
        Console.waitForEnter();
    }

    public void registerNewAccount() {
        if (!accountService.isAdmin()) {
            Console.println("Bạn không có quyền thực hiện chức năng này!");
            Console.waitForEnter();
            return;
        }

        Console.clearScreen();
        Console.println("===== ĐĂNG KÝ TÀI KHOẢN MỚI =====");
        Console.println("");

        String username = Console.readString("Tên đăng nhập: ");
        String password = Console.readPassword("Mật khẩu: ");
        String confirmPassword = Console.readPassword("Xác nhận mật khẩu: ");

        if (!password.equals(confirmPassword)) {
            Console.println("\nMật khẩu không khớp!");
            Console.waitForEnter();
            return;
        }

        Console.println("\nChọn vai trò:");
        Console.println("1. Admin");
        Console.println("2. HR");
        int role = Console.readInt("Chọn (1-2): ",1, 2);

        Account.AccountRole accountRole = (role == 1) ? Account.AccountRole.ADMIN : Account.AccountRole.HR;

        boolean success = accountService.register(username, password, accountRole);

        if (success) {
            Console.println("\nĐăng ký tài khoản thành công!");
        } else {
            Console.println("\nĐăng ký tài khoản thất bại! Tên đăng nhập đã tồn tại.");
        }

        Console.waitForEnter();
    }
}
