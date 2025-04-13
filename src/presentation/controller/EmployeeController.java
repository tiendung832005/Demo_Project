package presentation.controller;

import business.model.Employee;
import business.model.Department;
import business.service.EmployeeService;
import business.service.DepartmentService;
import presentation.util.Console;

import java.util.List;

public class EmployeeController {

    private static final EmployeeService employeeService = new EmployeeService();
    private static final DepartmentService departmentService = new DepartmentService();
    private static Runtime.Version LocalDate;
    private static String departmentName;

    public static void showMainMenu() {
        while (true) {
            Console.clearScreen();
            System.out.println("== Chương trình quản lý nhân viên ==");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng xuất");
            System.out.println("3. Quản lý phòng ban");
            System.out.println("4. Quản lý nhân viên");
            System.out.println("5. Thống kê");
            System.out.println("0. Thoát");
            int choice = Console.readInt("Chọn chức năng: ", 0,5);

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    logout();
                    break;
                case 3:
                    manageDepartments();
                    break;
                case 4:
                    manageEmployees();
                    break;
                case 5:
                    viewStatistics();
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }

    private static void login() {
        String username = Console.readString("Nhập tên đăng nhập: ");
        String password = Console.readPassword("Nhập mật khẩu: ");
        if (authenticate(username, password)) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
        }
        Console.waitForEnter();
    }

    private static void logout() {
        System.out.println("Đăng xuất thành công!");
        Console.waitForEnter();
    }

    private static boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    private static void manageDepartments() {
        while (true) {
            Console.clearScreen();
            System.out.println("== Quản lý phòng ban ==");
            System.out.println("1. Danh sách phòng ban");
            System.out.println("2. Thêm phòng ban");
            System.out.println("3. Cập nhật phòng ban");
            System.out.println("4. Xóa phòng ban");
            System.out.println("5. Tìm kiếm phòng ban");
            System.out.println("0. Quay lại");
            int choice = Console.readInt("Chọn chức năng: ",0, 5);

            switch (choice) {
                case 1:
                    listDepartments();
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
                case 0:
                    return;
            }
        }
    }

    private static void listDepartments() {
        int page = 1;
        int pageSize = 5;
        List<Department> departments = departmentService.getAllDepartments(page, pageSize);
        System.out.println("Danh sách phòng ban:");
        for (Department department : departments) {
            System.out.println(department);
        }
        Console.waitForEnter();
    }

    private static void addDepartment() {
        String name = Console.readString("Nhập tên phòng ban: ");
        Department department = new Department(departmentName, name);
        if (departmentService.createDepartment(department)) {
            System.out.println("Thêm phòng ban thành công!");
        } else {
            System.out.println("Thêm phòng ban thất bại!");
        }
        Console.waitForEnter();
    }

    private static void updateDepartment() {
        int departmentId = Console.readInt("Nhập mã phòng ban cần cập nhật: ",1, Integer.MAX_VALUE);
        Department department = departmentService.getDepartmentById(departmentId);
        if (department == null) {
            System.out.println("Phòng ban không tồn tại.");
            Console.waitForEnter();
            return;
        }
        String newName = Console.readStringWithDefault("Nhập tên mới cho phòng ban (nhấn Enter để giữ nguyên): ", department.getName());
        department.setName(newName);
        if (departmentService.updateDepartment(department)) {
            System.out.println("Cập nhật phòng ban thành công!");
        } else {
            System.out.println("Cập nhật phòng ban thất bại!");
        }
        Console.waitForEnter();
    }

    private static void deleteDepartment() {
        int departmentId = Console.readInt("Nhập mã phòng ban cần xóa: ",1, Integer.MAX_VALUE);
        if (departmentService.deleteDepartment(departmentId)) {
            System.out.println("Xóa phòng ban thành công!");
        } else {
            System.out.println("Không thể xóa phòng ban, phòng ban có nhân viên.");
        }
        Console.waitForEnter();
    }

    private static void searchDepartment() {
        String name = Console.readString("Nhập tên phòng ban cần tìm: ");
        List<Department> departments = departmentService.searchDepartmentsByName(name);
        if (departments.isEmpty()) {
            System.out.println("Không tìm thấy phòng ban nào.");
        } else {
            departments.forEach(System.out::println);
        }
        Console.waitForEnter();
    }

