package schoolrestservicedemo.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.service.InstructorService;
import schoolrestservicedemo.service.LectureService;
import schoolrestservicedemo.service.MulInstructorAndLectureService;

@RestController
@RequestMapping("/")
public class MulInstructorAndLectureController {
	
	@Autowired
	private MulInstructorAndLectureService instructorAndLectureService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private LectureService lectureService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/instructors/{instructorId}/lectures")
	public List<Lecture> findAllLecture(@PathVariable int instructorId){
		return checkInstructor(instructorId) != null ? instructorService.findById(instructorId).getLectures() : null;	
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/instructors/{instructorId}/lectures/{lectureId}")
	public Lecture findLectureById(@PathVariable int instructorId, @PathVariable int lectureId) {
		Instructor instructor = checkInstructor(instructorId);
		return instructor != null ? lectureService.findById(lectureId) : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/{lectureId}/instructors")
	public List<Instructor> findAllInstructor(@PathVariable int lectureId){
		return checkLecture(lectureId) != null ? lectureService.findById(lectureId).getInstructors() : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/{lectureId}/instructors/{instructorId}")
	public Instructor findInstructorById(@PathVariable int lectureId, @PathVariable int instructorId) {
		Lecture lecture = checkLecture(lectureId);
		return lecture != null ? instructorService.findById(instructorId) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PostMapping("/instructors/{instructorId}/lectures")
	public Lecture addLectureToInstructor(@PathVariable int instructorId, @RequestBody Lecture lecture) {
		Instructor instructor = checkInstructor(instructorId);
		return instructor != null ? instructorAndLectureService.addLectureToInstructor(instructor, lecture) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PostMapping("/lectures/{lectureId}/instructors")
	public Instructor addInstructorToLecture(@PathVariable int lectureId, @RequestBody Instructor instructor) {
		Instructor theInstructor = instructorService.findById(instructor.getId());
		Lecture lecture = checkLecture(lectureId);
		return lecture != null ? instructorAndLectureService.addInstructorToLecture(lecture,theInstructor) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@DeleteMapping("/lectures/{lectureId}/instructors")
	public void removeInstructorFromLecture(@PathVariable int lectureId, @RequestBody Instructor instructor) {
		Instructor theInstructor = instructorService.findById(instructor.getId());
		Lecture lecture = checkLecture(lectureId);
		if(lecture != null) {
			instructorAndLectureService.removeInstructorFromLecture(lecture, theInstructor);
		}
	}
	
	public Instructor checkInstructor(int instructorId) {
		return instructorService.findById(instructorId);	
	}
	
	public Lecture checkLecture(int lectureId) {
		return lectureService.findById(lectureId);	
	}
	
}
