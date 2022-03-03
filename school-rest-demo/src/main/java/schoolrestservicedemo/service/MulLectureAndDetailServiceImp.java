package schoolrestservicedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import schoolrestservicedemo.dao.LectureDetailRepository;
import schoolrestservicedemo.entity.Lecture;
import schoolrestservicedemo.entity.LectureDetail;

@Service
public class MulLectureAndDetailServiceImp implements MulLectureAndDetailService {
	
	@Autowired
	private LectureDetailRepository lectureDetailRepo;
	
	@Override
	public List<LectureDetail> findAll() {
		return lectureDetailRepo.findAll();
	}

	@Override
	public LectureDetail save(Lecture lecture, LectureDetail detail) {
		if(lecture.getLectureDetail() != null) {
			return lecture.getLectureDetail();
		}
		else {
			lecture.setLectureDetail(detail);
			return lectureDetailRepo.save(detail);
		}	
	}

	@Override
	public LectureDetail update(LectureDetail detail) {
		return lectureDetailRepo.save(detail);
	}

	@Override
	public String delete(Lecture lecture, int detailId) {
		lecture.setLectureDetail(null);
		lectureDetailRepo.deleteById(detailId);
		return "Object deleted";
	}

}
