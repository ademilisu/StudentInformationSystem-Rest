package schoolrestservicedemo.service;

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Student;

public interface MulStudentAndLectureService {
	
	public Lecture addLecture(Student student, Lecture lecture);
	
	public Student addStudent(Lecture lecture, Student student);

	void deleteLecture(int studentId, int lectureId);
	
}
