package presentation.controller;

import business.model.Department;
import business.service.DepartmentService;
import presentation.util.Console;

import java.util.List;

public class DepartmentController {
    private DepartmentService departmentService;
    private static final int PAGE_SIZE = 5;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void showDepartmentMenu() {
        int choice;
        do {
            Console.clearScreen();
            Console.println("===== QUẢN LÝ PHÒNG BAN =====");
            Console.println("1. Danh sách phòng ban");
            Console.println("2. Thêm phòng ban mới");
            Console.println("3. Cập nhật phòng ban");
            Console.println("4. Xóa phòng ban");
            Console.println("5. Tìm kiếm phòng ban theo tên");
            Console.println("0. Quay lại");

            choice = Console.readInt("Lựa chọn của bạn (0-5): ",0, 5);

            switch (choice) {
                case 1:
                    showDepartmentList();
                    break;
                case 2:
                    addDepartment();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    searchDepartment();
                    break;
            }
        } while (choice != 0);
    }

    private void showDepartmentList() {
        int currentPage = 1;
        int totalPages = departmentService.getTotalPages(PAGE_SIZE);

        if (totalPages == 0) {
            Console.clearScreen();
            Console.println("Không có phòng ban nào!");
            Console.waitForEnter();
            return;
        }

        boolean exit = false;
        while (!exit) {
            Console.clearScreen();
            Console.println("===== DANH SÁCH PHÒNG BAN =====");
            Console.println("Trang " + currentPage + "/" + totalPages);
            Console.println("");
            Console.println(String.format("%-5s | %-30s | %-50s | %-10s",
                    "ID", "Tên phòng ban", "Mô tả", "Trạng thái"));
            Console.println("-".repeat(100));

            List<Department> departments = departmentService.getAllDepartments(currentPage, PAGE_SIZE);

            for (Department dept : departments) {
                Console.println(String.format("%-5d | %-30s | %-50s | %-10s",
                        dept.getDepartmentId(),
                        dept.getDepartmentName(),
                        (dept.getDescription() != null ? dept.getDescription() : "")
                        ));
            }

            Console.println("\nTrang " + currentPage + "/" + totalPages);
            Console.println("P - Trang trước | N - Trang tiếp | X - Thoát");

            String choice = Console.readString("Lựa chọn của bạn: ").toUpperCase();

            switch (choice) {
                case "P":
                    if (currentPage > 1) {
                        currentPage--;
                    }
                    break;
                case "N":
                    if (currentPage < totalPages) {
                        currentPage++;
                    }
                    break;
                case "X":
                    exit = true;
                    break;
            }
        }
    }

    private void addDepartment() {
        Console.clearScreen();
        Console.println("===== THÊM PHÒNG BAN MỚI =====");
        Console.println("");

        Department department = new Department();

        String name = Console.readString("Tên phòng ban (10-100 ký tự): ");
        while (name.length() < 10 || name.length() > 100) {
            Console.println("Tên phòng ban phải có từ 10-100 ký tự!");
            name = Console.readString("Tên phòng ban (10-100 ký tự): ");
        }
        department.setDepartmentName(name);

        String description = Console.readString("Mô tả (tối đa 255 ký tự, Enter để bỏ qua): ");
        if (!description.isEmpty()) {
            while (description.length() > 255) {
                Console.println("Mô tả không được vượt quá 255 ký tự!");
                description = Console.readString("Mô tả (tối đa 255 ký tự, Enter để bỏ qua): ");
            }
            department.setDescription(description);
        }

        Console.println("\nTrạng thái phòng ban:");
        Console.println("1. Hoạt động");
        Console.println("2. Không hoạt động");

        boolean success = departmentService.createDepartment(department);

        if (success) {
            Console.println("\nThêm phòng ban thành công!");
        } else {
            Console.println("\nThêm phòng ban thất bại! Tên phòng ban có thể đã tồn tại.");
        }

        Console.waitForEnter();
    }

