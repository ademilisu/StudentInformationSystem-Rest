package schoolrestservicedemo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.RoleRepository;
import schoolrestservicedemo.dao.UserRepository;
import schoolrestservicedemo.entity.AppUser;
import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.entity.Role;
import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.entity.UserData;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	/*This class has methods for finding, saving and deleting the user through Jpa Repository and
	 * implements UserDetailsService Interface.
	 * This interface has a method named as loadUserByUsername and it check the username
	 * in database and find the user via Jpa Repository and return UserDetails object.
	 * UserDetails object is simply store user information (username, password, authorities)
	 *  which is later turn into Authentication objects in the SecurityContextHolder
	 */
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private InstructorService instructorService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = userRepo.findByUsername(username);
		if(appUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		else {
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			appUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
			User user = new User(appUser.getUsername(), appUser.getPassword(), authorities);
			return user;
		}
		
	}
	public AppUser findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	public UserData saveUser(AppUser user, Role role) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role oldRole = roleRepo.findByName(role.getName());
		if(oldRole != null) {
			user.addRole(oldRole);
		}
		if(oldRole == null) {
			user.addRole(role);
		}
		if(role.getName() == "ROLE_USER") {
			Student student = new Student();
			student.setEmail(user.getUsername());
			user.setStudent(student);
			studentService.save(student);
		}
		if(role.getName() == "ROLE_INSTRUCTOR") {
			Instructor instructor = new Instructor();
			instructor.setEmail(user.getUsername());
			user.setInstructor(instructor);
			instructorService.save(instructor);
		}
		user = userRepo.save(user);
		UserData newUserData = new UserData();
		newUserData.setUsername(user.getUsername());
		return newUserData;
	}
	
	public AppUser updateUser(AppUser user) {
		AppUser oldUser = findByUsername(user.getUsername());
		if(oldUser.getRoles() != null) {
			user.setRoles(oldUser.getRoles());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public AppUser findByStudentId(int studentId) {
		AppUser appUser = new AppUser();
		Student student = new Student();
		student.setId(studentId);
		appUser.setStudent(student);
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		Example<AppUser> example = Example.of(appUser, matcher);
		Optional<AppUser> result = userRepo.findOne(example);
		if(result.isPresent()) {
			return result.get();
		}
		else return null;
	}
	
	public AppUser findByInstructorId(int instructorId) {
		AppUser appUser = new AppUser();
		Instructor instructor = new Instructor();
		instructor.setId(instructorId);
		appUser.setInstructor(instructor);
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		Example<AppUser> example = Example.of(appUser, matcher);
		Optional<AppUser> result = userRepo.findOne(example);
		if(result.isPresent()) {
			return result.get();
		}
		else return null;
	}

	public void deleteUser(int id) {
		Optional<AppUser> result = userRepo.findById(id);
		if(result.isPresent()) {
			AppUser appUser = result.get();
			if(appUser.getStudent() != null) {
				studentService.delete(appUser.getStudent().getId());
			}
			if(appUser.getInstructor() != null) {
				instructorService.delete(appUser.getInstructor().getId());
			}
			userRepo.deleteById(id);	
		}
	}
	
	public AppUser addRole(AppUser user) {
		for(Role temp : user.getRoles()) {
			Role role = roleRepo.findByName(temp.getName());
			user.addRole(role);
		}
		return user;
	}
	
	
}
