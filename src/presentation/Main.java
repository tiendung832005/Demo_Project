package presentation;

import business.model.Employee;
import business.model.Department;
import business.service.EmployeeService;
import business.service.DepartmentService;
import presentation.util.Console;
import presentation.util.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static presentation.util.Console.clearScreen;
import static presentation.util.Console.waitForEnter;

public class Main {
    private static EmployeeService employeeService = new EmployeeService();
    private static DepartmentService departmentService = new DepartmentService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            clearScreen();
            Console.println("== QUẢN LÝ NHÂN VIÊN ==");
            Console.println("1. Quản lý phòng ban");
            Console.println("2. Quản lý nhân viên");
            Console.println("3. Thống kê");
            Console.println("0. Thoát");
            choice = scanner.nextInt(); // Sử dụng scanner để đọc đầu vào

            switch (choice) {
                case 1:
                    manageDepartments();
                    break;
                case 2:
                    manageEmployees();
                    break;
                case 3:
                    showStatistics();
                    break;
                case 0:
                    Console.println("Chương trình kết thúc!");
                    break;
                default:
                    Console.println("Chức năng không hợp lệ! Vui lòng thử lại.");
                    waitForEnter();
                    break;
            }
        } while (choice != 0);
    }

    private static void manageDepartments() {
        int choice;
        do {
            clearScreen();
            System.out.println("== QUẢN LÝ PHÒNG BAN ==");
            System.out.println("1. Danh sách phòng ban");
            System.out.println("2. Thêm mới phòng ban");
            System.out.println("3. Cập nhật phòng ban");
            System.out.println("4. Xóa phòng ban");
            System.out.println("5. Tìm kiếm phòng ban theo tên");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();

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
                    searchDepartmentByName();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ! Vui lòng thử lại.");
                    waitForEnter();
                    break;
            }
        } while (choice != 0);
    }

    private static void manageEmployees() {
        int choice;
        do {
            clearScreen();
            System.out.println("== QUẢN LÝ NHÂN VIÊN ==");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Cập nhật nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên theo tên");
            System.out.println("6. Tìm kiếm nhân viên theo khoảng tuổi");
            System.out.println("7. Sắp xếp nhân viên theo lương");
            System.out.println("8. Sắp xếp nhân viên theo tên");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();

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
                    searchEmployeeByName();
                    break;
                case 6:
                    searchEmployeeByAgeRange();
                    break;
                case 7:
                    sortEmployeesBySalary();
                    break;
                case 8:
                    sortEmployeesByName();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ! Vui lòng thử lại.");
                    waitForEnter();
                    break;
            }
        } while (choice != 0);
    }

    private static void showStatistics() {
        clearScreen();
        System.out.println("== THỐNG KÊ ==");
        System.out.println("1. Số lượng nhân viên theo từng phòng ban");
        System.out.println("2. Tổng số nhân viên");
        System.out.println("3. Phòng ban có nhiều nhân viên nhất");
        System.out.println("4. Phòng ban có lương cao nhất");
        System.out.println("0. Quay lại");
        System.out.print("Chọn chức năng: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                showEmployeeCountByDepartment();
                break;
            case 2:
                showTotalEmployees();
                break;
            case 3:
                showDepartmentWithMostEmployees();
                break;
            case 4:
                showDepartmentWithHighestSalary();
                break;
            case 0:
                break;
            default:
                System.out.println("Chức năng không hợp lệ! Vui lòng thử lại.");
                waitForEnter();
                break;
        }
    }

    private static void listDepartments() {
        List<Department> departments = departmentService.getAllDepartments(1, 5); // Example: page 1, 5 items per page
        for (Department department : departments) {
            Console.println(department.toString());
        }
        waitForEnter();
    }

    private static void addDepartment() {
        String departmentName = Console.readString("Nhập tên phòng ban: ");
        String departmentDescription = Console.readString("Nhập mô tả phòng ban: ");
        Department department = new Department(departmentName, departmentDescription);
        departmentService.createDepartment(department);
        Console.println("Đã thêm mới phòng ban!");
        waitForEnter();
    }

    private static void updateDepartment() {
        String departmentId = Console.readString("Nhập ID phòng ban cần cập nhật: ");
        Department department = departmentService.getDepartmentById(Integer.parseInt(departmentId));
        if (department != null) {
            String newName = Console.readStringWithDefault("Nhập tên mới phòng ban (để trống nếu không thay đổi): ", department.getDepartmentName());
            String newDescription = Console.readStringWithDefault("Nhập mô tả mới phòng ban (để trống nếu không thay đổi): ", department.getDepartmentDescription());
            department.setDepartmentName(newName);
            department.setDepartmentDescription(newDescription);
            departmentService.updateDepartment(department);
            Console.println("Đã cập nhật phòng ban!");
        } else {
            Console.println("Không tìm thấy phòng ban với ID đã nhập.");
        }
        waitForEnter();
    }

    private static void deleteDepartment() {
        int departmentId = Console.readInt("Nhập ID phòng ban cần xóa: ", 1, Integer.MAX_VALUE); // Thêm min và max
        if (departmentService.deleteDepartment(departmentId)) {
            Console.println("Đã xóa phòng ban!");
        } else {
            Console.println("Không thể xóa phòng ban có nhân viên.");
        }
        waitForEnter();
    }


    private static void searchDepartmentByName() {
        String name = Console.readString("Nhập tên phòng ban để tìm kiếm: ");
        List<Department> departments = departmentService.searchDepartmentsByName(name);
        for (Department department : departments) {
            Console.println(department.toString());
        }
        waitForEnter();
    }

    private static void listEmployees() {
        List<Employee> employees = employeeService.getAllEmployees(1, 10); // Example: page 1, 10 items per page
        for (Employee employee : employees) {
            Console.println(employee.toString());
        }
        waitForEnter();
    }

    private static void addEmployee() {
        String employeeId = Console.readString("Nhập ID nhân viên: ");
        String employeeName = Console.readString("Nhập tên nhân viên: ");
        String email = InputValidator.validateEmail("Nhập email: ");
        String phone = InputValidator.validatePhoneNumber("Nhập số điện thoại: ");
        Employee.Gender gender = Employee.Gender.valueOf(Console.readString("Nhập giới tính (MALE, FEMALE, OTHER): ").toUpperCase());
        int salaryLevel = Console.readInt("Nhập cấp bậc lương: ", 1, Integer.MAX_VALUE); // Thêm min và max
        double salary = Console.readDouble("Nhập lương: ");
        String address = Console.readString("Nhập địa chỉ: ");
        int departmentId = Console.readInt("Nhập ID phòng ban (Chỉ thêm vào phòng ban có trạng thái ACTIVE): ", 1, Integer.MAX_VALUE); // Thêm min và max
        Employee employee = new Employee(employeeId, employeeName, email, phone, gender, salaryLevel, salary, LocalDate.now(), address, Employee.EmployeeStatus.ACTIVE, departmentId);
        employeeService.createEmployee(employee);
        Console.println("Đã thêm mới nhân viên!");
        waitForEnter();
    }

    private static void updateEmployee() {
        String employeeId = Console.readString("Nhập ID nhân viên cần cập nhật: ");
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
        } else {
            Console.println("Không tìm thấy nhân viên với ID đã nhập.");
        }
        waitForEnter();
    }

    private static void deleteEmployee() {
        String employeeId = Console.readString("Nhập ID nhân viên cần xóa: ");
        if (employeeService.deleteEmployee(employeeId)) {
            Console.println("Đã xóa nhân viên!");
        } else {
            Console.println("Không thể xóa nhân viên.");
        }
        waitForEnter();
    }

    private static void searchEmployeeByName() {
        String name = Console.readString("Nhập tên nhân viên để tìm kiếm: ");
        List<Employee> employees = employeeService.searchEmployeesByName(name);
        for (Employee employee : employees) {
            Console.println(employee.toString());
        }
        waitForEnter();
    }

    private static void searchEmployeeByAgeRange() {
        int minAge = Console.readInt("Nhập tuổi tối thiểu: ", 1, 150); // Thêm min và max
        int maxAge = Console.readInt("Nhập tuổi tối đa: ", minAge, 150); // Thêm min và max
        List<Employee> employees = employeeService.searchEmployeesByAgeRange(minAge, maxAge);
        for (Employee employee : employees) {
            Console.println(employee.toString());
        }
        waitForEnter();
    }


    private static void sortEmployeesBySalary() {
        List<Employee> employees = employeeService.getEmployeesSortedBySalaryDesc();
        for (Employee employee : employees) {
            Console.println(employee.toString());
        }
        waitForEnter();
    }

    private static void sortEmployeesByName() {
        List<Employee> employees = employeeService.getEmployeesSortedByNameAsc();
        for (Employee employee : employees) {
            Console.println(employee.toString());
        }
        waitForEnter();
    }

    private static void showEmployeeCountByDepartment() {
    }

    private static void showTotalEmployees() {
        Console.println("Tổng số nhân viên: " + employeeService.getTotalEmployees());
        waitForEnter();
    }

    private static void showDepartmentWithMostEmployees() {
        Department department = employeeService.getDepartmentWithMostEmployees();
        Console.println("Phòng ban có nhiều nhân viên nhất: " + department.getDepartmentName());
        waitForEnter();
    }

    private static void showDepartmentWithHighestSalary() {
        Department department = employeeService.getDepartmentWithHighestSalary();
        Console.println("Phòng ban có lương cao nhất: " + department.getDepartmentName());
        waitForEnter();
    }
}