    private void updateDepartment() {
        Console.clearScreen();
        Console.println("===== CẬP NHẬT PHÒNG BAN =====");
        Console.println("");

        int id = Console.readInt("Nhập ID phòng ban cần cập nhật: ",1, Integer.MAX_VALUE);
        Department department = departmentService.getDepartmentById(id);

        if (department == null) {
            Console.println("\nKhông tìm thấy phòng ban với ID " + id);
            Console.waitForEnter();
            return;
        }

        Console.println("\nThông tin hiện tại:");
        Console.println("ID: " + department.getDepartmentId());
        Console.println("Tên phòng ban: " + department.getDepartmentName());
        Console.println("Mô tả: " + (department.getDescription() != null ? department.getDescription() : ""));
        Console.println("\nNhập thông tin mới (Enter để giữ nguyên):");

        String name = Console.readStringWithDefault("Tên phòng ban (10-100 ký tự): ", department.getDepartmentName());
        while (name.length() < 10 || name.length() > 100) {
            Console.println("Tên phòng ban phải có từ 10-100 ký tự!");
            name = Console.readStringWithDefault("Tên phòng ban (10-100 ký tự): ", department.getDepartmentName());
        }
        department.setDepartmentName(name);

        String description = Console.readStringWithDefault("Mô tả (tối đa 255 ký tự): ",
                department.getDescription() != null ? department.getDescription() : "");
        if (!description.isEmpty()) {
            while (description.length() > 255) {
                Console.println("Mô tả không được vượt quá 255 ký tự!");
                description = Console.readStringWithDefault("Mô tả (tối đa 255 ký tự): ",
                        department.getDescription() != null ? department.getDescription() : "");
            }
            department.setDescription(description);
        } else {
            department.setDescription(null);
        }

        Console.println("\nTrạng thái phòng ban:");
        Console.println("1. Hoạt động");
        Console.println("2. Không hoạt động");


        boolean success = departmentService.updateDepartment(department);

        if (success) {
            Console.println("\nCập nhật phòng ban thành công!");
        } else {
            Console.println("\nCập nhật phòng ban thất bại! Tên phòng ban có thể đã tồn tại.");
        }

        Console.waitForEnter();
    }

    private void deleteDepartment() {
        Console.clearScreen();
        Console.println("===== XÓA PHÒNG BAN =====");
        Console.println("");

        int id = Console.readInt("Nhập ID phòng ban cần xóa: ", 1, Integer.MAX_VALUE);
        Department department = departmentService.getDepartmentById(id);

        if (department == null) {
            Console.println("\nKhông tìm thấy phòng ban với ID " + id);
            Console.waitForEnter();
            return;
        }

        Console.println("\nThông tin phòng ban:");
        Console.println("ID: " + department.getDepartmentId());
        Console.println("Tên phòng ban: " + department.getDepartmentName());
        Console.println("Mô tả: " + (department.getDescription() != null ? department.getDescription() : ""));


        String confirm = Console.readString("\nBạn có chắc chắn muốn xóa phòng ban này? (Y/N): ").toUpperCase();

        if (confirm.equals("Y")) {
            boolean success = departmentService.deleteDepartment(id);

            if (success) {
                Console.println("\nXóa phòng ban thành công!");
            } else {
                Console.println("\nXóa phòng ban thất bại! Phòng ban có thể đang có nhân viên.");
            }
        } else {
            Console.println("\nĐã hủy xóa phòng ban.");
        }

        Console.waitForEnter();
    }

    private void searchDepartment() {
        Console.clearScreen();
        Console.println("===== TÌM KIẾM PHÒNG BAN =====");
        Console.println("");

        String keyword = Console.readString("Nhập tên phòng ban cần tìm: ");

        List<Department> results = departmentService.searchDepartmentsByName(keyword);

        Console.println("\nKết quả tìm kiếm:");
        if (results.isEmpty()) {
            Console.println("Không tìm thấy phòng ban nào!");
        } else {
            Console.println(String.format("%-5s | %-30s | %-50s | %-10s",
                    "ID", "Tên phòng ban", "Mô tả", "Trạng thái"));
            Console.println("-".repeat(100));

            for (Department dept : results) {
                Console.println(String.format("%-5d | %-30s | %-50s | %-10s",
                        dept.getDepartmentId(),
                        dept.getDepartmentName(),
                        (dept.getDescription() != null ? dept.getDescription() : "")
                ));
            }
        }

        Console.waitForEnter();
    }
}