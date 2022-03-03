package schoolrestservicedemo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.InstructorDetailRepository;
import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.entity.InstructorDetail;

@Service
public class MulInstructorAndDetailServiceImpl implements MulInstrcutorAndDetailService {
	
	@Autowired
	private InstructorDetailRepository instructorDetailRepo;
	
	
	@Override
	public List<InstructorDetail> findAll(){
		return instructorDetailRepo.findAll();
	}
	

	@Override
	public InstructorDetail save(Instructor instructor, InstructorDetail detail) {	
		if(instructor.getInstructorDetail()!= null) {
			return instructor.getInstructorDetail();
		}
		else {
		instructor.setInstructorDetail(detail);
		return instructorDetailRepo.save(detail);
		}
	}

	@Override
	public InstructorDetail update(InstructorDetail detail) {
		return instructorDetailRepo.save(detail);
	}

	@Override
	public String delete(Instructor instructor, int detailId) {
		instructor.setInstructorDetail(null);
		instructorDetailRepo.deleteById(detailId);
		return "Object deleted";
	}


}
