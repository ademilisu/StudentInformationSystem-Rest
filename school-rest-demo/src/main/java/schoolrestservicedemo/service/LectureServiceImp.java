package schoolrestservicedemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.LectureRepository;
import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.LectureSelection;

@Service
public class LectureServiceImp implements LectureService {
	
	@Autowired
	private LectureRepository lectureRepo;
	

	@Override
	public List<Lecture> searchLecture(String name) {
		Lecture lecture = new Lecture();
		lecture.setLectureName(name);
		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
		Example<Lecture> example = Example.of(lecture, matcher);
		return lectureRepo.findAll(example);
	}
	
	@Override
	public List<Lecture> findAll() {
		return lectureRepo.findAll();
	}

	@Override
	public Lecture findById(int lectureId) {
		Optional<Lecture> result = lectureRepo.findById(lectureId);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public Lecture save(Lecture lecture) {
		if(check(lecture)) {
			return lecture;
		}
		else return lectureRepo.save(lecture);
	}

	@Override
	public Lecture update(Lecture lecture) {
		Lecture oldLecture = findById(lecture.getId());
		if(oldLecture.getLectureDetail() != null) {
			lecture.setLectureDetail(oldLecture.getLectureDetail());
		}
		if(oldLecture.getInstructors() != null) {
			lecture.setInstructors(oldLecture.getInstructors());
		}
		if(oldLecture.getStudents() != null) {
			lecture.setStudents(oldLecture.getStudents());
		}
		return lectureRepo.save(lecture);
	}

	@Override
	public void delete(int lectureId) {
		lectureRepo.deleteById(lectureId);
	}

	@Override
	public boolean check(Lecture lecture) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnorePaths("id");
		Example<Lecture> example = Example.of(lecture, exampleMatcher);
		if(lectureRepo.exists(example)) {
			return true;
		}
		else return false;
	}

	@Override
	public void setActive() {
		List<Lecture> lectures = findAll();
		for(Lecture temp : lectures) {
			temp.setLectureSelection(LectureSelection.ACTIVE);
			update(temp);
		}
	}

	@Override
	public void setPassive() {
		List<Lecture> lectures = findAll();
		for(Lecture temp : lectures) {
			temp.setLectureSelection(LectureSelection.PASSIVE);
			update(temp);
		}
	}


}
