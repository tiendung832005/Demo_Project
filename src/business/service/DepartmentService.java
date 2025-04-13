package business.service;

import business.dao.DepartmentDAO;
import business.model.Department;

import java.util.List;

public class DepartmentService {
    private DepartmentDAO departmentDAO;

    public DepartmentService() {
        this.departmentDAO = new DepartmentDAO();
    }

    public List<Department> getAllDepartments(int page, int pageSize) {
        return departmentDAO.findAll(page, pageSize);
    }

    public int getTotalPages(int pageSize) {
        int totalDepartments = departmentDAO.countAll();
        return (int) Math.ceil((double) totalDepartments / pageSize);
    }

    public Department getDepartmentById(int departmentId) {
        return departmentDAO.findById(departmentId);
    }

    public List<Department> searchDepartmentsByName(String name) {
        return departmentDAO.findByName(name);
    }

    public List<Department> getActiveDepartments() {
        return departmentDAO.findActiveDepartments();
    }

    public boolean createDepartment(Department department) {
        if (department.getDepartmentName() == null || department.getDepartmentName().trim().isEmpty()) {
            return false;
        }

        if (department.getDepartmentName().length() < 10 || department.getDepartmentName().length() > 100) {
            return false;
        }

        if (department.getDescription() != null && department.getDescription().length() > 255) {
            return false;
        }

        if (departmentDAO.isNameExists(department.getDepartmentName())) {
            return false;
        }

        return departmentDAO.create(department);
    }

    public boolean updateDepartment(Department department) {
        if (department.getDepartmentName() == null || department.getDepartmentName().trim().isEmpty()) {
            return false;
        }

        if (department.getDepartmentName().length() < 10 || department.getDepartmentName().length() > 100) {
            return false;
        }

        if (department.getDescription() != null && department.getDescription().length() > 255) {
            return false;
        }

        if (departmentDAO.isNameExistsExcludingId(department.getDepartmentName(), department.getDepartmentId())) {
            return false;
        }

        return departmentDAO.update(department);
    }

    public boolean deleteDepartment(int departmentId) {
        if (departmentDAO.hasEmployees(departmentId)) {
            return false;
        }

        return departmentDAO.delete(departmentId);
    }

}