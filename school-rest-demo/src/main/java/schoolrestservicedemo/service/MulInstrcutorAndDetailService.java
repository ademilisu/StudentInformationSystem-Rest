package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.entity.InstructorDetail;

public interface MulInstrcutorAndDetailService {
	
	public List<InstructorDetail> findAll();
	
	public InstructorDetail save(Instructor instructor, InstructorDetail detail);
	
	public InstructorDetail update(InstructorDetail detail);
	
	public String delete(Instructor instructor, int detailId);


		
}
