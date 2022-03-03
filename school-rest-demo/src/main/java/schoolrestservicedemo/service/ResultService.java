package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Result;

public interface ResultService {

	List<Result> findLectureResults(int lectureId);

	List<Result> findStudentResults(int studentId);
	
	List<Result> updateAllResults(List<Result> results);
	
	Result findResult(int resultId);
	
	Result updateResult(Result result);

	String deleteResult(int resultId);

	String deleteLectureAllResults(int lectureId);


}
