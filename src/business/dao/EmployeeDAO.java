package business.dao;

import business.config.DatabaseConfig;
import business.model.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EmployeeDAO {

    public List<Employee> findAll(int page, int pageSize) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int offset = (page - 1) * pageSize;
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public int countAll() {
        String query = "SELECT COUNT(*) FROM Employee";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Employee findById(String employeeId) {
        String query = "SELECT * FROM Employee WHERE employee_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Employee> findByName(String name) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE employee_name LIKE ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Employee> findByAgeRange(int minAge, int maxAge) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE YEAR(CURRENT_DATE) - YEAR(birth_date) BETWEEN ? AND ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, minAge);
            stmt.setInt(2, maxAge);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Employee> findAllSortedBySalaryDesc() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee ORDER BY salary DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Employee> findAllSortedByNameAsc() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee ORDER BY employee_name ASC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public boolean create(Employee employee) {
        String query = "INSERT INTO Employee (employee_id, employee_name, email, phone, gender, " +
                "salary_level, salary, birth_date, address, status, department_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getEmployeeName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getGender().toString());
            stmt.setInt(6, employee.getSalaryLevel());
            stmt.setDouble(7, employee.getSalary());
            stmt.setDate(8, Date.valueOf(employee.getBirthDate()));
            stmt.setString(9, employee.getAddress());
            stmt.setString(10, employee.getStatus().toString());
            stmt.setInt(11, employee.getDepartmentId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Employee employee) {
        String query = "UPDATE Employee SET employee_name = ?, email = ?, phone = ?, gender = ?, " +
                "salary_level = ?, salary = ?, birth_date = ?, address = ?, status = ?, " +
                "department_id = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employee.getEmployeeName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getPhone());
            stmt.setString(4, employee.getGender().toString());
            stmt.setInt(5, employee.getSalaryLevel());
            stmt.setDouble(6, employee.getSalary());
            stmt.setDate(7, Date.valueOf(employee.getBirthDate()));
            stmt.setString(8, employee.getAddress());
            stmt.setString(9, employee.getStatus().toString());
            stmt.setInt(10, employee.getDepartmentId());
            stmt.setString(11, employee.getEmployeeId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLogically(String employeeId) {
        String query = "UPDATE Employee SET status = ? WHERE employee_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, Employee.EmployeeStatus.INACTIVE.toString());
            stmt.setString(2, employeeId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM Employee WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isEmailExistsExcludingId(String email, String employeeId) {
        String query = "SELECT COUNT(*) FROM Employee WHERE email = ? AND employee_id != ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isPhoneExists(String phone) {
        String query = "SELECT COUNT(*) FROM Employee WHERE phone = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isPhoneExistsExcludingId(String phone, String employeeId) {
        String query = "SELECT COUNT(*) FROM Employee WHERE phone = ? AND employee_id != ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, phone);
            stmt.setString(2, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isIdExists(String employeeId) {
        String query = "SELECT COUNT(*) FROM Employee WHERE employee_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Map<Integer, Integer> countEmployeesByDepartment() {
        Map<Integer, Integer> result = new HashMap<>();
        String query = "SELECT department_id, COUNT(*) as count FROM Employee GROUP BY department_id";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                result.put(rs.getInt("department_id"), rs.getInt("count"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getTotalEmployees() {
        String query = "SELECT COUNT(*) FROM Employee";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getDepartmentWithMostEmployees() {
        String query = "SELECT department_id FROM Employee GROUP BY department_id " +
                "ORDER BY COUNT(*) DESC LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("department_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getDepartmentWithHighestSalary() {
        String query = "SELECT department_id FROM Employee GROUP BY department_id " +
                "ORDER BY SUM(salary) DESC LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("department_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        String employeeId = rs.getString("employee_id");
        String employeeName = rs.getString("employee_name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        Employee.Gender gender = Employee.Gender.valueOf(rs.getString("gender"));
        int salaryLevel = rs.getInt("salary_level");
        double salary = rs.getDouble("salary");
        LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
        String address = rs.getString("address");
        Employee.EmployeeStatus status = Employee.EmployeeStatus.valueOf(rs.getString("status"));
        int departmentId = rs.getInt("department_id");

        Employee employee = new Employee(employeeId, employeeName, email, phone, gender, salaryLevel, salary, birthDate, address, status, departmentId);

        return employee;
    }

}