package business.service;

import business.dao.DepartmentDAO;
import business.dao.EmployeeDAO;
import business.model.Department;
import business.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class EmployeeService {
    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;

    // Regex patterns for validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0[3-9][0-9]{8})$"); // Vietnamese mobile phone format
    private static final Pattern EMPLOYEE_ID_PATTERN = Pattern.compile("^E[0-9]{4}$");

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
        this.departmentDAO = new DepartmentDAO();
    }

    public List<Employee> getAllEmployees(int page, int pageSize) {
        return employeeDAO.findAll(page, pageSize);
    }

    public int getTotalPages(int pageSize) {
        int totalEmployees = employeeDAO.countAll();
        return (int) Math.ceil((double) totalEmployees / pageSize);
    }

    public Employee getEmployeeById(String employeeId) {
        return employeeDAO.findById(employeeId);
    }

    public List<Employee> searchEmployeesByName(String name) {
        return employeeDAO.findByName(name);
    }

    public List<Employee> searchEmployeesByAgeRange(int minAge, int maxAge) {
        return employeeDAO.findByAgeRange(minAge, maxAge);
    }

    public List<Employee> getEmployeesSortedBySalaryDesc() {
        return employeeDAO.findAllSortedBySalaryDesc();
    }

    public List<Employee> getEmployeesSortedByNameAsc() {
        return employeeDAO.findAllSortedByNameAsc();
    }

    public boolean createEmployee(Employee employee) {


        // Validate employee data
        if (!validateEmployeeData(employee)) {
            return false;
        }

        // Check duplicated employee ID
        if (employeeDAO.isIdExists(employee.getEmployeeId())) {
            return false;
        }

        // Check duplicated email
        if (employeeDAO.isEmailExists(employee.getEmail())) {
            return false;
        }

        // Check duplicated phone
        if (employeeDAO.isPhoneExists(employee.getPhone())) {
            return false;
        }

        return employeeDAO.create(employee);
    }

    public boolean updateEmployee(Employee employee) {
        Employee existingEmployee = employeeDAO.findById(employee.getEmployeeId());
        if (existingEmployee == null) {
            return false;
        }


        if (!validateEmployeeData(employee)) {
            return false;
        }

        if (employeeDAO.isEmailExistsExcludingId(employee.getEmail(), employee.getEmployeeId())) {
            return false;
        }

        if (employeeDAO.isPhoneExistsExcludingId(employee.getPhone(), employee.getEmployeeId())) {
            return false;
        }

        return employeeDAO.update(employee);
    }

    public boolean deleteEmployee(String employeeId) {
        return employeeDAO.deleteLogically(employeeId);
    }

    public Map<Integer, Integer> getEmployeeCountByDepartment() {
        return employeeDAO.countEmployeesByDepartment();
    }

    public int getTotalEmployees() {
        return employeeDAO.getTotalEmployees();
    }

    public Department getDepartmentWithMostEmployees() {
        int departmentId = employeeDAO.getDepartmentWithMostEmployees();
        return departmentDAO.findById(departmentId);
    }

    public Department getDepartmentWithHighestSalary() {
        int departmentId = employeeDAO.getDepartmentWithHighestSalary();
        return departmentDAO.findById(departmentId);
    }

    private boolean validateEmployeeData(Employee employee) {
        // Validate employee ID format
        if (!EMPLOYEE_ID_PATTERN.matcher(employee.getEmployeeId()).matches()) {
            return false;
        }

        if (employee.getEmployeeName() == null || employee.getEmployeeName().trim().isEmpty() ||
                employee.getEmployeeName().length() < 15 || employee.getEmployeeName().length() > 150) {
            return false;
        }

        if (employee.getEmail() == null || !EMAIL_PATTERN.matcher(employee.getEmail()).matches()) {
            return false;
        }

        if (employee.getPhone() == null || !PHONE_PATTERN.matcher(employee.getPhone()).matches()) {
            return false;
        }

        if (employee.getSalaryLevel() <= 0) {
            return false;
        }

        if (employee.getSalary() <= 0) {
            return false;
        }

        if (employee.getBirthDate() == null) {
            return false;
        }

        if (employee.getAddress() == null || employee.getAddress().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}