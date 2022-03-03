package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Lecture;

public interface LectureService {
	
	public List<Lecture> findAll();
	
	public Lecture findById(int lectureId);
	
	public Lecture save(Lecture lecture);
	
	public Lecture update(Lecture lecture);
	
	public void delete(int lectureId);
	
	public boolean check(Lecture lecture);

	public void setActive();
	
	public void setPassive();

	public List<Lecture> searchLecture(String name);

}
