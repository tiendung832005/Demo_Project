package business.model;

public class Department {
    private int departmentId;
    private String departmentName;
    private String description;

    public void setName(String newName) {
    }

    public String getName() {
        return null;
    }

    private String departmentDescription;

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }


    public enum DepartmentStatus{
        ACTIVE, INACTIVE
    }

    public Department(){}

    public Department(String name, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
