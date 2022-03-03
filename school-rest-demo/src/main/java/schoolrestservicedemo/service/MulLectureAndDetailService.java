package schoolrestservicedemo.service;

import java.util.List;

import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.LectureDetail;

public interface MulLectureAndDetailService {
	
	public List<LectureDetail> findAll();
	
	public LectureDetail save(Lecture lecture, LectureDetail detail);
	
	public LectureDetail update(LectureDetail detail);
	
	public String delete(Lecture lecture, int detailId);
}
