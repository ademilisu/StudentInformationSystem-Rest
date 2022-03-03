package schoolrestservicedemo.service;

import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.entity.Lecture;

public interface MulInstructorAndLectureService {

	Lecture addLectureToInstructor(Instructor instructor, Lecture lecture);

	Instructor addInstructorToLecture(Lecture lecture, Instructor instructor);

	void removeInstructorFromLecture(Lecture lecture, Instructor instructor);
	
	void removeLectureFromInstructor(Lecture lecture, Instructor instructor);


}
