package com.p_school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p_school.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer>  {

}
