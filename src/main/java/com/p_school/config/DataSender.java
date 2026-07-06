package com.p_school.config;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.p_school.entity.Permission;
import com.p_school.entity.Role;
import com.p_school.entity.User;
import com.p_school.enums.PermissionStatus;
import com.p_school.enums.UserStatus;
import com.p_school.repository.PermissionRepository;
import com.p_school.repository.RoleRepository;
import com.p_school.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataSender implements CommandLineRunner {

	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final SecurityConfig securityConfig;

	@Override
	public void run(String... args) throws Exception {

		// ================= PERMISSION =================
		if (permissionRepository.count() == 0) {

			List<Permission> permissions = List.of(

					new Permission(0, "USER_CREATE", "Create User", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "USER_VIEW", "View User", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "USER_UPDATE", "Update User", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "USER_DELETE", "Delete User", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "ROLE_CREATE", "Create Role", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "ROLE_VIEW", "View Role", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "ROLE_UPDATE", "Update Role", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null),

					new Permission(0, "ROLE_DELETE", "Delete Role", PermissionStatus.ACTIVE, true, false,
							LocalDateTime.now(), null)

			);

			permissionRepository.saveAll(permissions);

			System.out.println("========== Permissions Inserted ==========");
		}

		// ================= SUPER ADMIN PERMISSIONS =================

		Set<Permission> superAdminPermissions = permissionRepository.findAll().stream()
				.filter(permission -> !permission.getPermissionName().equals("USER_UPDATE"))
				.collect(Collectors.toSet());

		// ================= ROLE =================

		if (roleRepository.count() == 0) {

			List<Role> roles = List.of(

					new Role(0, "SUPER_ADMIN", "System Super Administrator", true, false, LocalDateTime.now(), null,
							null, superAdminPermissions),

					new Role(0, "ADMIN", "Institute Administrator", true, false, LocalDateTime.now(), null, null, null),

					new Role(0, "TEACHER", "Teacher Role", true, false, LocalDateTime.now(), null, null, null),

					new Role(0, "STUDENT", "Student Role", true, false, LocalDateTime.now(), null, null, null),

					new Role(0, "PARENT", "Parent Role", true, false, LocalDateTime.now(), null, null, null),

					new Role(0, "INSTRUCTOR", "Institute Instructor", true, false, LocalDateTime.now(), null, null,
							null),

					new Role(0, "INDIVIDUAL_INSTRUCTOR", "Individual Instructor", true, false, LocalDateTime.now(),
							null, null, null)

			);

			roleRepository.saveAll(roles);

			System.out.println("========== Roles Inserted ==========");

			// ================= CREATE SUPER ADMIN USER =================

			if (!userRepository.existsByEmail("superadmin@pschool.com")) {

				System.err.println("super admin pannel");

				Role superAdminRole = roleRepository.findByRoleName("SUPER_ADMIN").orElseThrow();

				User superAdmin = new User();

				superAdmin.setFullName("Super Admin");
				superAdmin.setEmail("akpatidarpatidar0@gmail.com");
				superAdmin.setPassword(securityConfig.passwordEncoder().encode("admin@123"));
				System.err.println(securityConfig.passwordEncoder().encode("admin@123"));
//				superAdmin.setPassword("Admin@123");
				superAdmin.setStatus(UserStatus.ACTIVE);
				superAdmin.setDeleted(false);
				superAdmin.setIndividual(false);
				superAdmin.setCreatedAt(LocalDateTime.now());
				superAdmin.setCreatedBy(null);

				// Agar institute null ho sakta hai
				superAdmin.setInstitute(null);

				// SUPER_ADMIN role assign
				superAdmin.setRoles(Set.of(superAdminRole));

				userRepository.save(superAdmin);

				System.out.println("===== SUPER ADMIN USER CREATED =====");
			}

		}

	}
}