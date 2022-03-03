package schoolrestservicedemo.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.entity.Transcript;
import schoolrestservicedemo.service.StudentService;
import schoolrestservicedemo.service.TranscriptService;

@RestController
@RequestMapping("/")
public class TranscriptController {
	
	@Autowired
	private TranscriptService transcriptService;
	
	@Autowired
	private StudentService studentService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students/{studentId}/transcripts")
	public List<Transcript> findAllTranscripts(@PathVariable int studentId){
		Student student = checkStudent(studentId);
		return student != null ? transcriptService.findAllTranscripts(studentId) : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/transcripts/{studentId}/lectures")
	public List<Lecture> findAllLecturesOfTranscript(@PathVariable() int studentId){
		Student student = checkStudent(studentId);
		return student != null ? transcriptService.findAllLecturesOfTranscript(studentId) : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students/{studentId}/transcripts/{transcriptId}")
	public Transcript findById(@PathVariable int studentId, @PathVariable int transcriptId) {
		Student student = checkStudent(studentId);
		return student != null ? transcriptService.findTranscriptById(transcriptId) : null;
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@DeleteMapping("/transcripts/{transcriptId}")
	public void deleteTranscript(@PathVariable int transcriptId) {
		transcriptService.deleteTranscript(transcriptId);
	}
	
	public Student checkStudent(int studentId) {
		return studentService.findById(studentId);
	}
}
