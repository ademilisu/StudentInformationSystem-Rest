package schoolrestservicedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.StudentDetailRepository;
import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.entity.StudentDetail;

@Service
public class MulStudentAndDetailServiceImpl implements MulStudentAndDetailService {
	
 	@Autowired
	private StudentDetailRepository studentDetailRepo;

 	@Override
	public List<StudentDetail> findAll() {
		return studentDetailRepo.findAll();
	}

	@Override
	public StudentDetail save(Student student, StudentDetail detail) {		
		if(student.getStudentDetail() != null) {
			return student.getStudentDetail();
		}
		else {
		student.setStudentDetail(detail);
		return studentDetailRepo.save(detail);
		}
	}

	@Override
	public StudentDetail update(StudentDetail detail) {
		return studentDetailRepo.save(detail);
	}

	@Override
	public String delete(Student student, int detailId) {
		student.setStudentDetail(null);
		studentDetailRepo.deleteById(detailId);
		return "Object deleted";
	}

	

	
	

	
}
