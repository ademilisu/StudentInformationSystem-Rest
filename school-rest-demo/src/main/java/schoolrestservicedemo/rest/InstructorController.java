package schoolrestservicedemo.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.service.InstructorService;

@RestController
@RequestMapping("/")
public class InstructorController {
	
	@Autowired
	private InstructorService instructorService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/instructors")
	public List<Instructor> findAll(){
		return instructorService.findAll();
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/search/instructors/{name}")
	public List<Instructor> searchInstructors(@PathVariable String name){
		return instructorService.searchInstructor(name);
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/instructors/{instructorId}")
	public Instructor findById(@PathVariable int instructorId) {
		return instructorService.findById(instructorId);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@PostMapping("/instructors")
	public Instructor save(@RequestBody Instructor instructor) {
		instructor.setId(0);
		return instructorService.save(instructor);
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PutMapping("/instructors")
	public Instructor update(@RequestBody Instructor instructor) {
		return instructorService.update(instructor);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/instructors/{instructorId}")
	public void delete(@PathVariable int instructorId) {	
		instructorService.delete(instructorId);
	}
}


















