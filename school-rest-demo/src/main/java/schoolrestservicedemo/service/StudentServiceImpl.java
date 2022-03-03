package schoolrestservicedemo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.StudentRepository;
import schoolrestservicedemo.entity.Student;


@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Override
	public List<Student> findAll() {		
		return studentRepo.findAll();
	}
	
	@Override
	public List<Student> searchStudents(String name) {
		Student student = new Student();
		student.setFirstName(name);
		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Student> example = Example.of(student, matcher);
		return studentRepo.findAll(example);
	}
	
	@Override
	public Student findById(int studentId) {	
		Optional<Student> result = studentRepo.findById(studentId);	
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Student save(Student student) {	
		if(check(student)) {
			return student;
		}
		else {
			return studentRepo.save(student);
		}	
	}
	
	@Override
	public Student update(Student student) {
		Student oldStudent = findById(student.getId());
		if(oldStudent.getStudentDetail() != null) {
			student.setStudentDetail(oldStudent.getStudentDetail());
		}
		if(oldStudent.getLectures() != null) {
			student.setLectures(oldStudent.getLectures());
		}
		if(oldStudent.getEmail() != null) {
			student.setEmail(oldStudent.getEmail());
		}
		return studentRepo.save(student);
	}

	@Override
	public void delete(int studentId) {	
		studentRepo.deleteById(studentId);

	}

	@Override
	public boolean check(Student student) {		
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id");
		Example<Student> example = Example.of(student, exampleMatcher);
		if(studentRepo.exists(example)) {
			return true;
		}
		return false;
	}

	



}
