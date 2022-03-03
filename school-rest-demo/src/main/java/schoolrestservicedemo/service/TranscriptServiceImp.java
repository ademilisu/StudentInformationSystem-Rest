package schoolrestservicedemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import schoolrestservicedemo.dao.TranscriptRepository;
import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Result;
import schoolrestservicedemo.entity.Transcript;

@Service
public class TranscriptServiceImp implements TranscriptService{
	
	@Autowired
	private TranscriptRepository transcriptRepo;
	
	@Autowired 
	private LectureService lectureService;
	
	@Override
	public List<Transcript> findAllTranscripts(int studentId) {
		Transcript transcript = new Transcript();
		transcript.setStudentId(studentId);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnorePaths("id")
				.withIgnorePaths("lectureId")
				.withIgnorePaths("score");
		Example<Transcript> example = Example.of(transcript, matcher);
		return transcriptRepo.findAll(example);
	}
	
	@Override
	public List<Lecture> findAllLecturesOfTranscript(int studentId) {
		Transcript transcript = new Transcript();
		transcript.setStudentId(studentId);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnorePaths("id")
				.withIgnorePaths("lectureId")
				.withIgnorePaths("score");
		Example<Transcript> example = Example.of(transcript, matcher);
		List<Transcript> transcripts = transcriptRepo.findAll(example);
		List<Lecture> lectures = new ArrayList<>();
		for(Transcript temp : transcripts) {
			lectures.add(lectureService.findById(temp.getLectureId()));
		}
		return lectures;
	}
	
	@Override
	public Transcript findTranscriptById(int transcriptId) {
		Optional<Transcript> transcript = transcriptRepo.findById(transcriptId);
		return transcript.isPresent() ? transcript.get() : null;
	}
	
	@Override
	public void deleteTranscript(int transcriptId) {
		transcriptRepo.deleteById(transcriptId);
	}

	@Override
	public void updateTranscript(Result result) {
		Transcript transcript = new Transcript();
		transcript.setStudentId(result.getStudentId());
		transcript.setLectureId(result.getLectureId());
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnorePaths("id")
				.withIgnorePaths("score");
		Example<Transcript> example = Example.of(transcript, matcher);
		Optional<Transcript> newTranscript = transcriptRepo.findOne(example);
		if(newTranscript.isPresent()) {
			transcript = newTranscript.get();
				
		}
		String score = calculate(result.getExamOne(), result.getExamTwo(), result.getFinalExam());
		transcript.setScore(score);
		transcriptRepo.save(transcript);
	}
	
	@Override
	public void updateAllTranscripts(List<Result> results) {
		Transcript transcript = new Transcript();
		transcript.setLectureId(results.get(0).getLectureId());
		ExampleMatcher matcher = ExampleMatcher.matchingAll()
				.withIgnorePaths("id");
		Example<Transcript> example = Example.of(transcript, matcher);
		List<Transcript> transcripts = transcriptRepo.findAll(example);
		for(int i=0; i<results.size(); i++) {
			String score = calculate(results.get(i).getExamOne(), results.get(i).getExamTwo(), results.get(i).getFinalExam());
			Transcript newTranscript = null;
			if(transcripts.isEmpty()) {
				newTranscript = new Transcript();
			}
			else {
				newTranscript = transcripts.get(i);
			}
			newTranscript.setLectureId(results.get(i).getLectureId());
			newTranscript.setStudentId(results.get(i).getStudentId());
			newTranscript.setScore(score);
			results.get(i).setTranscript(newTranscript);
			transcriptRepo.save(newTranscript);
		}
	}
	
	public String calculate(String examOne, String examTwo, String finalExam) {
		int firstExam =Integer.parseInt(examOne);
		int result = 0;
		if(examTwo != null && finalExam == null) {
			int secondExam = Integer.parseInt(examTwo);
			result = (firstExam + secondExam)/2;
		}
		else {
			int lastExam = Integer.parseInt(finalExam);
			result = (firstExam + lastExam) /2;
		}	
		return String.valueOf(result);
	}

	

	

}
