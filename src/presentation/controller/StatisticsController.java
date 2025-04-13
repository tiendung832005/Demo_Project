package presentation.controller;

import business.service.EmployeeService;
import business.service.DepartmentService;
import business.model.Department;
import business.model.Employee;

import java.util.Map;

public class StatisticsController {
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    public StatisticsController() {
        this.employeeService = new EmployeeService();
        this.departmentService = new DepartmentService();
    }

    // Hiển thị số lượng nhân viên theo từng phòng ban
    public void displayEmployeeCountByDepartment() {
        Map<Integer, Integer> employeeCountByDepartment = employeeService.getEmployeeCountByDepartment();

        if (employeeCountByDepartment.isEmpty()) {
            System.out.println("Không có dữ liệu nhân viên.");
        } else {
            System.out.println("Số lượng nhân viên theo từng phòng ban:");
            for (Map.Entry<Integer, Integer> entry : employeeCountByDepartment.entrySet()) {
                Department department = departmentService.getDepartmentById(entry.getKey());
                System.out.println("Phòng ban: " + department.getDepartmentName() + " - Số nhân viên: " + entry.getValue());
            }
        }
    }

    // Hiển thị tổng số nhân viên trong hệ thống
    public void displayTotalEmployeeCount() {
        int totalEmployees = employeeService.getTotalEmployees();
        System.out.println("Tổng số nhân viên của toàn bộ hệ thống: " + totalEmployees);
    }

    // Hiển thị phòng ban có nhiều nhân viên nhất
    public void displayDepartmentWithMostEmployees() {
        Department department = employeeService.getDepartmentWithMostEmployees();
        if (department != null) {
            System.out.println("Phòng ban có nhiều nhân viên nhất: " + department.getDepartmentName());
        } else {
            System.out.println("Không có phòng ban nào có nhân viên.");
        }
    }

    // Hiển thị phòng ban có lương cao nhất
    public void displayDepartmentWithHighestSalary() {
        Department department = employeeService.getDepartmentWithHighestSalary();
        if (department != null) {
            System.out.println("Phòng ban có lương cao nhất: " + department.getDepartmentName());
        } else {
            System.out.println("Không có phòng ban nào có lương cao.");
        }
    }

    // Hiển thị tất cả thống kê
    public void displayStatistics() {
        displayEmployeeCountByDepartment();
        displayTotalEmployeeCount();
        displayDepartmentWithMostEmployees();
        displayDepartmentWithHighestSalary();
    }
}
