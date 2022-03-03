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

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.service.LectureService;
import schoolrestservicedemo.service.MulStudentAndLectureService;
import schoolrestservicedemo.service.StudentService;

@RestController
@RequestMapping("/")
public class MulStudentAndLectureController {
	
	@Autowired
	private MulStudentAndLectureService studentAndLectureService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private LectureService lectureService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students/{studentId}/lectures")
	public List<Lecture> findAllLectures(@PathVariable int studentId){
		return checkStudent(studentId) != null ? studentService.findById(studentId).getLectures() : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students/{studentId}/lectures/{lectureId}")
	public Lecture findByIdLecture(@PathVariable int studentId, @PathVariable int lectureId) {
		Student student = checkStudent(studentId);
		return student != null ? lectureService.findById(lectureId) : null; 
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@DeleteMapping("/students/{studentId}/lectures/{lectureId}")
	public void deleteLecture(@PathVariable int studentId, @PathVariable int lectureId) {
		Student student = checkStudent(studentId);
		if(student != null) {
			studentAndLectureService.deleteLecture(studentId, lectureId);
		}
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PostMapping("/students/{studentId}/lectures")
	public Lecture addLecture(@PathVariable int studentId, @RequestBody Lecture lecture) {
		Student student = checkStudent(studentId);
		return student != null ? studentAndLectureService.addLecture(student, lecture): null;
	}
	
	@RolesAllowed({"ROLE_USER","ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/{lectureId}/students")
	public List<Student> findAllStudents(@PathVariable int lectureId){
		return checkLecture(lectureId) != null ? lectureService.findById(lectureId).getStudents() : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/{lectureId}/students/{studentId}")
	public Student findByIdStudent(@PathVariable int lectureId, @PathVariable int studentId) {
		Lecture lecture = checkLecture(lectureId);
		return lecture != null ? studentService.findById(studentId) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PostMapping("/lectures/{lectureId}/students")
	public Student addStudent(@PathVariable int lectureId, @RequestBody Student student) {
		Student theStudent = studentService.findById(student.getId());
		Lecture lecture = checkLecture(lectureId);
		return lecture != null ? studentAndLectureService.addStudent(lecture, theStudent) : null;
	}
	
	public Student checkStudent(int studentId) {
		return studentService.findById(studentId);
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	public Lecture checkLecture(int lectureId) {
		return lectureService.findById(lectureId);
	}
}








































































