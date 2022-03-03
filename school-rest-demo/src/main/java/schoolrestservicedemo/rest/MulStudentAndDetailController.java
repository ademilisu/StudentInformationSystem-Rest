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
import schoolrestservicedemo.entity.StudentDetail;
import schoolrestservicedemo.service.MulStudentAndDetailService;
import schoolrestservicedemo.service.StudentService;

@RestController
@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
@RequestMapping("/students")
public class MulStudentAndDetailController {
	
	@Autowired
	private MulStudentAndDetailService studentDetailService;
	
	@Autowired
	private StudentService studentService;
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/details")
	public List<StudentDetail> findAll(){
		return studentDetailService.findAll(); 
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/{studentId}/details")
	public StudentDetail findById(@PathVariable int studentId) {
		return check(studentId) != null ? studentService.findById(studentId).getStudentDetail() : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
	@PostMapping("/{studentId}/details")
	public StudentDetail save(@PathVariable int studentId, @RequestBody StudentDetail detail) {	
		detail.setId(0);
		Student student = check(studentId);
		return student != null ? studentDetailService.save(student, detail) : null;	
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
	@PutMapping("/{studentId}/details")
	public StudentDetail update(@PathVariable int studentId, @RequestBody StudentDetail detail) {
		Student student = check(studentId);
		return student != null ? studentDetailService.update(detail) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/{studentId}/details/{detailId}")
	public String delete(@PathVariable int studentId, @PathVariable int detailId) {
		Student student = check(studentId);
		return check(studentId) != null ? studentDetailService.delete(student, detailId) : null;		
	}
	
	public Student check(int studentId) {
		return studentService.findById(studentId);
	}
	
}	
