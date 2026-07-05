package com.p_school.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p_school.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByRoleName(String string);

}
