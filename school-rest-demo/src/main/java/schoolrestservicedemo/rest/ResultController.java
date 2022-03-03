package schoolrestservicedemo.rest;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Result;
import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.service.LectureService;
import schoolrestservicedemo.service.ResultService;
import schoolrestservicedemo.service.StudentService;
import schoolrestservicedemo.service.TranscriptService;

@RestController
@RequestMapping("/")
public class ResultController {
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private LectureService lectureService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/lectures/{lectureId}/results")
	public List<Result> findLectureResults(@PathVariable int lectureId){		
		return checkLecture(lectureId) != null ? resultService.findLectureResults(lectureId) : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/students/{studentId}/results")
	public List<Result> findStudentResults(@PathVariable int studentId){
		return checkStudent(studentId) != null ? resultService.findStudentResults(studentId) : null;
	}
	
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@GetMapping("/results/{resultId}")
	public Result findResult(@PathVariable int resultId) {
		return resultService.findResult(resultId);	
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PutMapping("/results/all")
	public List<Result> updateAllResultS(@RequestBody List<Result> results) {
		if(results != null) {
			if(results.get(0).getExamTwo() != null || results.get(0).getFinalExam() != null) {
				transcriptService.updateAllTranscripts(results);
			}
		}
		return resultService.updateAllResults(results);
	}
	
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_INSTRUCTOR"})
	@PutMapping("/results")
	public Result updateResult(@RequestBody Result result) {
		if(result.getExamTwo() != null || result.getFinalExam() != null) {
			transcriptService.updateTranscript(result);
		}
		return resultService.updateResult(result);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/results/{resultId}")
	public String deleteResult(@PathVariable int resultId) {
		return resultService.deleteResult(resultId);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/lectures/{lectureId}/results")
	public String deleteLectureAllResults(@PathVariable int lectureId) {
		return checkLecture(lectureId) != null ? resultService.deleteLectureAllResults(lectureId) : null;
	}
	
	public Lecture checkLecture(int lectureId) {
		return lectureService.findById(lectureId);
	}
	
	public Student checkStudent(int studentId) {
		return studentService.findById(studentId);
	}
	
	
	
	
	
	
}
