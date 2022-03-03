package schoolrestservicedemo.service;


import java.util.List;

import schoolrestservicedemo.entity.Student;
import schoolrestservicedemo.entity.StudentDetail;

public interface MulStudentAndDetailService {
	
	public List<StudentDetail> findAll();
	
	public StudentDetail save(Student student, StudentDetail detail);
	
	public StudentDetail update(StudentDetail detail);
	
	public String delete(Student student, int detailId);
	

	
	
	


}
