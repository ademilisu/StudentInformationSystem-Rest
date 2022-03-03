package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.Result;
import schoolrestservicedemo.entity.Transcript;

public interface TranscriptService {

	public List<Transcript> findAllTranscripts(int studentId);

	public List<Lecture> findAllLecturesOfTranscript(int studentId);

	public Transcript findTranscriptById(int transcriptId);

	public void updateTranscript(Result result);

	public void deleteTranscript(int transcriptId);

	public void updateAllTranscripts(List<Result> results);


}
