package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.Employee;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT lpad(max(e.emp_no) + 1 , 8 , 0) FROM sriLankaVacationPlanner.employee as e" , nativeQuery = true)
    String getNextEmpNo();

    @Query(value = "SELECT * FROM sriLankaVacationPlanner.employee AS e where e.nic = ?1" , nativeQuery = true)
    Employee getByNIC(String nic);

    @Query(value = "SELECT * FROM sriLankaVacationPlanner.employee AS e where e.email = :email" , nativeQuery = true)
    Employee getByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM sriLankaVacationPlanner.employee AS e where e.mobile_number = :mobileNumber" , nativeQuery = true)
    Employee getByMobileNumber(@Param("mobileNumber") String mobileNumber);

//    List<Employee> findByNic(@Length(min = 10,max = 12) String nic);

    @Query(value = "SELECT e FROM Employee e where e.id not in(SELECT u.employee.id from User u where u.employee.id is not null )")
    List<Employee> listWithoutUserAccount();
}
