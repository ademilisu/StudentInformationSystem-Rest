package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Instructor;


public interface InstructorService {
	
	public List<Instructor> findAll();
	
	public Instructor findById(int instructorId);
	
	public Instructor save(Instructor instructor);
	
	public Instructor update(Instructor instructor);
	
	public void delete(int instructorId);
	
	public boolean check(Instructor instructor);

	public List<Instructor> searchInstructor(String name);
}
