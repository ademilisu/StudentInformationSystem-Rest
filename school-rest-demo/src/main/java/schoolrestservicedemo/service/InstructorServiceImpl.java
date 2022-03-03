package schoolrestservicedemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.InstructorRepository;
import schoolrestservicedemo.entity.Instructor;

@Service
public class InstructorServiceImpl implements InstructorService {
	
	@Autowired
	private InstructorRepository instructorRepo;
	
	
	@Override
	public List<Instructor> findAll() {		
		return instructorRepo.findAll();
	}
	
	@Override
	public List<Instructor> searchInstructor(String name) {
		Instructor instructor = new Instructor();
		instructor.setFirstName(name);
		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withStringMatcher(StringMatcher.STARTING);
		Example<Instructor> example = Example.of(instructor, matcher);
		return instructorRepo.findAll(example);
	}
	
	@Override
	public Instructor findById(int instructorId) {
		Optional<Instructor> result = instructorRepo.findById(instructorId);
		return result.isPresent() ? result.get() : null; 
	}

	@Override
	public Instructor save(Instructor instructor) {
		if(check(instructor)) {
			return instructor;
		}
		else return instructorRepo.save(instructor);		
	}

	@Override
	public Instructor update(Instructor instructor) {
		Instructor oldInstructor = findById(instructor.getId());
		if(oldInstructor.getInstructorDetail() != null) {
			instructor.setInstructorDetail(oldInstructor.getInstructorDetail());
		}
		if(oldInstructor.getLectures() != null) {
			instructor.setLectures(oldInstructor.getLectures());
		}
		if(oldInstructor.getEmail() != null) {
			instructor.setEmail(oldInstructor.getEmail());
		}
		return instructorRepo.save(instructor);
	}

	@Override
	public void delete(int instructorId) {
		instructorRepo.deleteById(instructorId);
	}

	@Override
	public boolean check(Instructor instructor) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id");
		Example<Instructor> example = Example.of(instructor, exampleMatcher);
		if(instructorRepo.exists(example)) {
			return true;
		}
		else return false;
	}

	

}
