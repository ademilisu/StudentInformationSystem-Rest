package schoolrestservicedemo.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.ResultRepository;
import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Result;
import schoolrestservicedemo.entity.Student;

@Service
public class MulStudentAndLectureServiceImpl implements MulStudentAndLectureService {
	
	@Autowired
	private ResultRepository resultRepo;
	
	@Autowired
	private StudentService studentService;

	@Override
	public Lecture addLecture(Student student, Lecture lecture) {
		Result result = check(student.getId(), lecture.getId());
		if(result.getId() == 0) {		
			student.addLecture(lecture);
			studentService.update(student);
		}
		return lecture;
	}
	
	@Override
	public void deleteLecture(int studentId, int lectureId) {
		Result result = check(studentId, lectureId);
		if(result.getId() != 0) {
			resultRepo.deleteById(result.getId());
		}
	}

	@Override
	public Student addStudent(Lecture lecture, Student student) { 
		Result result = check(student.getId(), lecture.getId());
		if(result == null) {
			student.addLecture(lecture);
			studentService.update(student);
		}
		return student;
	}
	

	
	public Result check(int studentId, int lectureId) {
		Result result = new Result();
		result.setStudentId(studentId);
		result.setLectureId(lectureId);
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("id");		
		Example<Result> example = Example.of(result, matcher);
		Optional<Result> newResult = resultRepo.findOne(example);
		if(newResult.isPresent()) {
			return newResult.get();
		}
		else {
			Result rs = new Result();
			rs.setId(0);
			return rs;
		}
	}

	
}
