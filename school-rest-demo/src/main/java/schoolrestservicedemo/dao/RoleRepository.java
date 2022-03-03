package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String roleName);
}
