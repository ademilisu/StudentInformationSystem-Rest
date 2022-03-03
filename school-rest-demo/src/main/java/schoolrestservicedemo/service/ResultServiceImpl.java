package schoolrestservicedemo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.ResultRepository;
import schoolrestservicedemo.entity.Result;

@Service
public class ResultServiceImpl implements ResultService{
	
	@Autowired
	private ResultRepository resultRepo;

	@Override
	public List<Result> findLectureResults(int lectureId) {
		Result result = new Result();
		result.setLectureId(lectureId);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id")
				.withIgnorePaths("studentId")
				.withIgnorePaths("examOne")
				.withIgnorePaths("examTwo")
				.withIgnorePaths("finalExam");
		Example<Result> example = Example.of(result, exampleMatcher);
		return resultRepo.findAll(example);
		
	}

	@Override
	public List<Result> findStudentResults(int studentId) {
		Result result = new Result();
		result.setStudentId(studentId);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnorePaths("id")
				.withIgnorePaths("lectureId")
				.withIgnorePaths("examOne")
				.withIgnorePaths("examTwo")
				.withIgnorePaths("finalExam");		
		Example<Result> example = Example.of(result, exampleMatcher);
		return resultRepo.findAll(example);
	}
	
	@Override
	public Result findResult(int resultId) {
		Optional<Result> result = resultRepo.findById(resultId);
		return result.isPresent() ? result.get() : null;
	}
	
	@Override
	public List<Result> updateAllResults(List<Result> results) {
		for(Result temp : results) {
			resultRepo.save(temp);
		}
		Result result = new Result();
		result.setLectureId(results.get(0).getLectureId());
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("id");
		Example<Result> example = Example.of(result, matcher);
		return resultRepo.findAll(example);
	}
	
	@Override
	public Result updateResult(Result result) {
		return resultRepo.save(result);
	}

	@Override
	public String deleteResult(int resultId) {
		resultRepo.deleteById(resultId);
		return "Object deleted";
	}

	@Override
	public String deleteLectureAllResults(int lectureId) {
		List<Result> results = findLectureResults(lectureId);
		for(Result tempResult : results) {
			resultRepo.deleteById(tempResult.getId());
		}
		return "Objects deleted";
	}

	

	
}
