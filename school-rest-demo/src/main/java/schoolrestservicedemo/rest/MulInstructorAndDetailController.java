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
import schoolrestservicedemo.entity.InstructorDetail;
import schoolrestservicedemo.service.InstructorService;
import schoolrestservicedemo.service.MulInstrcutorAndDetailService;

@RestController
@RequestMapping("/instructors")
public class MulInstructorAndDetailController {
	
	@Autowired
	private MulInstrcutorAndDetailService instructorDetailService;
	
	@Autowired
	private InstructorService instructorService;
	
	@RolesAllowed({"ROLE_ADMIN"})
	@GetMapping("/details")
	public List<InstructorDetail> findAll(){
		return instructorDetailService.findAll();	
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/{instructorId}/details")
	public InstructorDetail findById(@PathVariable int instructorId) {
		return check(instructorId) == null ? null : instructorService.findById(instructorId).getInstructorDetail();
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PostMapping("{instructorId}/details")
	public InstructorDetail save(@PathVariable int instructorId, @RequestBody InstructorDetail detail) {
		detail.setId(0);
		Instructor instructor = check(instructorId);
		return instructor == null ? null : instructorDetailService.save(instructor, detail);
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PutMapping("{instructorId}/details")
	public InstructorDetail update(@PathVariable int instructorId, @RequestBody InstructorDetail detail) {
		Instructor instructor = check(instructorId);
		return instructor == null ? null : instructorDetailService.update(detail);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("{instructorId}/details/{detailId}")
	public String delete(@PathVariable int instructorId, @PathVariable int detailId) {
		Instructor instructor = check(instructorId);
		return instructor == null ? null : instructorDetailService.delete(instructor, detailId);
	}
	
	public Instructor check(int instructorId) {
		return instructorService.findById(instructorId);
	}
}















