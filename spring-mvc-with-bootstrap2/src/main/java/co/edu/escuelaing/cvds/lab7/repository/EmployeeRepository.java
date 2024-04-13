package co.edu.escuelaing.cvds.lab7.repository;


import co.edu.escuelaing.cvds.lab7.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    public List<Employee> findByFirstName(String firstName);

    // @Query("SELECT e FROM employee e WHERE e.first_name LIKE %:first_name% OR e.last_name LIKE %:last_name%")
    // List<Employee> searchByFirstNameOrLastName(@Param("first_name") String firstName, @Param("last_name") String lastName);
    public Employee findByEmployeeId(String employeeId);
}