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
import schoolrestservicedemo.entity.LectureDetail;
import schoolrestservicedemo.service.LectureService;
import schoolrestservicedemo.service.MulLectureAndDetailService;

@RestController
@RequestMapping("/lectures")
public class MulLectureAndDetailController {
	
	@Autowired
	private LectureService lectureService;
	
	@Autowired
	private MulLectureAndDetailService lectureDetailService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/details")
	public List<LectureDetail> findAll(){
		return lectureDetailService.findAll();
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/{lectureId}/details")
	public LectureDetail findById(@PathVariable int lectureId) {
		return check(lectureId) != null ? lectureService.findById(lectureId).getLectureDetail() : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PostMapping("/{lectureId}/details")
	public LectureDetail save(@PathVariable int lectureId, @RequestBody LectureDetail detail) {
		detail.setId(0);
		Lecture lecture = check(lectureId);
		return lecture != null ? lectureDetailService.save(lecture, detail) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PutMapping("/{lectureId}/details")
	public LectureDetail update(@PathVariable int lectureId, @RequestBody LectureDetail detail) {
		Lecture lecture = check(lectureId);
		return lecture != null ? lectureDetailService.update(detail) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/{lectureId}/details/{detailId}")
	public String delete(@PathVariable int lectureId, @PathVariable int detailId) {
		Lecture lecture = check(lectureId);
		return lecture != null ? lectureDetailService.delete(lecture, detailId) : null;
	}
	
	public Lecture check(int lectureId) {
		return lectureService.findById(lectureId);
	}
}





