    private static void manageEmployees() {
        while (true) {
            Console.clearScreen();
            System.out.println("== Quản lý nhân viên ==");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm nhân viên");
            System.out.println("3. Cập nhật nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println("6. Sắp xếp nhân viên");
            System.out.println("0. Quay lại");
            int choice = Console.readInt("Chọn chức năng: ",0,6);

            switch (choice) {
                case 1:
                    listEmployees();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    searchEmployee();
                    break;
                case 6:
                    sortEmployees();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void listEmployees() {
        int page = 1;
        int pageSize = 10;
        List<Employee> employees = employeeService.getAllEmployees(page, pageSize);
        System.out.println("Danh sách nhân viên:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        Console.waitForEnter();
    }

    private static void addEmployee() {
        String employeeId = Console.readString("Nhập mã nhân viên: ");
        String employeeName = Console.readString("Nhập tên nhân viên: ");
        String email = Console.readString("Nhập email nhân viên: ");
        String phone = Console.readString("Nhập số điện thoại: ");
        int salaryLevel = Console.readInt("Nhập mức lương: ", 1, Integer.MAX_VALUE);
        double salary = Console.readDouble("Nhập lương: ");
        String birthDate = Console.readString("Nhập ngày sinh (yyyy-MM-dd): ");
        String address = Console.readString("Nhập địa chỉ: ");
        int departmentId = Console.readInt("Nhập mã phòng ban: ",1, Integer.MAX_VALUE);

        Employee employee = new Employee(employeeId, employeeName, email, phone, Employee.Gender.MALE, salaryLevel, salary, LocalDate.parse(birthDate), address, Employee.EmployeeStatus.ACTIVE, departmentId);

        if (employeeService.createEmployee(employee)) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Thêm nhân viên thất bại!");
        }
        Console.waitForEnter();
    }

    private static void updateEmployee() {
        String employeeId = Console.readString("Nhập mã nhân viên cần cập nhật: ");
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Nhân viên không tồn tại.");
            Console.waitForEnter();
            return;
        }
        Console.waitForEnter();
    }

    private static void deleteEmployee() {
        String employeeId = Console.readString("Nhập mã nhân viên cần xóa: ");
        if (employeeService.deleteEmployee(employeeId)) {
            System.out.println("Xóa nhân viên thành công!");
        } else {
            System.out.println("Xóa nhân viên thất bại!");
        }
        Console.waitForEnter();
    }

    private static void searchEmployee() {
        String name = Console.readString("Nhập tên nhân viên cần tìm: ");
        List<Employee> employees = employeeService.searchEmployeesByName(name);
        if (employees.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên nào.");
        } else {
            employees.forEach(System.out::println);
        }
        Console.waitForEnter();
    }

    private static void sortEmployees() {
        System.out.println("1. Sắp xếp theo lương giảm dần");
        System.out.println("2. Sắp xếp theo tên tăng dần");
        int choice = Console.readInt("Chọn cách sắp xếp: ",1,2);
        List<Employee> employees;
        if (choice == 1) {
            employees = employeeService.getEmployeesSortedBySalaryDesc();
        } else {
            employees = employeeService.getEmployeesSortedByNameAsc();
        }
        employees.forEach(System.out::println);
        Console.waitForEnter();
    }

    private static void viewStatistics() {
        // Chức năng thống kê
        System.out.println("== Thống kê ==");
        System.out.println("1. Số lượng nhân viên theo từng phòng ban");
        System.out.println("2. Tổng số nhân viên của toàn bộ hệ thống");
        System.out.println("3. Phòng ban có nhiều nhân viên nhất");
        System.out.println("4. Phòng ban có lương cao nhất");
        System.out.println("0. Quay lại");

        int choice = Console.readInt("Chọn chức năng thống kê: ",0,4);
        switch (choice) {
            case 1:
                System.out.println(employeeService.getEmployeeCountByDepartment());
                break;
            case 2:
                System.out.println("Tổng số nhân viên: " + employeeService.getTotalEmployees());
                break;
            case 3:
                Department departmentWithMostEmployees = employeeService.getDepartmentWithMostEmployees();
                System.out.println("Phòng ban có nhiều nhân viên nhất: " + departmentWithMostEmployees);
                break;
            case 4:
                Department departmentWithHighestSalary = employeeService.getDepartmentWithHighestSalary();
                System.out.println("Phòng ban có lương cao nhất: " + departmentWithHighestSalary);
                break;
            case 0:
                return;
        }
        Console.waitForEnter();
    }
}
