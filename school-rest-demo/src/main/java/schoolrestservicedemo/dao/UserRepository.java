package schoolrestservicedemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import schoolrestservicedemo.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
	AppUser findByUsername(String username);
}
