 package schoolrestservicedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.entity.Instructor;
import schoolrestservicedemo.entity.Lecture;

@Service
public class MulInstructorAndLectureServiceImpl implements MulInstructorAndLectureService {
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired LectureService lectureService;
	
	@Override
	public Lecture addLectureToInstructor(Instructor instructor, Lecture lecture) {
		List<Lecture> lectures = instructor.getLectures();
		boolean check = true;
		for(Lecture temp : lectures) {
			if(temp.getLectureName().equals(lecture.getLectureName())) {
				check = false;
			}
		}
		if(check) {
			instructor.addLecture(lecture);
		}
		instructorService.update(instructor);
		return lecture;
	}

	@Override
	public Instructor addInstructorToLecture(Lecture lecture, Instructor instructor) {
		List<Lecture> lectures = instructor.getLectures();
		boolean check = true;
		for(Lecture temp : lectures) {
			if(temp.getLectureName().equals(lecture.getLectureName())) {
				check = false;
			}
		}
		if(check) {
			instructor.addLecture(lecture);
		}
		return instructorService.update(instructor);
	}

	@Override
	public void removeInstructorFromLecture(Lecture lecture, Instructor instructor) {
		List<Instructor> instructors = lecture.getInstructors();
		boolean check = false;
		for(Instructor temp : instructors) {
			if(temp.getEmail().equals(instructor.getEmail())) {
				check = true;
			}
		}
		if(check) {
			lecture.removeInstructor(instructor);
		}
		lectureService.update(lecture);
	}

	@Override
	public void removeLectureFromInstructor(Lecture lecture, Instructor instructor) {
		List<Lecture> lectures = instructor.getLectures();
		boolean check = false;
		for(Lecture temp : lectures) {
			if(temp.getLectureName().equals(lecture.getLectureName())) {
				check = true;
			}
		}
		if(check) {
			instructor.removeLecture(lecture);
		}
		instructorService.update(instructor);
		
	}


}
