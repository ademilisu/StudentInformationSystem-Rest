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

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.service.LectureService;

@RestController
@RequestMapping("/")
public class LectureController {
	
	@Autowired
	private LectureService lectureService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/search/lectures/{name}")
	public List<Lecture> searchLecture(@PathVariable String name){
		List<Lecture> lecture = lectureService.searchLecture(name);
		return lecture;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures")
	public List<Lecture> findAll(){
		return lectureService.findAll();
	}
	
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/{lectureId}")
	public Lecture findById(@PathVariable int lectureId) {
		return lectureService.findById(lectureId);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@PostMapping("/lectures")
	public Lecture save(@RequestBody Lecture lecture) {
		lecture.setId(0);
		return lectureService.save(lecture);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@PutMapping("/lectures")
	public Lecture update(@RequestBody Lecture lecture) {
		return lectureService.update(lecture);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/lectures/{lectureId}")
	public String delete(@PathVariable int lectureId) {
		lectureService.delete(lectureId);
		return "Object deleted";
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/active")
	public void lecturesActive() {
		lectureService.setActive();
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/passive")
	public void lecturePassive() {
		lectureService.setPassive();
	}
}



















