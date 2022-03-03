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

import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.service.StudentService;



@RestController
@RequestMapping("/")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students")
	public List<Student> findAll(){	
		return studentService.findAll();
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/search/students/{name}")
	public List<Student> searchStudents(@PathVariable String name){
		return studentService.searchStudents(name);
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students/{studentId}")
	public Student findById(@PathVariable int studentId) {
		return studentService.findById(studentId);
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping("/students")
	public Student save(@RequestBody Student student) {
		student.setId(0);
		return studentService.save(student);
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
	@PutMapping("/students")
	public Student update(@RequestBody Student student) {
		return studentService.update(student);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/students/{studentId}")
	public void delete(@PathVariable int studentId) {	
		studentService.delete(studentId);
	}
	
	
}


















