package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Student;

public interface StudentService {
	
	public List<Student> findAll();
	
	public Student findById(int studentId);
	
	public Student save(Student student);
	
	public Student update(Student student);
	
	public void delete(int studentId);
	
	public boolean check(Student student);

	public List<Student> searchStudents(String name);
}
