package com.sampleproject.sampleproject.repo;

import com.sampleproject.sampleproject.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    @Query(value = "SELECT * FROM sriLankaVacationPlanner.privilege AS p where p.role_id = ?1 AND p.module_id = ?2" , nativeQuery = true)
    Privilege getPrivilegeByRoleModule( Integer roleid, Integer moduleid);

    @Query(value = "SELECT BIT_OR(p.privi_select) , BIT_OR(p.privi_insert) , BIT_OR(p.privi_update) , BIT_OR(p.privi_delete) " +
            "FROM sriLankaVacationPlanner.privilege AS p " +
            "WHERE p.module_id IN (SELECT m.id FROM sriLankaVacationPlanner.module AS m WHERE m.name = ?2) " +
            "AND p.role_id IN (SELECT uhr.role_id FROM sriLankaVacationPlanner.user_has_role AS uhr " +
            "WHERE uhr.user_id IN (SELECT u.id FROM sriLankaVacationPlanner.user AS u WHERE u.username = ?1))",
            nativeQuery = true)
    String getPrivilegeByUserModule(String username, String modulename);

}
